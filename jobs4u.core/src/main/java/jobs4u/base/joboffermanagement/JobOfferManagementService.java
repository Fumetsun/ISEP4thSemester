package jobs4u.base.joboffermanagement;

import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.joboffermanagement.domain.JobOfferBuilder;
import jobs4u.base.joboffermanagement.domain.ContractType;
import jobs4u.base.joboffermanagement.domain.JobMode;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.joboffermanagement.domain.JobRefCode;
import jobs4u.base.joboffermanagement.domain.dto.JobOfferDto;
import jobs4u.base.joboffermanagement.repositories.JobOfferRepository;
import jobs4u.base.pluginhandler.domain.RegisteredPlugin;
import jobs4u.base.pluginhandler.domain.RegisteredPluginDTO;
import jobs4u.base.pluginhandler.repositories.RegisteredPluginsRepository;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcess;

import java.util.Optional;

public class JobOfferManagementService {
    private final JobOfferRepository repo = PersistenceContext.repositories().jobOffers();
    private final JobOfferBuilder jobOfferBuilder = new JobOfferBuilder();
    private final JobOfferRepository jobRepo = PersistenceContext.repositories().jobOffers();
    private final RegisteredPluginsRepository plugRepo = PersistenceContext.repositories().plugins();

    public JobOffer addJobOffer(Customer customer, String titleVar, ContractType typeVar, JobMode modeVar,
                                Integer vacanciesVar,
                                String descriptionVar, String addressVar, RegisteredPlugin interviewVar, RegisteredPlugin requirementVar, RecruitmentProcess recruitmentProcess) {

        jobOfferBuilder.withCustomer(customer)
                .withJobTitle(titleVar)
                .withContractType(typeVar)
                .withJobMode(modeVar)
                .withVacancies(vacanciesVar)
                .withDescription(descriptionVar)
                .withAddress(addressVar)
                .withInterviewModel(interviewVar)
                .withRequirementSpecification(requirementVar)
                .withRecruitmentProcess(recruitmentProcess);

        JobOffer job = jobOfferBuilder.build();

        if (job == null) {
            return null;
        }

        return repo.save(job);
    }

    public JobOffer addJobOffer(Customer customer, String titleVar, ContractType typeVar, JobMode modeVar,
                                Integer vacanciesVar,
                                String descriptionVar, String addressVar, RecruitmentProcess recruitmentProcess) {

        jobOfferBuilder.withCustomer(customer)
                .withJobTitle(titleVar)
                .withContractType(typeVar)
                .withJobMode(modeVar)
                .withVacancies(vacanciesVar)
                .withDescription(descriptionVar)
                .withAddress(addressVar)
                .withRecruitmentProcess(recruitmentProcess);

        JobOffer job = jobOfferBuilder.build();

        if (job == null) {
            return null;
        }

        return repo.save(job);
    }

    public Iterable<JobOffer> getOffers() {
        return repo.findAllOffers();
    }

    public Optional<JobOffer> findByRef(JobRefCode joffer) {
        return repo.findByRef(joffer);
    }

    public JobOffer updateJobOfferPlugins(JobOffer offer, RegisteredPluginDTO plugin) {
        JobOfferBuilder builder = new JobOfferBuilder();
        Optional<RegisteredPlugin> reqPlugin = plugRepo.getPluginByName(plugin.getFilePath());
        return jobRepo.save(builder.withJobOffer(offer).update(reqPlugin.get()));
    }
}