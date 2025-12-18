package jobs4u.base.app.backoffice.console.presentation.customermanager;

import java.util.ArrayList;
import java.util.List;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.applicationmanagement.application.GradeInterviewsController;
import jobs4u.base.applicationmanagement.domain.dto.ApplicationDTO;
import jobs4u.base.joboffermanagement.domain.dto.JobOfferDto;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GradeInterviewsUI extends AbstractUI {
	private final GradeInterviewsController ctrl = new GradeInterviewsController();
	
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

	@Override
	protected boolean doShow() {
		JobOfferDto offer;
		List<ApplicationDTO> apps;

		offer = showJobOffers();

		if (offer == null)
			return false;

		if (!ctrl.getPlugin(offer)) {
			System.out.println("No interview model has been selected for that job opening");
			return false;
		}

		if (!ctrl.getApplications(offer)) {
			System.out.println("Selected job offer has no submitted applications.");
			return false;
		}

		apps = (ArrayList) ctrl.gradeInterviews(offer);
		if (apps.isEmpty()) {
			System.out.println("No applications for that job offer have interview answer files.");
			return false;
		}

		System.out.printf("Graded interviews:\n\n");
		apps.forEach(a -> System.out.println(a.toString()));

		return false;
	}

	@Override
	public String headline() {
		return "Grade interview answers.";
	}
}
