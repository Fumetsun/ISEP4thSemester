package jobs4u.base.joboffermanagement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import jobs4u.base.customermanagement.FilteredCustomerListingService;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.joboffermanagement.JobOfferManagementService;
import jobs4u.base.joboffermanagement.domain.ContractType;
import jobs4u.base.joboffermanagement.domain.JobMode;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.pluginhandler.ManagePluginService;
import jobs4u.base.pluginhandler.domain.RegisteredPlugin;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentPhase;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcess;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcessFactory;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.util.ArrayList;
import java.util.List;

public class RegisterJobOfferController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final FilteredCustomerListingService customerListingService = new FilteredCustomerListingService();
    private final ManagePluginService pluginService = new ManagePluginService();
    private final JobOfferManagementService jobOfferService = new JobOfferManagementService();
    private final RecruitmentProcessFactory processFactory = new RecruitmentProcessFactory();

    public JobOffer createJobOffer(Customer customer, String titleVar, ContractType typeVar, JobMode modeVar, Integer vacanciesVar,
                               String descriptionVar, String addressVar, RegisteredPlugin interviewModel, RegisteredPlugin requirementSpecification, RecruitmentProcess recruitmentProcess){
        return jobOfferService.addJobOffer(customer, titleVar, typeVar, modeVar, vacanciesVar,
                descriptionVar, addressVar, interviewModel, requirementSpecification, recruitmentProcess);
    }


    public JobOffer createJobOfferWithoutPlugins(Customer customer, String titleVar, ContractType typeVar, JobMode modeVar, Integer vacanciesVar,
                                   String descriptionVar, String addressVar, RecruitmentProcess recruitmentProcess){
        return jobOfferService.addJobOffer(customer, titleVar, typeVar, modeVar, vacanciesVar,
                descriptionVar, addressVar, recruitmentProcess);
    }

    public List<RegisteredPlugin> getPluginList() {
        List<RegisteredPlugin> pluginList = new ArrayList<>();
        for(RegisteredPlugin plugin : pluginService.allPlugins()){
            pluginList.add(plugin);
        }
        return pluginList;
    }

    public Iterable<Customer> getCustomerList(){
        return customerListingService.allCustomersOfAManager(authz.loggedinUserWithPermissions(BaseRoles.CUSTOMER_MANAGER).get());
    }

    public RecruitmentProcess createRecruitmentProcess(ArrayList<RecruitmentPhase> recruitmentPhases, RecruitmentPhase currentPhase) {
        return processFactory.createRecruitmentProcess(recruitmentPhases, currentPhase);
    }
}
