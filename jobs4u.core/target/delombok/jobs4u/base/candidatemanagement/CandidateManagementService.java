package jobs4u.base.candidatemanagement;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserManagementService;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.candidatemanagement.domain.CandidateFactory;
import jobs4u.base.candidatemanagement.domain.PhoneNumber;
import jobs4u.base.candidatemanagement.repositories.CandidateRepository;
import jobs4u.base.clientusermanagement.application.PasswordGenerator;
import jobs4u.base.common.ImportFileService;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.usermanagement.application.ListUsersController;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.io.File;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class CandidateManagementService {
	private final AuthorizationService authz = AuthzRegistry.authorizationService();
	private final CandidateRepository repo = PersistenceContext.repositories().candidate();
	private final AuthorizationService autService = AuthzRegistry.authorizationService();
	private final UserManagementService userService = AuthzRegistry.userService();
	private final ListUsersController usersController = new ListUsersController();
	private final CandidateFactory candidateFactory = new CandidateFactory();
	private final PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
			.useDigits(true)
			.useLower(true)
			.useUpper(true)
			.usePunctuation(true)
			.build();

	public Candidate findByNumber(PhoneNumber number) {
		return repo.findByNumber(number).orElse(null);
	}

	public boolean addCandidate(Candidate candidate) {
		if (candidate == null)
			return false;
		repo.save(candidate);
		return true;
	}

	/*
	* public Candidate findByNumber(PhoneNumber number) {
		Iterable<Candidate> candidates = repo.findAllCandidate();

		for(Candidate c: candidates){
			if(c.toString().equals(number.toString())){
				return c;
			}
		}
		return null;
	}*/

	public Candidate importCandidateInfo(String file) {
		file = "processedFiles/" + file;

		File directory = new File(file);
		File[] files = directory.listFiles();

		if (files != null) {
			for (File f : files) {
				if (f.getName().contains("-candidate-data.txt")) {
					String[] info;
					try {
						info = ImportFileService.importCandidateInfo(f.getAbsolutePath());
						return registerCandidate(info[0], info[1], info[2], info[3]);
					} catch (Exception e) {
						String err = "Error reading file, either file not found or incorrect formatting.";
						err.concat(e.toString());
						throw new IllegalArgumentException(err);
					}
				}
			}
		}

		return null;

	}

	public Candidate registerCandidate(final String number, final String email, final String firstName,
			final String lastName) {
		autService.ensureAuthenticatedUserHasAnyOf(BaseRoles.OPERATOR);
		SystemUser user = usersController.find(Username.valueOf(email)).orElse(null);
		if (user == null) {
			Random random = new Random();
			String password = passwordGenerator.generate(random.nextInt(9) + 8);
			Role role = getCandidateRole();
			Set<Role> roles = new HashSet<>();
			roles.add(role);
			user = userService.registerNewUser(email, password, firstName, lastName, email, roles);
		}
		Candidate candidate = findByNumber(PhoneNumber.valueOf(number));
		if (candidate == null) {
			candidate = candidateFactory.newCandidate(user, PhoneNumber.valueOf(number));
			addCandidate(candidate);
		}
		return candidate;
	}

	private Role getCandidateRole() {
		return BaseRoles.getCandidateRole();
	}

	public Iterable<Candidate> getCandidates() {
		return repo.findAllCandidate();
	}
}
