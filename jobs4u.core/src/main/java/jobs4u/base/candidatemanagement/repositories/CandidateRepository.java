package jobs4u.base.candidatemanagement.repositories;

import java.util.Optional;

import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.candidatemanagement.domain.PhoneNumber;

public interface CandidateRepository
		extends DomainRepository<PhoneNumber, Candidate> {

	Optional<Candidate> findByNumber(PhoneNumber number);

	Optional<Candidate> findByRefCode(SystemUser user);

	Iterable<Candidate> findAllCandidate();
}
