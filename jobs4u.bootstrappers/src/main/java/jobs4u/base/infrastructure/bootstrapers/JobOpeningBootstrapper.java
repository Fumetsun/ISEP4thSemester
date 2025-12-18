package jobs4u.base.infrastructure.bootstrapers;

import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.joboffermanagement.domain.JobOfferBuilder;
import jobs4u.base.joboffermanagement.domain.ContractType;
import jobs4u.base.joboffermanagement.domain.JobMode;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.joboffermanagement.repositories.JobOfferRepository;
import jobs4u.base.pluginhandler.domain.RegisteredPlugin;

import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentPhase;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcess;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcessFactory;
import jobs4u.base.recruitmentprocessmanagement.repositories.RecruitmentProcessRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class JobOpeningBootstrapper {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(
			JobOpeningBootstrapper.class);

	private final JobOfferBuilder jobOfferBuilder = new JobOfferBuilder();
	private final JobOfferRepository jobRepo = PersistenceContext.repositories().jobOffers();
	private final RecruitmentProcessRepository processRepo = PersistenceContext.repositories().processes();
	private final RecruitmentProcessFactory processFactory = new RecruitmentProcessFactory();

	public JobOffer jobOfferForListingDemo(Customer customer) {
		List<RecruitmentPhase> process = new ArrayList<>();

		process.add(new RecruitmentPhase("Planning","Planning what we will evaluate", "09/1/2000-09/02/2000"));
		process.add(new RecruitmentPhase("Interview","Interview the candidates", "09/3/2000-09/04/2000"));
		process.add(new RecruitmentPhase("Debating","Debating between Members", "09/4/2000-09/05/2000"));
		process.add(new RecruitmentPhase("Interview 2","Interview the finalists", "09/5/2000-09/06/2000"));

		RecruitmentProcess recruitmentProcess1 = processFactory.createRecruitmentProcess(process, process.get(0));

		jobOfferBuilder.withCustomer(customer)
				.withJobTitle("Lifeguard")
				.withContractType(ContractType.PARTTIME)
				.withJobMode(JobMode.ONSITE)
				.withVacancies(3)
				.withDescription("Come save lives 4 us")
				.withAddress("Jobs4u beach, 123")
				.withRecruitmentProcess(recruitmentProcess1);
		JobOffer jobOffer2 = jobOfferBuilder.build();
		jobRepo.save(jobOffer2);

		List<RecruitmentPhase> process2 = new ArrayList<>();
		process2.add(new RecruitmentPhase("Not Planning","Not planning what we will evaluate", "09/1/2000-09/02/2000"));
		process2.add(new RecruitmentPhase("Not Interview","Don't interview the candidates", "09/3/2000-09/04/2000"));
		process2.add(new RecruitmentPhase("Not Debating","Not debating between Members", "09/4/2000-09/05/2000"));
		process2.add(new RecruitmentPhase("Not Interview 2","Don't interview the finalists", "09/5/2000-09/06/2000"));

		RecruitmentProcess recruitmentProcess2 = processFactory.createRecruitmentProcess(process2, process2.get(0));

		jobOfferBuilder.withCustomer(customer)
				.withJobTitle("Jobs4u IT Support")
				.withContractType(ContractType.PARTTIME)
				.withJobMode(JobMode.REMOTE)
				.withVacancies(3)
				.withDescription("Come work 4 us")
				.withAddress("Jobs4u avenue, 123")
				.withRecruitmentProcess(recruitmentProcess2);
		JobOffer jobOffer1 = jobOfferBuilder.build();
		return jobRepo.save(jobOffer1);
	}

	public JobOffer jobOfferForUserResponseSave(Customer customer, RegisteredPlugin interview, RegisteredPlugin requirement) {
		List<RecruitmentPhase> process3 = new ArrayList<>();
		process3.add(new RecruitmentPhase("Maybe Planning","Maybe planning what we will evaluate", "09/1/2000-09/02/2000"));
		process3.add(new RecruitmentPhase("Maybe Interview","Maybe interview the candidates", "09/3/2000-09/04/2000"));
		process3.add(new RecruitmentPhase("Maybe Debating","Maybe debating between Members", "09/4/2000-09/05/2000"));
		process3.add(new RecruitmentPhase("Maybe Interview 2","Maybe interview the finalists", "09/5/2000-09/06/2000"));

		RecruitmentProcess recruitmentProcess3 = processFactory.createRecruitmentProcess(process3, process3.get(0));

		jobOfferBuilder.withCustomer(customer)
				.withJobTitle("Jobs4u Temporary Support")
				.withContractType(ContractType.PARTTIME)
				.withJobMode(JobMode.REMOTE)
				.withVacancies(30)
				.withDescription("Come work 4 us, temporarily")
				.withAddress("Jobs4u boulevard, 123")
				.withInterviewModel(interview)
				.withRequirementSpecification(requirement)
				.withRecruitmentProcess(recruitmentProcess3);
		JobOffer jobOffer3 = jobOfferBuilder.build();
		return jobRepo.save(jobOffer3);
	}

	public JobOffer jobOfferForTesting(Customer customer) {
		List<RecruitmentPhase> process = new ArrayList<>();

		process.add(new RecruitmentPhase("Planning","Planning what we will evaluate", "09/1/2000-09/02/2000"));
		process.add(new RecruitmentPhase("Interview","Interview the candidates", "09/3/2000-09/04/2000"));
		process.add(new RecruitmentPhase("Debating","Debating between Members", "09/4/2000-09/05/2000"));
		process.add(new RecruitmentPhase("Interview 2","Interview the finalists", "09/5/2000-09/06/2000"));

		RecruitmentProcess recruitmentProcess1 = processFactory.createRecruitmentProcess(process, process.get(0));

		jobOfferBuilder.withCustomer(customer)
				.withJobTitle("Test Title")
				.withContractType(ContractType.PARTTIME)
				.withJobMode(JobMode.ONSITE)
				.withVacancies(1)
				.withDescription("Come test 4 us")
				.withAddress("Testing location, 123")
				.withRecruitmentProcess(recruitmentProcess1);
		JobOffer jobOffer2 = jobOfferBuilder.build();
		return jobRepo.save(jobOffer2);
	}
}
