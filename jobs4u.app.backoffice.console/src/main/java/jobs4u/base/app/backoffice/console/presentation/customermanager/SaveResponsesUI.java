 package jobs4u.base.app.backoffice.console.presentation.customermanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.applicationmanagement.application.SaveResponsesController;
import jobs4u.base.applicationmanagement.domain.dto.ApplicationDTO;
import jobs4u.base.joboffermanagement.domain.dto.JobOfferDto;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class SaveResponsesUI extends AbstractUI {
	private final SaveResponsesController ctrl = new SaveResponsesController();

	private JobOfferDto showJobOffers() {
		List<JobOfferDto> offers = (ArrayList) ctrl.getJobOffers();
		int choice = -1, i = 0;
		System.out.printf("Please choose one of these job offers.\n\n");
		for (JobOfferDto o : offers) {
			i++;
			System.out.printf("--- %d ---\n%s\n", i, o.toString());
		}
		if (i == 0) {
			System.out.println("Current user has no associated job offers.");
			return null;
		}
		choice = Console.readInteger("Offer number: ");
		if (choice < 0 || choice > i) {
			System.out.println("Invalid choice, returning.");
			return null;
		}
		return offers.get(choice - 1);
	}

	private ApplicationDTO showApplications(JobOfferDto offer) {
		List<ApplicationDTO> apps = (ArrayList) ctrl.getJobApplicationsOfJobOffer(offer);
		int choice = -1, i = 0;
		System.out.printf("Please choose one of these applications.\n\n");
		for (ApplicationDTO a : apps) {
			i++;
			System.out.printf("--- %d ---\n%s\n", i, a.toString());
		}
		if (i == 0) {
			System.out.println("Current user has no associated job offers.");
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
	public boolean doShow() {
		JobOfferDto offer;
		String file;
		ApplicationDTO app;

		offer = showJobOffers();

		if (offer == null)
			return false;

		if (!ctrl.checkInterview(offer)) {
			System.out.println("No interview model has been selected for that job offer, returning.");
			return false;
		}

		app = showApplications(offer);
		if (app == null)
			return false;
		
		if (!ctrl.getJobApplicationByDTO(app)) {
			System.out.println("Error getting application");
			return false;
		}

		if (ctrl.checkAnswerAlreadyExists(app)) {
			int choice = Console.readInteger("An answer file already do you want to delete it?\n\t1 - yes\n\t2 - no\n");
			if (choice == 1) {
				if (!ctrl.deleteOldAnswers(app)) {
					System.out.println("Error deleting file, continuing.");
				}
			}
			else {
				System.out.println("Returning.");
			}
		}

		file = Console.readLine("Path to answers file: ");
		boolean valid = false;
		try {
			valid = ctrl.validateResponses(file);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return false;
		}
		if (!valid) {
			System.out.println("File is not valid for selected job offer.");
			return false;
		}

		try {
			ctrl.saveResponses(app, file);
		} catch (IOException e) {
			System.out.printf("Unexpected error.\n%s\n", e.toString());
			e.printStackTrace();
			return false;
		}

		return false;
	}

	@Override
	public String headline() {
		return "Save a candidate's interview answers.";
	}

}
