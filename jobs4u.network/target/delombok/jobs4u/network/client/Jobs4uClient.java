package jobs4u.network.client;



import jobs4u.base.notificationmanagement.domain.MessageDTO;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

class Jobs4uClient {
    private static final int ASS_PORT_NUMBER = 6912;
    private static InetAddress serverIP;
    private static Socket sock;

    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        System.out.println("IPv4/IPv6/DNS: ");
        String ip = read.nextLine();

        try {
            serverIP = InetAddress.getByName(ip);
        } catch (UnknownHostException ex) {
            System.out.println("Invalid server specified: " + ip);
            System.exit(1);
        }

        try {
            sock = new Socket(serverIP, ASS_PORT_NUMBER);
        } catch (IOException ex) {
            System.out.println("Failed to establish TCP connection");
            System.exit(1);
        }

        try (ObjectOutputStream sOut = new ObjectOutputStream(sock.getOutputStream());
             ObjectInputStream sIn = new ObjectInputStream(sock.getInputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {

            String input;
            do {
                input = getUserInput(in);

                if (input == null || input.isEmpty()) {
                    continue;
                }

                int code;
                try {
                    code = Integer.parseInt(input);
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid number");
                    continue;
                }

                if (code < 0) {
                    System.out.println("Please enter a positive integer.");
                    continue;
                }

                sendVersionAndCode(sOut, code);

                if (code == 5) {
                    // Simulating sending a Message object
                    MessageDTO message = new MessageDTO("1220962@isep.ipp.pt/1220976@isep.ipp.pt/SMTP TEST/Hello Mr. Smurf Asterix 7",0,0);
                    sOut.writeObject(message);
                } else if (code == 4) {
                    // Simulating sending username and password for authentication
                    sendCredentials(sOut, "cm@email.org", "!CManager1");
                }else if (code == 6) {
                    // Simulating sending username and password for authentication
                    //sendQuery(sOut);
                } else {
                    sendEmptyData(sOut);
                }

                sOut.flush();

                // Read response from server
                byte response = sIn.readByte();
                System.out.println("Server response: " + response);

            } while (!input.equals("0"));

        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            closeSocket();
        }
    }



    private static String getUserInput(BufferedReader in) throws IOException {
        System.out.print("Enter a system code (positive integer): ");
        return in.readLine();
    }

    private static void sendVersionAndCode(ObjectOutputStream sOut, int code) throws IOException {
        byte version = 1;
        sOut.writeByte(version);
        sOut.writeByte(code);
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
    }

    private static void sendEmptyData(ObjectOutputStream sOut) throws IOException {
        sOut.writeByte(0);
        sOut.writeByte(0);
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

// Example serializable class



















