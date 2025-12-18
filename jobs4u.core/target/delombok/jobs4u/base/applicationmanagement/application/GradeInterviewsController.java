package jobs4u.base.applicationmanagement.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import jobs4u.base.applicationmanagement.JobApplicationManagementService;
import jobs4u.base.applicationmanagement.ListJobApplicationService;
import jobs4u.base.applicationmanagement.domain.JobApplication;
import jobs4u.base.applicationmanagement.domain.dto.ApplicationDTO;
import jobs4u.base.joboffermanagement.ListJobOfferService;
import jobs4u.base.joboffermanagement.domain.dto.JobOfferDto;
import jobs4u.base.joboffermanagement.domain.JobRefCode;
import jobs4u.base.pluginhandler.domain.RegisteredPlugin;
import jobs4u.base.recruitmentprocessmanagement.domain.OperationMadeEvent;
import jobs4u.base.usermanagement.domain.BaseRoles;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GradeInterviewsController {
	private final AuthorizationService autService = AuthzRegistry.authorizationService();
	private final ListJobOfferService offerService = new ListJobOfferService();
	private final ListJobApplicationService listApplicationService = new ListJobApplicationService();
	private final JobApplicationManagementService applicationService = new JobApplicationManagementService();
	private final EventPublisher dispatcher = InProcessPubSub.publisher();
	
	public Iterable<JobOfferDto> getJobOffers() {
		autService.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
		SystemUser manager = autService.loggedinUserWithPermissions(BaseRoles.CUSTOMER_MANAGER).get();
		return offerService.getJobOffersOfManager(manager);
	}

	public boolean getPlugin(JobOfferDto offer) {
		autService.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
		Optional<RegisteredPlugin> plugin = offerService
                .getInterviewModelOfJobOffer(new JobRefCode(Integer.parseInt(offer.getRefCode())));
		if (plugin.isEmpty())
			return false;
		return true;
	}

	public boolean getApplications(JobOfferDto offer) {
		autService.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
		List<JobApplication> apps = (ArrayList) listApplicationService
				.getJobApplicationsOfJobOfferCodeNonDTO(new JobRefCode(Integer.parseInt(offer.getRefCode())));
		if (apps.isEmpty())
			return false;
		return true;
	}

	public Iterable<ApplicationDTO> gradeInterviews(JobOfferDto offer) {
		autService.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
		
		RegisteredPlugin interview = offerService
                .getInterviewModelOfJobOffer(new JobRefCode(Integer.parseInt(offer.getRefCode()))).orElse(null);

		List<JobApplication> apps = (ArrayList) listApplicationService
				.getJobApplicationsOfJobOfferCodeNonDTO(new JobRefCode(Integer.parseInt(offer.getRefCode())));
			
		List<ApplicationDTO> dtos = (ArrayList) applicationService.gradeAndSave(apps, interview.fileName().toString());

		final DomainEvent event = new OperationMadeEvent(new JobRefCode(Integer.parseInt(offer.getRefCode())));
		dispatcher.publish(event);

		return dtos;
	}
}
