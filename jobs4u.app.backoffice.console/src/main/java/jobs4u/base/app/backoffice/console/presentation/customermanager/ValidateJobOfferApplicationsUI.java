package jobs4u.base.app.backoffice.console.presentation.customermanager;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.applicationmanagement.application.UploadRequirementsFileController;
import jobs4u.base.applicationmanagement.application.ValidateJobOfferApplicationsController;
import jobs4u.base.applicationmanagement.domain.dto.ApplicationDTO;
import jobs4u.base.joboffermanagement.domain.dto.JobOfferDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ValidateJobOfferApplicationsUI extends AbstractUI {
    private final ValidateJobOfferApplicationsController controller = new ValidateJobOfferApplicationsController();

    @Override
    protected boolean doShow() {
        JobOfferDto jobOfferDto;

        jobOfferDto = showJobOffers();

        if (jobOfferDto == null)
            return false;

        if (!controller.checkRequirements(jobOfferDto)) {
            System.out.println("No requirements model has been selected for this Job Offer, returning.");
            return false;
        }

        if (!controller.checkJobApplicationsOfJobOffer(jobOfferDto)){
            System.out.println("No applications have been created for this Job Offer, returning.");
            return false;
        }

        System.out.println(controller.validateAllApplicationsOfJobOffer(jobOfferDto));

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

    @Override
    public String headline() {
        return "Validate the Applications of a JobOffer";
    }
}
