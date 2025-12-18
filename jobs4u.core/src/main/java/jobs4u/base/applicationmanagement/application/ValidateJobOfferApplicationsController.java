package jobs4u.base.applicationmanagement.application;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import jobs4u.base.applicationmanagement.JobApplicationManagementService;
import jobs4u.base.applicationmanagement.ListJobApplicationService;
import jobs4u.base.applicationmanagement.domain.dto.ApplicationDTO;
import jobs4u.base.joboffermanagement.ListJobOfferService;
import jobs4u.base.joboffermanagement.domain.JobRefCode;
import jobs4u.base.joboffermanagement.domain.dto.JobOfferDto;
import jobs4u.base.pluginhandler.domain.RegisteredPlugin;
import jobs4u.base.recruitmentprocessmanagement.domain.OperationMadeEvent;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ValidateJobOfferApplicationsController {

    private final AuthorizationService authService = AuthzRegistry.authorizationService();
    private final ListJobOfferService offerService = new ListJobOfferService();
    private final ListJobApplicationService listApplicationService = new ListJobApplicationService();
    private final JobApplicationManagementService applicationService = new JobApplicationManagementService();
    private final EventPublisher dispatcher = InProcessPubSub.publisher();

    public Iterable<JobOfferDto> getJobOffers() {
        authService.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
        SystemUser manager = authService.loggedinUserWithPermissions(BaseRoles.CUSTOMER_MANAGER).get();
        return offerService.getJobOffersOfManager(manager);
    }

    public boolean checkRequirements(JobOfferDto offerdto) {
        authService.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
        Optional<RegisteredPlugin> plugin = getRequirements(offerdto);
        return plugin.isPresent();
    }

    public Optional<RegisteredPlugin> getRequirements(JobOfferDto offerdto){
        return offerService.getJobRequirementsOfJobOffer(new JobRefCode(Integer.parseInt(offerdto.getRefCode())));
    }

    public Iterable<ApplicationDTO> getJobApplicationsOfJobOffer(JobOfferDto offerdto) {
        authService.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
        return listApplicationService.getJobApplicationsOfJobOfferCode(new JobRefCode(Integer.parseInt(offerdto.getRefCode())));
    }

    public String validateAllApplicationsOfJobOffer(JobOfferDto offerDto) throws IllegalArgumentException {
        authService.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);

        RegisteredPlugin requirementsModel = getRequirements(offerDto).get();
        List <ApplicationDTO> applicationDTOS = (ArrayList) getJobApplicationsOfJobOffer(offerDto);

        String report = applicationService.validateAndSave(applicationDTOS,requirementsModel);

        if (!report.isEmpty()){
            //US 1010 - Operation Tracking
            final DomainEvent event = new OperationMadeEvent(new JobRefCode(Integer.parseInt(offerDto.getRefCode())));
            dispatcher.publish(event);
            //============================
        }

        return report;
    }

    public boolean checkJobApplicationsOfJobOffer(JobOfferDto offerdto) {
        authService.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
        List<ApplicationDTO> list = (ArrayList) listApplicationService.getJobApplicationsOfJobOfferCode(new JobRefCode(Integer.parseInt(offerdto.getRefCode())));

        return !list.isEmpty();
    }
}
