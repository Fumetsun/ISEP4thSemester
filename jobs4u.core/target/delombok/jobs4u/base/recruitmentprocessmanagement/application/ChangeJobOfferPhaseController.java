package jobs4u.base.recruitmentprocessmanagement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.recruitmentprocessmanagement.RecruitmentPhaseHandler;
import jobs4u.base.joboffermanagement.ListJobOfferService;
import jobs4u.base.joboffermanagement.domain.dto.JobOfferDto;
import jobs4u.base.recruitmentprocessmanagement.RecruitmentProcessRepositoryHandler;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcess;
import jobs4u.base.recruitmentprocessmanagement.domain.dto.RecruitmentProcessDTO;
import jobs4u.base.usermanagement.domain.BaseRoles;

public class ChangeJobOfferPhaseController {

    private final AuthorizationService authService = AuthzRegistry.authorizationService();
    private final ListJobOfferService offerService = new ListJobOfferService();
    private final RecruitmentPhaseHandler handler = new RecruitmentPhaseHandler();

    public Iterable<JobOfferDto> getJobOffers() {
        authService.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
        SystemUser manager = authService.loggedinUserWithPermissions(BaseRoles.CUSTOMER_MANAGER).get();
        return offerService.getJobOffersOfManager(manager);
    }

    public String incrementPhase(RecruitmentProcessDTO processDto) {
        return handler.incrementPhase(processDto);
    }

    public String decreasePhase(RecruitmentProcessDTO processDto) {
        return handler.decreasePhase(processDto);
    }

}
