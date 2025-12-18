package jobs4u.base.joboffermanagement.domain.dto;

import jobs4u.base.joboffermanagement.domain.JobRefCode;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentPhase;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcess;
import jobs4u.base.recruitmentprocessmanagement.domain.dto.RecruitmentProcessDTO;

import java.util.List;

public class JobOfferDto {
	private String refCode;
	private String title;
	private String JobMode;
	private String NumberVacancies;
	private RecruitmentProcessDTO recruitmentProcess;

	public String toString() {
		return String.format("Job Offer %s info:\n\t- Title: %s\n\t- Mode: %s\n\t- Number of vacancies: %s\n", refCode,title,
				JobMode, NumberVacancies);
	}

	public JobOfferDto(String refCode, String title, String jobMode, String numberVacancies, RecruitmentProcess recruitmentProcess) {
		this.refCode = refCode;
		this.title = title;
		JobMode = jobMode;
		NumberVacancies = numberVacancies;
		this.recruitmentProcess = recruitmentProcess.toDTO();
	}

	public String getRefCode() {
		return refCode;
	}

	public RecruitmentProcessDTO getRecruitmentProcess() {
		return recruitmentProcess;
	}

	public String getTitle() {
		return title;
	}

	public String getJobMode() {
		return JobMode;
	}

	public String getNumberVacancies() {
		return NumberVacancies;
	}
}
