package jobs4u.base.infrastructure.bootstrapers;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import jobs4u.base.applicationmanagement.domain.*;
import jobs4u.base.candidatemanagement.domain.PhoneNumber;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.joboffermanagement.domain.ContractType;
import jobs4u.base.joboffermanagement.domain.JobMode;
import jobs4u.base.joboffermanagement.domain.JobOfferBuilder;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentPhase;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcess;
import jobs4u.base.usermanagement.domain.BaseRoles;
import jobs4u.base.usermanagement.domain.UserBuilderHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jobs4u.base.applicationmanagement.JobApplicationManagementService;
import jobs4u.base.applicationmanagement.repositories.JobApplicationRepository;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static jobs4u.base.applicationmanagement.JobApplicationManagementService.copyFileNamesToString;
import static jobs4u.base.applicationmanagement.JobApplicationManagementService.extractInfoFromEmailFile;

@SuppressWarnings("unused")
public class JobApplicationBootstrapper {
	private static final Logger LOGGER = LoggerFactory.getLogger(
			JobOpeningBootstrapper.class);

	private final JobApplicationRepository repo = PersistenceContext.repositories().jobApplications();
	private final JobApplicationManagementService appService = new JobApplicationManagementService();
	private final ApplicationBuilder builder = new ApplicationBuilder();

	public void applicationsForListingDemo(JobOffer jobOffer, Candidate candidate) {
		if (candidate == null) {
			return;
        }
		TransactionalContext ctx = PersistenceContext.repositories().newTransactionalContext();

		ctx.beginTransaction();
		appService.addApplication(jobOffer, candidate);
		ctx.commit();
	}

	public void applicationForGradingDemo(JobOffer offer, Candidate candidate, ApplicationState state) {
		if (offer == null || candidate == null)
			return;
		String filesPath = "processedFiles/" + candidate.associatedUser().name().firstName() + "_"
				+ candidate.associatedUser().name().lastName() + "/";
		String attachedFiles = "";

		attachedFiles = copyFileNamesToString(filesPath);
		builder
				.withCandidate(candidate)
				.withJobOffer(offer)
				.withEmail(new ApplicationEmail(extractInfoFromEmailFile(filesPath)))
				.withFilesPath(new ApplicationFilesPath(filesPath))
				.withAttachedFile(new ApplicationAttachedFile(attachedFiles))
				.withApplicationState(state)
				.withInterviewGrade(new InterviewGrade(-1));
		repo.save(builder.build());
	}

	public JobApplication applicationForTesting(JobOffer jobOffer, Candidate candidate, int grade) {
		TransactionalContext ctx = PersistenceContext.repositories().newTransactionalContext();

		ApplicationBuilder builder = new ApplicationBuilder();
		String filesPath = "processedFiles/" + candidate.associatedUser().name().firstName() + "_"
				+ candidate.associatedUser().name().lastName() + "/";
		String attachedFiles = "";

		attachedFiles = copyFileNamesToString(filesPath);

		builder
				.withCandidate(candidate)
				.withJobOffer(jobOffer)
				.withEmail(new ApplicationEmail(extractInfoFromEmailFile(filesPath)))
				.withFilesPath(new ApplicationFilesPath(filesPath))
				.withAttachedFile(new ApplicationAttachedFile(attachedFiles))
				.withApplicationState(ApplicationState.ACCEPTED)
				.withInterviewGrade(new InterviewGrade(grade));
		return repo.save(builder.build());
	}
}
