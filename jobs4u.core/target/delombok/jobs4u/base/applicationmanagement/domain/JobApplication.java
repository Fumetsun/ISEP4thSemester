package jobs4u.base.applicationmanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.representations.dto.DTOable;
import jakarta.persistence.*;
import jobs4u.base.applicationmanagement.domain.dto.ApplicationDTO;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.joboffermanagement.domain.JobOffer;

import java.io.Serializable;

@Entity
public class JobApplication implements AggregateRoot<Integer>, Serializable, DTOable<ApplicationDTO> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "appRefCode")
	private Integer appRefCode;

	private ApplicationEmail email;

	private ApplicationFilesPath filesPath;

	private ApplicationAttachedFile attachedFile;

	private ApplicationState state;

	private InterviewGrade grade;

	@ManyToOne
	private JobOffer joboffer;

	@ManyToOne
	private Candidate candidate;

	public JobApplication(
			final ApplicationEmail email,
			final ApplicationFilesPath filesPath,
			final ApplicationAttachedFile attachedFile,
			final ApplicationState state, final InterviewGrade grade,
			final JobOffer joboffer, final Candidate candidate) {
		this.email = email;
		this.filesPath = filesPath;
		this.attachedFile = attachedFile;
		this.state = state;
		this.grade = grade;
		this.candidate = candidate;
		this.joboffer = joboffer;

	}

	public JobApplication() {
		// FOR ORM
	}

	@Override
	public boolean sameAs(Object other) {
		return false;
	}

	@Override
	public Integer identity() {
		return this.appRefCode;
	}

	@Override
	public ApplicationDTO toDTO() {
		return new ApplicationDTO(appRefCode, email.toString(), filesPath.toString(), attachedFile.toString(),
				joboffer.identity().getRef(), candidate.associatedUser().identity().toString(), grade.toString(), state.toString());
	}

	public boolean sameOffer(JobOffer jobOffer) {
		return this.joboffer.equals(jobOffer);
	}

	public String getFilePath() {
		return this.filesPath.toString();
	}

	public void addFileRef(String file) {
		this.attachedFile.addFile(file);
	}

	public void removeFile(String file) {this.attachedFile.removeFile(file);}

	public JobOffer getJoboffer() {
		return joboffer;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public ApplicationState getState() {
		return state;
	}

	public void setState(ApplicationState state) {
		this.state = state;
	}

	public String getFileRef() {
		return this.attachedFile.toString();
	}

	public void setGrade(int grade) {
		this.grade.setGrade(grade);
	}

	public int getGrade() {
		return this.grade.getGrade();
	}

	@Override
	public String toString() {
		return candidate.toString() + " |ApplicationState: " + state.toString();
	}
}
