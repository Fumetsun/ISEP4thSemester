package jobs4u.base.applicationmanagement.application;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.applicationmanagement.ListJobApplicationService;
import jobs4u.base.applicationmanagement.domain.JobApplication;
import jobs4u.base.applicationmanagement.domain.dto.ApplicationDTO;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.customermanagement.FilteredCustomerListingService;
import jobs4u.base.joboffermanagement.ListJobOfferService;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.joboffermanagement.domain.dto.JobOfferDto;
import jobs4u.base.usermanagement.domain.BaseRoles;

public class ListSortedApplicantsController {
    private final ListJobApplicationService applicationListService = new ListJobApplicationService();
    private final AuthorizationService authorizationService = AuthzRegistry.authorizationService();

    private final ListJobOfferService jobOfferService = new ListJobOfferService();

    public List<JobOffer> findAllOffers(){
        List<JobOffer> allOffersOfCM = new ArrayList<>();
        Iterable<JobOffer> offers = jobOfferService.getJobOffersBySystemUser(authorizationService.loggedinUserWithPermissions(BaseRoles.CUSTOMER_MANAGER).get());
        for(JobOffer job : offers){
            allOffersOfCM.add(job);
        }

        return allOffersOfCM;
    }

    public List<JobApplication> getSortedApplicants(JobOffer job){
        List<JobApplication> applications = new ArrayList<>();

        for(JobApplication app : applicationListService.getJobApplicationsOfJobOfferCodeNonDTO(job.identity())){
            applications.add(app);
        }

        applications.sort(Comparator.comparingInt(JobApplication::getGrade).reversed());
        return applications;
    }
}
