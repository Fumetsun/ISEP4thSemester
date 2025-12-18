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
import jobs4u.base.recruitmentprocessmanagement.RecruitmentPhaseHandler;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentPhase;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcess;
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


class ClientHandler implements Runnable {

    NotificationRepository notifRepo = PersistenceContext.repositories().notifications();

    UserRepository usersRepo = PersistenceContext.repositories().users();

    JobOfferRepository offerRepo = PersistenceContext.repositories().jobOffers();

    JobApplicationRepository appRepo = PersistenceContext.repositories().jobApplications();
    CustomerRepository customerRepo = PersistenceContext.repositories().customers();
    CandidateRepository candidateRepository = PersistenceContext.repositories().candidate();

    JobApplicationRepository jobApplicationRepository = PersistenceContext.repositories().jobApplications();

    RankingRepository rankingRepository = PersistenceContext.repositories().rankings();

    TransactionalContext ctx = PersistenceContext.repositories().newTransactionalContext();

    private Socket clientSocket;
    private ObjectOutputStream socketOut;
    private ObjectInputStream socketIn;

    private String threadUser;

    private ArrayList<Notification> pendingNotifications;

    boolean running = true;

    private byte data_len_l;
    private byte data_len_m;
    private byte[] buffer;

    public ClientHandler(Socket clientSocket, ArrayList<Notification> pendingNotifications) {
        this.clientSocket = clientSocket;
        this.pendingNotifications = pendingNotifications;
    }

    @Override
    public void run() {

        AuthzRegistry.configure(PersistenceContext.repositories().users(),
                new BasePasswordPolicy(), new PlainTextEncoder());


        try {
            socketOut = new ObjectOutputStream(clientSocket.getOutputStream());
            socketIn = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (running) {
            try {
                byte version = socketIn.readByte();
                System.out.println("Message of version " + version + " received.");

                byte code = socketIn.readByte();
                System.out.println("Message code " + code + " received.");

                if (code == 0) {
                    socketOut.writeByte(2);
                    socketOut.flush();

                } else if (code == 4) {

                    if (authenticate() == 3) {
                        clientSocket.close();
                        running = false;
                    }

                } else if (code == 5) {

                    MessageDTO message = (MessageDTO) socketIn.readObject();
                    String[] emailInfo = message.getData().split("/");


                    new Thread(new TcpSrvEmailThread(emailInfo[0], emailInfo[1], emailInfo[2], emailInfo[3])).start();

                    sendVersionAndCode(socketOut,2);

                    clientSocket.close();
                    running = false;

                }else if (code == 7) {

                    socketIn.readByte();
                    socketIn.readByte();

                    MessageDTO message = (MessageDTO) socketIn.readObject();


                    sendNotification(message.toString());


                } else if (code == 8) {
                    sendVersionAndCode(socketOut,2);

                    retrieveAllMyNotifications();

                } else if(code == 9){


                    byte result = checkForNotifications();

                    if(result == 1){

                        sendVersionAndCode(socketOut,10);
                    }else{

                        sendVersionAndCode(socketOut,11);
                    }


                }else if(code == 10){

                    byte option = socketIn.readByte();
                    if(option == 1){
                        MessageDTO message = (MessageDTO) socketIn.readObject();
                        String[] offerInfo = message.getData().split("/");
                        emailParticipants(offerInfo);
                    }else{
                        retrieveOffers();
                    }

                    sendVersionAndCode(socketOut,2);

                    clientSocket.close();
                    running = false;

                }else if(code == 11){

                    sendVersionAndCode(socketOut,2);

                    getAllCandidateApplications();

                }else if(code == 12){

                    sendVersionAndCode(socketOut,2);

                    retrieveOffers();

                }else if(code == 13){
                    sendVersionAndCode(socketOut,2);

                    retrieveMyUnseenNotifications();
                } else {
                    socketOut.writeByte(2);
                    socketOut.flush();

                    running = false;

                    clientSocket.close();
                }

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Client connection closed: " + e.getMessage());
                running = false;

            }
        }
    }

    private static void sendVersionAndCode(ObjectOutputStream sOut, int code) throws IOException {
        byte version = 1;
        sOut.writeByte(version);
        sOut.writeByte(code);
        sOut.flush();
    }

    private void emailParticipants(String[] offerInfo) throws IOException, ClassNotFoundException{
        Optional<JobOffer> offer = offerRepo.findByRef(new JobRefCode(Integer.parseInt(offerInfo[1])));
        if(offer.isPresent()){

            String customerEmail = offerInfo[0] + "/" + offer.get().customer().customerUser().email().toString() + "/Application Results" + "/The application has been closed. Here's the list of the candidates:";
            int i = 1;
            int max = Integer.parseInt(offer.get().vacancies().toString());
            for(Ranking rank : rankingRepository.getRankingsofJobOffer(offer.get())) {
                if (rank.placement().value() <= max) {
                    JobApplication app = rank.application();
                    if (app.getState().equals(ApplicationState.ACCEPTED)){
                        String email = offerInfo[0] + "/" + app.getCandidate().associatedUser().email() + "/Application Results" + "/Your application was accepted. You will shortly be contacted by the company to whom you've applied for.";
                        customerEmail += "\n" + rank.placement().value() + " Name: " + app.getCandidate().associatedUser().name() + " Email: " + app.getCandidate().associatedUser().email() + " Phone Number: " + app.getCandidate().identity();

                        String[] emailInfo = email.split("/");
                        new Thread(new TcpSrvEmailThread(emailInfo[0], emailInfo[1], emailInfo[2], emailInfo[3])).start();
                    }
                    if(i==max) break;
                    else i++;
                }
            }
            String[] emailInfo = customerEmail.split("/");
            new Thread(new TcpSrvEmailThread(emailInfo[0], emailInfo[1], emailInfo[2], emailInfo[3])).start();

            RecruitmentPhaseHandler handler = new RecruitmentPhaseHandler();
            handler.updateRecruitmentProcessPhase(offer.get().recruitmentProcess().identity(), (offer.get().recruitmentProcess().phases().size()-1));
        }
    }


    private void retrieveOffers() throws IOException, ClassNotFoundException{
        Optional<SystemUser> user = usersRepo.ofIdentity(Username.valueOf(threadUser));
        ArrayList<MessageDTO> offerData = new ArrayList<>();
        boolean isCustomer = false;
        if(user.isPresent()) {
            Iterable<JobOffer> offers = new ArrayList<>();
            if(user.get().hasAny(BaseRoles.CUSTOMER_MANAGER)){
                offers = offerRepo.findAllOffersOfManager(user.get());
                isCustomer = false;
            }else if(user.get().hasAny(BaseRoles.CUSTOMER)){
                Customer customer = customerRepo.findByUser(user.get());
                offers = offerRepo.findAllOffersOfCustomer(customer);
                isCustomer = true;
            }

            for(JobOffer job : offers){
                String returnMessage = null;
                if(isCustomer && !job.recruitmentProcess().currentPhase().name().equals
                        (job.recruitmentProcess().phases().get(job.recruitmentProcess().phases().size()-1).name())){

                    String date = job.recruitmentProcess().phases().get(0).phaseDatePeriod().split("-")[0].replace("/","-");
                    LocalDate date1 = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-M-yyyy"));
                    LocalDate date2 = LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-M-yyyy")), DateTimeFormatter.ofPattern("dd-M-yyyy"));
                    int daysBetween = (int) DAYS.between(date1, date2);
                    long apps = appRepo.findAllApplicationsOfJobOffer(job.identity()).spliterator().getExactSizeIfKnown();
                    returnMessage = job.identity() + "/" + job.title() + "/" + daysBetween  + "/" + apps;

                }else{
                    if(!job.recruitmentProcess().currentPhase().name().equals
                            (job.recruitmentProcess().phases().get(job.recruitmentProcess().phases().size()-1).name())){
                        returnMessage = job.customer().customerCode() + "/" + job.identity();
                    }
                }

                try{
                    byte[] offerStat = returnMessage.getBytes();
                    int offerStatLen = offerStat.length;
                    int l = offerStatLen & 0xFF;
                    int m = (offerStatLen >> 8) & 0xFF;

                    offerData.add(new MessageDTO(returnMessage, l, m));
                }catch(Exception e){}

            }

            socketOut.writeObject((Iterable<MessageDTO>) offerData);
            socketOut.flush();
        }
    }

    private void retrieveMyUnseenNotifications() throws IOException {
        SystemUser user = usersRepo.ofIdentity(Username.valueOf(threadUser)).get();
        synchronized (pendingNotifications){
            Iterable<Notification> myNotifs = notifRepo.findCandidateUnseenNotifications(user);

            ArrayList<MessageDTO> notificationMessages = new ArrayList<>();

            for(Notification n : myNotifs){
                notificationMessages.add(n.toDTO());
            }


            socketOut.writeObject((Iterable<MessageDTO>) notificationMessages);
            socketOut.flush();

            for(Notification notif: myNotifs){
                notif.updateNotifState(NotificationState.DELIVERED);
                notifRepo.save(notif);
            }

            refreshNotifications();
        }
    }

    private void sendNotification(String notifInfo) throws IOException, ClassNotFoundException {

        socketIn.readByte();
        socketIn.readByte();
        socketIn.readByte();
        socketIn.readByte();
        SystemUser sender = (SystemUser) socketIn.readObject();

        socketIn.readByte();
        socketIn.readByte();
        socketIn.readByte();
        socketIn.readByte();
        SystemUser receiver = (SystemUser) socketIn.readObject();

        NotificationFactory notifFactory = new NotificationFactory();

        notifFactory.newNotification(sender, receiver, notifInfo, NotificationTypes.IN_APP);
        Notification notif = notifFactory.build();
        synchronized (pendingNotifications){
            ctx.beginTransaction();

            notifRepo.save(notif);

            ctx.commit();

            refreshNotifications();
        }
        socketOut.writeByte(1);
        socketOut.writeByte(2);
        socketOut.flush();
    }

    private void retrieveAllMyNotifications() throws IOException, ClassNotFoundException {


        SystemUser user = usersRepo.ofIdentity(Username.valueOf(threadUser)).get();
        synchronized (pendingNotifications){
            Iterable<Notification> myNotifs = notifRepo.findCandidateNotifications(user);

            ArrayList<MessageDTO> notificationMessages = new ArrayList<>();

            for(Notification n : myNotifs){
                notificationMessages.add(n.toDTO());
            }


            socketOut.writeObject((Iterable<MessageDTO>) notificationMessages);
            socketOut.flush();

            for(Notification notif: myNotifs){
                notif.updateNotifState(NotificationState.DELIVERED);
                notifRepo.save(notif);
            }

            refreshNotifications();
        }
    }

    private void refreshNotifications(){
        synchronized (pendingNotifications){
            pendingNotifications = (ArrayList<Notification>) notifRepo.findAllNotifications();
        }
    }

    private byte checkForNotifications() throws IOException {
        synchronized (pendingNotifications){
            refreshNotifications();
            for(Notification notif : pendingNotifications){
                if(notif.receiver().username().toString().equals(threadUser) && notif.state() == NotificationState.SENT){
                    System.out.println("Notification not delivered detected");
                    return 1;
                }
            }
        }

        return 0;
    }

    private int authenticate() {


        try {
            data_len_l = socketIn.readByte();
            System.out.println("Data_Len_L: " + data_len_l);

            data_len_m = socketIn.readByte();
            System.out.println("Data_Len_M: " + data_len_m);

            buffer = new byte[data_len_l + 256 * data_len_m];

            socketIn.readFully(buffer);

            String username = new String(buffer);

            data_len_l = socketIn.readByte();
            System.out.println("Data_Len_L: " + data_len_l);

            data_len_m = socketIn.readByte();
            System.out.println("Data_Len_M: " + data_len_m);

            buffer = new byte[data_len_l + 256 * data_len_m];

            socketIn.readFully(buffer);

            String password = new String(buffer);

            AuthenticationService authenticationService = AuthzRegistry.authenticationService();



            if (authenticationService.authenticate(username, password, BaseRoles.CUSTOMER_MANAGER, BaseRoles.CUSTOMER, BaseRoles.CANDIDATE).isPresent()) {
                socketOut.writeByte(2);
                socketOut.flush();
                this.threadUser = username;
                System.out.println("Authentication successful for user of email: " + username);

                return 4;
            } else {
                socketOut.writeByte(3);
                System.out.println("Authentication failed for user of email: " + username);
                socketOut.flush();
                return 3;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getAllCandidateApplications() throws IOException, ClassNotFoundException{
        Optional<SystemUser> user = usersRepo.ofIdentity(Username.valueOf(threadUser));
        if(user.isPresent()) {
            Optional<Candidate> candidate = candidateRepository.findByRefCode(user.get());

            if(candidate.isPresent()) {
                Iterable<JobApplication> applications = jobApplicationRepository.findApplicationsByCandidate(candidate.get().associatedUser());

                ArrayList<MessageDTO> appMessages = new ArrayList<>();

                for (JobApplication app : applications) {

                    String candidateApplication = "Application '" + app.identity()
                            + "':\n\tState: " + app.getState() +"\n";

                    if (app.getGrade()!=-1){
                        candidateApplication += "\tGrade: " + app.getGrade()+ "\n";
                    }

                    List<JobApplication> applicationsToOffer = (ArrayList) jobApplicationRepository.findAllApplicationsOfJobOffer(app.getJoboffer().identity());

                    candidateApplication += "\tGlobal Applications to that offer: " + applicationsToOffer.size() + "\n";

                    byte[] appData = candidateApplication.getBytes();
                    int appDataLen = appData.length;
                    int l = appDataLen & 0xFF;
                    int m = (appDataLen >> 8) & 0xFF;

                    appMessages.add(new MessageDTO(candidateApplication, l, m));
                }

                socketOut.writeObject(appMessages);
                socketOut.flush();
            }
        }
    }

}


