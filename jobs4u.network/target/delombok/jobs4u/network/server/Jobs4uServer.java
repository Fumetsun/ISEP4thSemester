package jobs4u.network.server;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.application.AuthenticationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import jobs4u.base.applicationmanagement.domain.ApplicationState;
import jobs4u.base.applicationmanagement.domain.JobApplication;
import jobs4u.base.applicationmanagement.repositories.JobApplicationRepository;
import jobs4u.base.applicationmanagement.domain.JobApplication;
import jobs4u.base.applicationmanagement.repositories.JobApplicationRepository;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.candidatemanagement.repositories.CandidateRepository;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.customermanagement.repositories.CustomerRepository;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.joboffermanagement.domain.JobRefCode;
import jobs4u.base.joboffermanagement.repositories.JobOfferRepository;
import jobs4u.base.notificationmanagement.domain.*;
import jobs4u.base.notificationmanagement.repositories.NotificationRepository;
import jobs4u.base.rankingmanagement.domain.Ranking;
import jobs4u.base.rankingmanagement.repositories.RankingRepository;
import jobs4u.base.usermanagement.domain.BasePasswordPolicy;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.io.*;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

public class Jobs4uServer {
    private static final int ASS_PORT_NUMBER = 6912;

    private NotificationRepository notifRepo = PersistenceContext.repositories().notifications();

    // Declare pendingNotifications as a non-static field
    private ArrayList<Notification> pendingNotifications = (ArrayList<Notification>) notifRepo.findAllNotifications();

    public static void main(String[] args) {
        Jobs4uServer server = new Jobs4uServer();
        server.connect();
    }

    private void connect(){
        try (ServerSocket serverSocket = new ServerSocket(ASS_PORT_NUMBER)) {
            System.out.println("Server listening on port " + ASS_PORT_NUMBER);

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Connection established to end node " + clientSocket.getInetAddress().getHostAddress() + " on port " + clientSocket.getPort());

                    new Thread(new ClientHandler(clientSocket, pendingNotifications)).start();

                    // Use pendingNotifications here
                } catch (IOException e) {
                    System.out.println("Error accepting connection: " + e.getMessage());
                }
            }
        } catch (IOException ex) {
            System.out.println("Failed to open server socket\n" + ex.getMessage());
            System.exit(1);
        }
    }
}
