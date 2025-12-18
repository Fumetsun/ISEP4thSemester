package jobs4u.base.candidatemanagement.application;

import jobs4u.base.candidatemanagement.CandidateManagementService;
import jobs4u.base.candidatemanagement.domain.Candidate;

//@UseCaseController
public class RegisterCandidateController {
	private final CandidateManagementService candidateService = new CandidateManagementService();

	public Candidate importCandidateInfo(String file) {
		return candidateService.importCandidateInfo(file);

	}

	public Candidate registerCandidate(final String number, final String email, final String firstName,
			final String lastName) {
		return candidateService.registerCandidate(number, email, firstName, lastName);
	}
}
