package jobs4u.base.app.backoffice.console.presentation.customermanager;

import java.util.ArrayList;
import java.util.List;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.applicationmanagement.application.ListApplicationsController;
import jobs4u.base.applicationmanagement.domain.dto.ApplicationDTO;
import jobs4u.base.customermanagement.domain.dto.CustomerDTO;
import jobs4u.base.joboffermanagement.domain.dto.JobOfferDto;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ListApplicationsUI extends AbstractUI {
	private final ListApplicationsController ctrl = new ListApplicationsController();

	private String pickCustomer() {
		List<CustomerDTO> customers = (ArrayList) ctrl.getCustomers();
		int choice = -1, i = 0;
		System.out.printf("Please pick one of these customers:\n");
		for (CustomerDTO c : customers) {
			i++;
			System.out.printf("--- %d ---\n%s\n", i, c.toString());
		}
		if (i == 0) {
			System.out.println("Current user does not have any assigned customers, returning,");
			return null;
		}
		choice = Console.readInteger("Customer number:");
		if (choice < 0 || choice > i) {
			System.out.println("Invalid choice, returning.");
			return null;
		}
		return customers.get(choice - 1).getCustomerCode();
	}

	private String pickJobOffer(String code) {
		List<JobOfferDto> jobs = (ArrayList) ctrl.getJobOpenings(code);
		int choice = -1, i = 0;
		System.out.printf("Please pick one of these job offers:\n");
		for (JobOfferDto j : jobs) {
			i++;
			System.out.printf("--- %d ---\n%s\n", i, j.toString());
		}
		if (i == 0) {
			System.out.println("Customer does not have any job offers in the system, returning,");
			return null;
		}
		choice = Console.readInteger("Customer number:");
		if (choice < 0 || choice > i) {
			System.out.println("Invalid choice, returning.");
			return null;
		}
		return jobs.get(choice - 1).getRefCode();
	}

	private void showApplications(String code) {
		Iterable<ApplicationDTO> applications = ctrl.getApplications(code);
		System.out.println("Applications for that job opening:\n");
		for (ApplicationDTO a : applications) {
			System.out.println(a.toString());
			System.out.println();
		}
	}

	@Override
	protected boolean doShow() {
		String customerCode = pickCustomer();
		if (customerCode == null) {
			System.out.println("Error picking a customer.");
			return false;
		}
		String jobCode = pickJobOffer(customerCode);
		if (jobCode == null) {
			System.out.println("Error picking a job.");
			return false;
		}
		showApplications(jobCode);
		return false;
	}

	@Override
	public String headline() {
		return "List all applications for a job opening";
	}
}
