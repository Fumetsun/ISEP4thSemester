package jobs4u.base.notificationmanagement;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.applicationmanagement.ListJobApplicationService;
import jobs4u.base.applicationmanagement.domain.ApplicationState;
import jobs4u.base.applicationmanagement.domain.JobApplication;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.joboffermanagement.domain.JobRefCode;
import jobs4u.base.notificationmanagement.domain.MessageDTO;
import jobs4u.base.notificationmanagement.domain.Notification;
import jobs4u.base.notificationmanagement.domain.NotificationFactory;
import jobs4u.base.notificationmanagement.domain.NotificationTypes;
import jobs4u.base.notificationmanagement.repositories.NotificationRepository;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ResultsNotificationHandler extends Thread{
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private static final int ASS_PORT_NUMBER = 6912;
    private static InetAddress serverIP;
    private static Socket sock;
    private String serverIp = "10.8.0.80";
    private String password;
    private String jobRef;


    public ResultsNotificationHandler(String jobRef, String password) {
        this.jobRef = jobRef;
        this.password = password;
    }

    public ResultsNotificationHandler(String password) {
        this.password = password;
    }

    public Iterable<MessageDTO> getJobOffers() throws ClassNotFoundException{

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

        try {
            ObjectInputStream sIn = new ObjectInputStream(sock.getInputStream());
            ObjectOutputStream sOut = new ObjectOutputStream(sock.getOutputStream());
            SystemUser user = authz.loggedinUserWithPermissions(BaseRoles.CUSTOMER_MANAGER).get();

            synchronized (sOut) {
                sendVersionAndCode(sOut, 4);
                sendCredentials(sOut, user.username().toString(), password);
            }

            byte responseCode = 0;
            // Read response from server
            responseCode = sIn.readByte();
            if(responseCode != 3 && responseCode != 0){
                sOut.writeByte(1);
                sOut.writeByte(10);
                sOut.writeByte(2);

                sOut.flush();

                Iterable<MessageDTO> jobOffers = (Iterable<MessageDTO>) sIn.readObject();

                sIn.readByte();
                sIn.readByte();

                closeSocket();
                return jobOffers;
            }
            return new ArrayList<MessageDTO>();
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
            return null;
        } finally {
            closeSocket();
        }
    }

    public void emailCandidates() {

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

            synchronized (sOut){
                sendVersionAndCode(sOut, 4);
                sendCredentials(sOut, user.username().toString(), password);
            }

            String email = user.email().toString() + "/" + jobRef;

            byte[] emailData = email.getBytes();
            int emailDataLen = emailData.length;
            int l = emailDataLen & 0xFF;
            int m = (emailDataLen >> 8) & 0xFF;

            MessageDTO message = new MessageDTO(email, l , m);

            synchronized (sOut){
                sendVersionAndCode(sOut, 10);
                sOut.writeByte(1);
                sendEmail(message, sOut);
            }

            byte responseCode = 0;
            // Read response from server
            sIn.readByte();
            responseCode = sIn.readByte();


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
