package jobs4u.base.applicationmanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import jobs4u.base.applicationmanagement.domain.JobApplication;
import jobs4u.base.applicationmanagement.domain.dto.ApplicationDTO;
import jobs4u.base.applicationmanagement.repositories.JobApplicationRepository;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.joboffermanagement.domain.JobRefCode;

public class ListJobApplicationService {
    @SuppressWarnings("unused")
	private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final JobApplicationRepository repo = PersistenceContext.repositories().jobApplications();

	public Iterable<ApplicationDTO> getJobApplicationsOfJobOfferCode(JobRefCode code) {
		Iterable <JobApplication> apps = repo.findAllApplicationsOfJobOffer(code);
		List<ApplicationDTO> ret = new ArrayList<>();
		apps.forEach(a -> ret.add(a.toDTO()));
		return ret;
	}

	public Optional<JobApplication> getByRef(int code) {
		return repo.findByRefCode(code);
	}

	public Iterable<JobApplication> getJobApplicationsOfJobOfferCodeNonDTO(JobRefCode code) {
		return repo.findAllApplicationsOfJobOffer(code);
	}

}
