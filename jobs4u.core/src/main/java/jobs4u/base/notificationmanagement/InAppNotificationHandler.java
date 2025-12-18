package jobs4u.base.notificationmanagement;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import jobs4u.base.infrastructure.persistence.PersistenceContext;

import jobs4u.base.notificationmanagement.domain.MessageDTO;

import jobs4u.base.notificationmanagement.repositories.NotificationRepository;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class InAppNotificationHandler{
    private final AuthorizationService authz = AuthzRegistry.authorizationService();


    Integer option;
    private static final int ASS_PORT_NUMBER = 6912;
    private static InetAddress serverIP;
    private static Socket sock;
    private String password;
    private String serverIp = "10.8.0.80";

    public InAppNotificationHandler(String password, Integer option) {
        this.password = password;
        this.option = option;
    }


    public Iterable<MessageDTO> getMyNotifications() {


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













            SystemUser user = authz.loggedinUserWithPermissions(BaseRoles.CANDIDATE).get();

            sendVersionAndCode(sOut, 4);
            sendCredentials(sOut, user.username().toString(), password);

            byte responseCode =0;
            // Read response from server
            responseCode = sIn.readByte();

            if(responseCode != 3 && responseCode != 0){
                sOut.writeByte(1);

                if(this.option ==1){
                    sOut.writeByte(13);
                }else{
                    sOut.writeByte(8);
                }

                sOut.flush();

                sIn.readByte();
                sIn.readByte();

                Iterable<MessageDTO> notifs = (Iterable<MessageDTO>) sIn.readObject();

                sOut.writeByte(1);
                sOut.writeByte(1);
                sOut.flush();

                closeSocket();
                return notifs;
            }



        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFound Exception: " + e.getMessage());
        }

        closeSocket();
        return null;
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
