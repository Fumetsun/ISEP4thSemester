package jobs4u.base.persistence.impl.inmemory;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import jobs4u.base.applicationmanagement.domain.JobApplication;
import jobs4u.base.applicationmanagement.repositories.JobApplicationRepository;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.joboffermanagement.domain.JobRefCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryJobApplicationRepository
        extends InMemoryDomainRepository<JobApplication, Integer>
        implements JobApplicationRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public Iterable<JobApplication> findAllApplications() {
        return findAll();
    }

    @Override
    public Optional<JobApplication> findByRef(final Integer ref) {
        return Optional.of(data().get(ref));
    }

	@Override
	public Iterable<JobApplication> findAllApplicationsOfJobOffer(JobRefCode code) {
		List<JobApplication> apps = new ArrayList<>();

		for(JobApplication app : findAllApplications() ){
			if(app.getJoboffer().reference() == code){
				apps.add(app);
			}
		}

		return apps;
	}

	@Override
	public Optional<JobApplication> findByRefCode(Integer code) {


		for(JobApplication app : findAllApplications() ){
			if(app.identity() == code){
				return Optional.of(app);
			}
		}

		return null;
	}

	@Override
	public Iterable<JobApplication> findApplicationsByCandidate(SystemUser candidate) {
		List<JobApplication> apps = (List<JobApplication>) findAllApplications();

		for(JobApplication app : findAllApplications() ){
			if(app.getCandidate().associatedUser() != candidate){
				apps.remove(app);
			}
		}

		return apps;
	}

	@Override
	public Iterable<JobApplication> getCustomerManagerApplications(SystemUser user) {
		List<JobApplication> apps = (List<JobApplication>) findAllApplications();

		for(JobApplication app : findAllApplications() ){
			if(app.getJoboffer().customer().customerManagerUser() != user){
				apps.remove(app);
			}
		}

		return apps;
	}

	@Override
	public <S extends JobApplication> S save(S entity) {
		return super.save(entity);
	}
}
