package jobs4u.base.joboffermanagement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.customermanagement.FilteredCustomerListingService;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.joboffermanagement.ListJobOfferService;
import jobs4u.base.joboffermanagement.JobOfferManagementService;
import jobs4u.base.joboffermanagement.domain.ContractType;
import jobs4u.base.joboffermanagement.domain.JobMode;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.pluginhandler.ManagePluginService;
import jobs4u.base.pluginhandler.domain.RegisteredPlugin;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.util.ArrayList;
import java.util.List;

public class ListCustomerOffersController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final FilteredCustomerListingService customerListingService = new FilteredCustomerListingService();
    private final ListJobOfferService jobOfferService = new ListJobOfferService();

    public List<JobOffer> jobOfferList(){
        SystemUser a = authz.loggedinUserWithPermissions(BaseRoles.CUSTOMER).get();
        //return jobOfferService.getJobOffersByUser();
        return null;
    }
}
