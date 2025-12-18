package jobs4u.base.candidatemanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.representations.dto.DTOable;
import jakarta.persistence.*;
import jobs4u.base.candidatemanagement.domain.dto.CandidateDto;

import java.io.Serializable;

@Entity
public class Candidate implements AggregateRoot<PhoneNumber>, Serializable, DTOable<CandidateDto> {

	@EmbeddedId
	private PhoneNumber num;

    @OneToOne
	private SystemUser user;

	public Candidate(final SystemUser user, final PhoneNumber num) {
		this.user = user;
		this.num = num;
	}

	public Candidate() {
	}

	@Override
	public boolean equals(final Object o) {
		return DomainEntities.areEqual(this, o);
	}

	@Override
	public int hashCode() {
		return DomainEntities.hashCode(this);
	}

	@Override
	public boolean sameAs(final Object other) {
		return DomainEntities.areEqual(this, other);
	}

	@Override
	public PhoneNumber identity() {
		return this.num;
	}

	public SystemUser associatedUser(){
		return this.user;
	}

	@Override
	public CandidateDto toDTO() {
		return new CandidateDto(this.num.toString(), (user.name().toString()));
	}


	public String toString() {
		return "Number: " + num.toString(); // TEMPORARY SOLUTION
	}
}
