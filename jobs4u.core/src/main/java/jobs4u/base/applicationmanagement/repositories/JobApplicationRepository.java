package jobs4u.base.applicationmanagement.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.applicationmanagement.domain.JobApplication;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.joboffermanagement.domain.JobRefCode;

import java.util.Optional;

public interface JobApplicationRepository
		extends DomainRepository<Integer, JobApplication> {

	/**
	 * Returns Application with ID ref from database
	 *
	 * @param ref
	 * @return
	 */

	default Optional<JobApplication> findByRef(final Integer ref) {
		return ofIdentity(ref);
	}

	/**
	 * Returns all Applications from database
	 * 
	 * @return
	 */
	public Iterable<JobApplication> findAllApplications();

	public Iterable<JobApplication> findAllApplicationsOfJobOffer(JobRefCode code);

	public Optional<JobApplication> findByRefCode(Integer code);

	public Iterable<JobApplication> findApplicationsByCandidate(SystemUser candidate);

	Iterable<JobApplication> getCustomerManagerApplications(SystemUser user);
}
