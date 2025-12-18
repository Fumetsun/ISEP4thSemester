package jobs4u.base.notificationmanagement;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import jobs4u.base.applicationmanagement.domain.JobApplication;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.notificationmanagement.domain.MessageDTO;
import jobs4u.base.notificationmanagement.domain.Notification;
import jobs4u.base.notificationmanagement.domain.NotificationFactory;
import jobs4u.base.notificationmanagement.domain.NotificationTypes;
import jobs4u.base.notificationmanagement.repositories.NotificationRepository;
import jobs4u.base.usermanagement.domain.BaseRoles;


import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class EmailNotificationHandler extends Thread{
    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private JobApplication jobApplication;

    private final NotificationRepository notifRepo = PersistenceContext.repositories().notifications();


    private static final int ASS_PORT_NUMBER = 6912;
    private static InetAddress serverIP;
    private static Socket sock;
    private String password;
    private String serverIp = "10.8.0.80";

    public EmailNotificationHandler(JobApplication jobApplication, String password) {
        this.jobApplication = jobApplication;
        this.password = password;
    }


    public void run() {


        try {
            serverIP = InetAddress.getByName(serverIp);
        } catch (UnknownHostException ex) {
            System.out.println("Invalid server specified: " + serverIp);
            System.exit(1);
        }

        try {
            sock = new Socket(serverIP, ASS_PORT_NUMBER);
            //sock.setSoTimeout(SOCKET_TIMEOUT * 1000);
        } catch (IOException ex) {
            System.out.println("Failed to establish TCP connection");
            System.exit(1);
        }

        try (ObjectOutputStream sOut = new ObjectOutputStream(sock.getOutputStream());
             ObjectInputStream sIn = new ObjectInputStream(sock.getInputStream());
             ) {













            SystemUser user = authz.loggedinUserWithPermissions(BaseRoles.CUSTOMER_MANAGER).get();

            sendVersionAndCode(sOut, 4);
            sendCredentials(sOut, user.username().toString(), password);

            String email = user.email().toString() + "/" + jobApplication.getCandidate().associatedUser().email() + "/Verification Process Results"  + "/Your application state has been updated. Your application is now: " + jobApplication.getState().toString() + ". This is an automated email, please do not reply.";

            byte[] emailData = email.getBytes();
            int emailDataLen = emailData.length;
            int l = emailDataLen & 0xFF;
            int m = (emailDataLen >> 8) & 0xFF;


            MessageDTO message = new MessageDTO(email, l , m);
            sendVersionAndCode(sOut, 5);
            sendEmail(message, sOut);

            byte responseCode =0;
            // Read response from server
            sIn.readByte();
            responseCode = sIn.readByte();

            if(responseCode != 3 && responseCode != 0){
                NotificationFactory factory = new NotificationFactory();

                Notification notif = factory.newNotification(user, jobApplication.getCandidate().associatedUser(), ("Verification Process Results \n The verification process for you job application has concluded, the outcome was: " + jobApplication.getState().toString()) ,NotificationTypes.EMAIL);
                notifRepo.save(notif);
            }


        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            closeSocket();
        }



    }

    private void sendEmail(MessageDTO message, ObjectOutputStream sOut) throws IOException {
        sOut.writeObject(message);
        sOut.flush();
    }


    private static void sendVersionAndCode(ObjectOutputStream sOut, int code) throws IOException {
        byte version = 1;
        sOut.writeByte(version);
        sOut.writeByte(code);
        sOut.flush();
    }

    private static void sendCredentials(ObjectOutputStream sOut, String username, String password) throws IOException {
        byte[] usernameData = username.getBytes();
        int usernameLen = usernameData.length;
        sOut.writeByte(usernameLen & 0xFF);
        sOut.writeByte((usernameLen >> 8) & 0xFF);
        sOut.write(usernameData);

        byte[] passwordData = password.getBytes();
        int passwordLen = passwordData.length;
        sOut.writeByte(passwordLen & 0xFF);
        sOut.writeByte((passwordLen >> 8) & 0xFF);
        sOut.write(passwordData);

        sOut.flush();
    }

    private static void closeSocket() {
        try {
            if (sock != null && !sock.isClosed()) {
                sock.close();
            }
        } catch (IOException e) {
            System.out.println("Failed to close socket: " + e.getMessage());
        }
    }

}
