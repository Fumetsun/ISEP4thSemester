package jobs4u.base.applicationmanagement;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.applicationmanagement.domain.*;
import jobs4u.base.applicationmanagement.domain.dto.ApplicationDTO;
import jobs4u.base.applicationmanagement.repositories.JobApplicationRepository;
import jobs4u.base.candidatemanagement.CandidateManagementService;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.candidatemanagement.domain.dto.CandidateDto;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.joboffermanagement.JobOfferManagementService;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.joboffermanagement.domain.dto.JobOfferDto;
import jobs4u.base.pluginhandler.PluginHandlerService;
import jobs4u.base.pluginhandler.domain.RegisteredPlugin;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@SuppressWarnings({"rawtypes", "unused"})
public class JobApplicationManagementService {
    JobOfferManagementService jobOfferManagementService = new JobOfferManagementService();
    CandidateManagementService candidateManagementService = new CandidateManagementService();
    PluginHandlerService pluginHandlerService = new PluginHandlerService();

    private final CandidateManagementService candidatesvc = new CandidateManagementService();
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final JobApplicationRepository repo = PersistenceContext.repositories().jobApplications();

    public List<Iterable> getData() {
        Iterable<JobOffer> offers = jobOfferManagementService.getOffers();

        Iterable<Candidate> candidates = candidateManagementService.getCandidates();

        List<Iterable> data;

        List<JobOfferDto> offersData = new ArrayList<>();

        for (JobOffer o : offers) {
            offersData.add(o.toDTO());
        }

        List<CandidateDto> candidateData = new ArrayList<>();

        for (Candidate o : candidates) {
            candidateData.add(o.toDTO());
        }

        List<Iterable> list = new ArrayList<>();
        list.add(offersData);
        list.add(candidateData);

        return list;
    }

    public JobApplication addApplication(JobOffer jobOffer, Candidate candidate) {

        ApplicationBuilder builder = new ApplicationBuilder();
        String filesPath = "processedFiles/" + candidate.associatedUser().name().firstName() + "_"
                + candidate.associatedUser().name().lastName() + "/";
        String attachedFiles = "";

        attachedFiles = copyFileNamesToString(filesPath);

        if (jobOffer == null || candidate == null) {
            return null;
        } else {
            builder
                    .withCandidate(candidate)
                    .withJobOffer(jobOffer)
                    .withEmail(new ApplicationEmail(extractInfoFromEmailFile(filesPath)))
                    .withFilesPath(new ApplicationFilesPath(filesPath))
                    .withAttachedFile(new ApplicationAttachedFile(attachedFiles))
                    .withApplicationState(ApplicationState.OPEN)
                    .withInterviewGrade(new InterviewGrade(-1));
            return repo.save(builder.build());
        }
    }

    public static String copyFileNamesToString(String directoryPath) {
        File directory = new File(directoryPath);

        // Check if the given path is a directory
        if (!directory.isDirectory()) {
            System.err.println("Error: The given path is not a directory.");
            return null;
        }

        File[] files = directory.listFiles();

        // Check if the directory is empty
        if (files == null || files.length == 0) {
            System.err.println("Error: The directory is empty.");
            return null;
        }

        // Create a StringBuilder to store file names
        StringBuilder stringBuilder = new StringBuilder();

        // Iterate over the files and append their names to the StringBuilder
        for (File file : files) {
            stringBuilder.append(file.getName()).append("\n");
        }

        // Convert StringBuilder to String and return
        return stringBuilder.toString();
    }

    public static String extractInfoFromEmailFile(String directoryPath) {
        StringBuilder infoStringBuilder = new StringBuilder();

        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.getName().contains("-email.txt")) {
                    try {

                        StringBuilder contentBuilder = new StringBuilder();
                        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                            for (int i = 0; i < 7; i++) {
                                reader.readLine();
                            }
                            String line;
                            while ((line = reader.readLine()) != null) {
                                contentBuilder.append(line).append("\n");
                            }
                        }
                        return contentBuilder.toString();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return "NOTHING";
    }

    public JobApplication addApplication(JobOffer jobOffer, String candidateOption) {
        Candidate candidate = candidatesvc.importCandidateInfo(candidateOption);

        ApplicationBuilder builder = new ApplicationBuilder();
        String filesPath = "processedFiles/" + candidate.associatedUser().name().firstName() + "_"
                + candidate.associatedUser().name().lastName() + "/";
        String attachedFiles = "";

        attachedFiles = copyFileNamesToString(filesPath);

        if (jobOffer == null || candidate == null) {
            return null;
        } else {
            builder
                    .withCandidate(candidate)
                    .withJobOffer(jobOffer)
                    .withEmail(new ApplicationEmail(extractInfoFromEmailFile(filesPath)))
                    .withFilesPath(new ApplicationFilesPath(filesPath))
                    .withAttachedFile(new ApplicationAttachedFile(attachedFiles));

            return repo.save(builder.build());
        }
    }

    public void saveResponses(JobApplication app, String file) {
        app.addFileRef(file);
        repo.save(app);
    }

    public Iterable<JobApplication> getMyCandidates() {
        SystemUser user = authz.loggedinUserWithPermissions(BaseRoles.CUSTOMER_MANAGER).get();
        return repo.getCustomerManagerApplications(user);
    }

    public Iterable<ApplicationDTO> gradeAndSave(List<JobApplication> apps, String interview) {
        String arr[], helper;
        boolean graded;
        int grade, helper2;
        List<ApplicationDTO> ret = new ArrayList<>();
        Pattern nonAnswersPattern = Pattern.compile(
                "^\\d+-((candidate-data|cv|email|letter)|(big-file-\\d+|report-\\d+)){1}(.txt)$",
                Pattern.CASE_INSENSITIVE);

        for (JobApplication a : apps) {
            if (a.getState() != ApplicationState.MR)
                continue;

            arr = a.getFileRef().split("\n");
            grade = 0;
            graded = false;

            for (String s : arr) {

                if (!s.contains("_INTERVIEW") || nonAnswersPattern.matcher(s).matches())
                    continue;

                helper = a.getFilePath();
                if (!(helper.endsWith("/") || helper.endsWith("\\")))
                    helper += File.separator;
                helper += s;

                grade = pluginHandlerService.activateEvaluationFunction(interview, helper, 2);
                graded = true;
                break;
            }

            if (!graded || (graded && grade < 0))
                continue;

            a.setGrade(grade);
            a.setState(ApplicationState.GRADED);
            repo.save(a);
            ret.add(a.toDTO());
        }
        return ret;
    }

    public String removeFile(JobApplication app, String fileName) {
        File file = new File(app.getFilePath() + fileName);

        if (file.exists()) {
            if (file.delete()) {
                app.removeFile(fileName);
                repo.save(app);
                return "File deleted successfully.";
            } else {
                return "[Error] Failed to delete the file.";
            }
        } else {
            return "[Error] File does not exist.";
        }
    }

    public String validateAndSave(List<ApplicationDTO> applicationDTOS, RegisteredPlugin requirementsModel) {
        StringBuilder operationReport = new StringBuilder();

        boolean validated;
        int result = -5;

        for (ApplicationDTO appDto : applicationDTOS) {
            if (!appDto.getState().equalsIgnoreCase(ApplicationState.OPEN.toString()) && !appDto.getState().equalsIgnoreCase(ApplicationState.PROCESSING.toString()))
                continue;

            String[] files = appDto.getAttachedFile().split("\n");

            validated = false;
            for (String filePath : files) {

                if (!filePath.contains("_JOBREQUIREMENTS"))
                    continue;

                String jobRequirementFilePath = appDto.getFilesPath();
                if (!(jobRequirementFilePath.endsWith("/") || jobRequirementFilePath.endsWith("\\")))
                    jobRequirementFilePath += File.separator;
                jobRequirementFilePath += filePath;

                validated = true;
                result = pluginHandlerService.activateEvaluationFunction(requirementsModel.fileName().toString(), jobRequirementFilePath, 1);
                break;
            }

            if (!validated || result==-5)
                continue;

            JobApplication application = repo.findByRefCode(appDto.getRefCode()).get();

            if (result == 1) {
                application.setState(ApplicationState.MR);
                operationReport.append("Application '"+ application.identity() + "':\n\tRequirements Verification: Candidate Passed.\n");
            } else {
                application.setState(ApplicationState.DNMR);
                operationReport.append("Application '"+ application.identity() + "':\n\tRequirements Verification: Candidate Failed.\n");
            }
            repo.save(application);

        }
        return operationReport.toString();
    }

    public List<ApplicationDTO> applicationsOfCandidate(SystemUser user){
        Iterable<JobApplication> jobApps = repo.findApplicationsByCandidate(user);
        List<ApplicationDTO> appDTO = new ArrayList<>();
        for(JobApplication app : jobApps){
            appDTO.add(app.toDTO());
        }
        return appDTO;
    }

}
