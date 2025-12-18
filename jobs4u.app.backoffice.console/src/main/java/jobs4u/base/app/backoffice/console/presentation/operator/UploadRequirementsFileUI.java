package jobs4u.base.app.backoffice.console.presentation.operator;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.applicationmanagement.application.UploadRequirementsFileController;
import jobs4u.base.applicationmanagement.domain.dto.ApplicationDTO;
import jobs4u.base.joboffermanagement.domain.dto.JobOfferDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UploadRequirementsFileUI extends AbstractUI {

    private final UploadRequirementsFileController controller = new UploadRequirementsFileController();

    @Override
    protected boolean doShow() {
        JobOfferDto jobOfferDto;
        String file;
        ApplicationDTO applicationDTO;

        jobOfferDto = showJobOffers();

        if (jobOfferDto == null)
            return false;

        if (!controller.checkRequirements(jobOfferDto)) {
            System.out.println("No requirements model has been selected for that job offer, returning.");
            return false;
        }

        applicationDTO = showApplications(jobOfferDto);
        if (applicationDTO == null)
            return false;

        String currentRequirementFile = controller.checkIfApplicationHasRequirementsFile(applicationDTO);

        if (currentRequirementFile!=null) {
            if (!applicationHasRequirementsFile(applicationDTO, currentRequirementFile)){
                return false;
            }
        }

        file = Console.readLine("Path to answers file: ");
        boolean operationSucess;

        try {
            operationSucess = controller.validateResponses(file);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }

        if (operationSucess){
            try {
                controller.saveResponses(applicationDTO, file);
                System.out.println("Operation Success: File validated and saved.");
            } catch (IOException e) {
                System.out.println("Operation Failed: File validated but an unexpected error occurred while saving.\n" + e.getMessage() + "\n");
            }
        } else {
            System.out.println("Operation Failed: File couldn't be validated.");
        }

        return false;
    }

    private boolean applicationHasRequirementsFile(ApplicationDTO applicationDTO, String file) {
        System.out.println("The application you choose already has a job requirements file attached to it.");

        String answer;
        do {
            answer = Console.readLine("Do you wish to delete the current one? (Yes/No)");

            if (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no") && !answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n")) {
                System.out.println("Invalid answer, try again.");
            }
        } while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no") && !answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n"));

        if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")){
            System.out.println(controller.deleteCurrentRequirementFile(applicationDTO, file));
            return true;
        }
        return false;
    }

    private JobOfferDto showJobOffers() {
        List<JobOfferDto> offers = (ArrayList) controller.getJobOffers();
        int choice = -1, i = 0;
        System.out.printf("Please choose one of these job offers.\n\n");
        if (offers.isEmpty()) {
            System.out.println("There are no available job offers.");
            return null;
        }

        for (JobOfferDto o : offers) {
            i++;
            System.out.println(i + ". " + o.toString());
        }

        do {
            choice = Console.readInteger("Offer number: ");
            if (choice < 0 || choice > i) {
                System.out.println("Invalid choice, try again.\n");
            }
        } while (choice < 0 || choice > i);
        return offers.get(choice - 1);
    }

    private ApplicationDTO showApplications(JobOfferDto offer) {
        List<ApplicationDTO> apps = (ArrayList) controller.getJobApplicationsOfJobOffer(offer);
        int choice = -1, i = 0;
        System.out.printf("Please choose one of these applications.\n\n");
        for (ApplicationDTO a : apps) {
            i++;
            System.out.printf("--- %d ---\n%s\n", i, a.toString());
        }
        if (i == 0) {
            System.out.println("There are no job applications for this .");
            return null;
        }
        choice = Console.readInteger("Application number: ");
        if (choice < 0 || choice > i) {
            System.out.println("Invalid choice, returning.");
            return null;
        }
        return apps.get(choice - 1);
    }

    @Override
    public String headline() {
        return "Import JobRequirement of a Candidate";
    }
}
