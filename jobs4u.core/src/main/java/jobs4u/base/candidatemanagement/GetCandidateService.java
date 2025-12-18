package jobs4u.base.candidatemanagement;


import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.candidatemanagement.domain.PhoneNumber;
import jobs4u.base.candidatemanagement.repositories.CandidateRepository;
import jobs4u.base.infrastructure.persistence.PersistenceContext;

import java.util.Optional;

public class GetCandidateService {
	private final CandidateRepository repo = PersistenceContext.repositories().candidate();

	public Iterable<Candidate> getCandidates() {
		return repo.findAllCandidate();
	}

	public Optional<Candidate> getCandidateByPhoneNumber(PhoneNumber phoneNumber){
		return repo.ofIdentity(phoneNumber);
	}
}
