package jobs4u.base.persistence.impl.jpa;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import jakarta.persistence.TypedQuery;
import jobs4u.base.Application;
import jobs4u.base.applicationmanagement.domain.JobApplication;
import jobs4u.base.candidatemanagement.repositories.CandidateRepository;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.candidatemanagement.domain.PhoneNumber;

class JpaCandidateRepository
		extends JpaAutoTxRepository<Candidate, PhoneNumber, PhoneNumber>
		implements CandidateRepository {

	public JpaCandidateRepository(final TransactionalContext autoTx) {
		super(autoTx, "num");
	}

	public JpaCandidateRepository(final String puname) {
		super(puname, Application.settings().getExtendedPersistenceProperties(),
				"num");
	}

	@Override
	public Optional<Candidate> findByNumber(PhoneNumber number) {
		return ofIdentity(number);
	}

	@Override
	public Optional<Candidate> findByRefCode(SystemUser user) {
		Candidate candidate = (createQuery("select c from Candidate c where c.user.username = :candidateUsername", Candidate.class)
				.setParameter("candidateUsername", user.username()).getSingleResult());

		return Optional.ofNullable(candidate);
	}

	public Optional<Candidate> queryByNumber(PhoneNumber number) {
		final TypedQuery<Candidate> q = createQuery("SELECT e FROM CANDIDATE e WHERE  e.number = :number",
				Candidate.class);
		q.setParameter("number", Integer.parseInt(number.toString()));
		Candidate candidate = q.getSingleResult();

		return Optional.of(candidate);
	}
	@Override
	public Iterable<Candidate> findAllCandidate() {
		return findAll();
	}

}
