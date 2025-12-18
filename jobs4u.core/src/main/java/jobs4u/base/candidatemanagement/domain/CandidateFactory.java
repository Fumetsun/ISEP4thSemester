package jobs4u.base.candidatemanagement.domain;

import eapli.framework.domain.model.DomainFactory;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

public class CandidateFactory implements DomainFactory<Candidate> {
	private SystemUser user;
	private PhoneNumber number;

	public Candidate newCandidate(final SystemUser user, final PhoneNumber number) {
		this.user = user;
		this.number = number;
		return build();
	}

	@Override
	public Candidate build() {
		return new Candidate(this.user, this.number);
	}
	
}
