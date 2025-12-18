package jobs4u.network.server;

import java.io.*;
import java.net.Socket;

class TcpSrvEmailThread implements Runnable {
    private String smtpServer = "frodo.dei.isep.ipp.pt";
    private int SMTP_PORT = 25;
    private String from = "1220962@isep.ipp.pt";
    private String to = "1220716@isep.ipp.pt";
    private String subject = "SMTP Test";
    private String body = "Simple Mail Transfer Protocol (SMTP) is the protocol used to send and relay email messages across networks";


    public TcpSrvEmailThread(String from, String to, String subject, String body) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public void run() {


        try {


            Socket socket = new Socket(smtpServer, SMTP_PORT);
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Read server response
            System.out.println("Response: " + in.readLine());

            // HELO command
            out.println("HELO " + smtpServer);
            System.out.println("HELO Response: " + in.readLine());

            // MAIL FROM command
            out.println("MAIL FROM:<" + from + ">");
            System.out.println("MAIL FROM Response: " + in.readLine());

            // RCPT TO command
            out.println("RCPT TO:<" + to + ">");
            System.out.println("RCPT TO Response: " + in.readLine());

            // DATA command
            out.println("DATA");
            System.out.println("DATA Response: " + in.readLine());

            // Sending the email data
            out.println("Subject: " + subject);
            out.println("To: " + to);
            out.println("From: " + from);
            out.println(); // empty line to indicate end of headers
            out.println(body);
            out.println(".");
            System.out.println("End of Data Response: " + in.readLine());

            // QUIT command
            out.println("QUIT");
            System.out.println("QUIT Response: " + in.readLine());

            socket.close();


        } catch (IOException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }
}
