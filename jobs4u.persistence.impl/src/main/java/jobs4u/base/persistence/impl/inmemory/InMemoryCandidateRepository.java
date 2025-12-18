package jobs4u.base.persistence.impl.inmemory;

import java.util.Optional;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.candidatemanagement.domain.PhoneNumber;
import jobs4u.base.candidatemanagement.repositories.CandidateRepository;

public class InMemoryCandidateRepository
		extends InMemoryDomainRepository<Candidate, PhoneNumber>
		implements CandidateRepository {

	static {
		InMemoryInitializer.init();
	}

	@Override
	public Optional<Candidate> findByNumber(PhoneNumber number) {
        return Optional.of(data().get(number));
	}

	@Override
	public Optional<Candidate> findByRefCode(SystemUser user) {
		return Optional.empty();
	}

	@Override
	public Iterable<Candidate> findAllCandidate() {
		return findAll();
	}
}
