package jobs4u.base.applicationmanagement.application;

import java.util.ArrayList;
import java.util.List;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.applicationmanagement.ListJobApplicationService;
import jobs4u.base.applicationmanagement.domain.JobApplication;
import jobs4u.base.applicationmanagement.domain.dto.ApplicationDTO;
import jobs4u.base.customermanagement.FilteredCustomerListingService;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.customermanagement.domain.dto.CustomerDTO;
import jobs4u.base.joboffermanagement.ListJobOfferService;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.joboffermanagement.domain.dto.JobOfferDto;
import jobs4u.base.usermanagement.domain.BaseRoles;

public class ListApplicationsController {
	private final FilteredCustomerListingService customerListService = new FilteredCustomerListingService();
	private final ListJobOfferService offerListService = new ListJobOfferService();
	private final ListJobApplicationService applicationListService = new ListJobApplicationService();
    private final AuthorizationService authorizationService = AuthzRegistry.authorizationService();
	private Iterable<Customer> customers;
	private Iterable<JobOffer> joboffers;

	private SystemUser getLoggedUser() {
		return authorizationService.loggedinUserWithPermissions(BaseRoles.CUSTOMER_MANAGER).get();
	}

	public Iterable<CustomerDTO> getCustomers() {
		SystemUser manager = getLoggedUser();
		customers = customerListService.allCustomersOfAManager(manager);
		List<CustomerDTO> ret = new ArrayList<>();
		for (Customer c : customers) {
			ret.add(c.toDTO());
		}
		return ret;
	}

	public Iterable<JobOfferDto> getJobOpenings(String code) {
		Customer customer = null;
		List<JobOfferDto> ret = new ArrayList<>();
		for (Customer c : customers) {
			if (c.customerCode().toString().equals(code))
				customer = c;
		}
		joboffers = offerListService.getJobOffersByUser(customer);
		for (JobOffer j : joboffers) {
			ret.add(j.toDTO());
		}
		return ret;
	}

	public Iterable<ApplicationDTO> getApplications(String code) {
		JobOffer jobOffer = null;
		List<ApplicationDTO> ret =  new ArrayList<>();
		for (JobOffer j : joboffers) {
			if (j.identity().toString().equals(code)) {
				jobOffer = j;
				break;
			}
		}
		Iterable<JobApplication> applications = applicationListService.getJobApplicationsOfJobOfferCodeNonDTO(jobOffer.identity());
		for (JobApplication j : applications) {
			ret.add(j.toDTO());
		}
		return ret;
	}
}
