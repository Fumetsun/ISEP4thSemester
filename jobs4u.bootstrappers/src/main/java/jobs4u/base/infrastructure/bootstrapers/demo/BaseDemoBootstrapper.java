/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package jobs4u.base.infrastructure.bootstrapers.demo;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import jobs4u.base.applicationmanagement.domain.JobApplication;
import jobs4u.base.candidatemanagement.domain.PhoneNumber;
import jobs4u.base.candidatemanagement.repositories.CandidateRepository;
import jobs4u.base.common.domain.Address;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.customermanagement.domain.CustomerCode;
import jobs4u.base.customermanagement.domain.CustomerFactory;
import jobs4u.base.customermanagement.domain.CustomerName;
import jobs4u.base.customermanagement.repositories.CustomerRepository;
import jobs4u.base.infrastructure.bootstrapers.JobOpeningBootstrapper;
import jobs4u.base.joboffermanagement.domain.ContractType;
import jobs4u.base.joboffermanagement.domain.JobMode;
import jobs4u.base.joboffermanagement.domain.JobOfferBuilder;
import jobs4u.base.notificationmanagement.repositories.NotificationRepository;
import jobs4u.base.pluginhandler.domain.*;
import jobs4u.base.pluginhandler.repositories.RegisteredPluginsRepository;
import jobs4u.base.rankingmanagement.domain.Placement;
import jobs4u.base.rankingmanagement.domain.Ranking;
import jobs4u.base.rankingmanagement.domain.RankingFactory;
import jobs4u.base.rankingmanagement.repositories.RankingRepository;
import jobs4u.base.usermanagement.domain.BaseRoles;
import jobs4u.base.applicationmanagement.domain.ApplicationState;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.candidatemanagement.domain.CandidateFactory;
import jobs4u.base.infrastructure.bootstrapers.BaseBootstrapper;
import jobs4u.base.infrastructure.bootstrapers.JobApplicationBootstrapper;
import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.application.AuthenticationService;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.strings.util.Strings;
import eapli.framework.validations.Invariants;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.usermanagement.domain.UserBuilderHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Base Bootstrapping data app
 *
 * @todo avoid duplication with {@link BaseBootstrapper}
 * @author Paulo Gandra de Sousa
 */
@SuppressWarnings("squid:S106")
public class BaseDemoBootstrapper implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(
			BaseDemoBootstrapper.class);

	private static final String POWERUSER_A1 = "poweruserA1";
	private static final String POWERUSER = "joe@email.org";

	private static final String INTERVIEW_PLUGIN = "plugins/JobApplication_ManagerPlugin/textFiles/InterviewProgrammerSymbols.txt";
	private static final String REQUIREMENT_PLUGIN = "plugins/JobApplication_ManagerPlugin/textFiles/RequirementsProgrammerSymbols.txt";

	private final AuthorizationService authz = AuthzRegistry.authorizationService();
	private final AuthenticationService authenticationService = AuthzRegistry.authenticationService();
	private final UserRepository userRepository = PersistenceContext.repositories().users();
	private final CandidateRepository candidateRepo = PersistenceContext.repositories().candidate();
	private final CandidateFactory candidateFactory = new CandidateFactory();
	private final CustomerRepository customerRepo = PersistenceContext.repositories().customers();
	private final CustomerFactory customerFactory = new CustomerFactory();

	private final RegisteredPluginsRepository pluginRepo = PersistenceContext.repositories().plugins();
	private final RegisteredPluginFactory pluginFactory = new RegisteredPluginFactory();

	private final RankingRepository rankingRepository = PersistenceContext.repositories().rankings();
	private final RankingFactory rankingFactory = new RankingFactory();
	private final NotificationRepository notifRepo = PersistenceContext.repositories().notifications();

	@Override
	public boolean execute() {
		// declare bootstrap actions
		final Action[] actions = { new BackofficeUsersBootstrapper(),
				new ClientUserBootstrapper(), };

		authenticateForBootstrapping();

		registerCustomer();
		registerCandidate();
		registerJobOffer();
		registerEmailTest();
		// registerPluginPlaceholder();
		// registerLifeGuardPlugins();
		Customer customer = customerForListingDemo();

		SystemUser customerManagerUser = userRepository.ofIdentity(Username.valueOf("cm@email.org")).orElse(null);
		SystemUser candidate = userRepository.ofIdentity(Username.valueOf("1220962@isep.ipp.pt")).get();
		RegisteredPlugin interviewPlugin = registerInterviewPluginForResponses();
		RegisteredPlugin requirementPlugin = registerRegisterPluginForResponses();

		JobOpeningBootstrapper jobOpeningBootstrapper = new JobOpeningBootstrapper();
		JobOffer jobOffer = jobOpeningBootstrapper.jobOfferForListingDemo(customer),
				jobOffer2 = jobOpeningBootstrapper.jobOfferForUserResponseSave(customer, interviewPlugin, requirementPlugin);

		JobApplicationBootstrapper jobApplicationBootstrapper = new JobApplicationBootstrapper();
		jobApplicationBootstrapper.applicationsForListingDemo(jobOffer,
				candidateRepo.findByNumber(PhoneNumber.valueOf("961234567")).orElse(null));
		// jobApplicationBootstrapper = new JobApplicationBootstrapper();
		jobApplicationBootstrapper.applicationsForListingDemo(jobOffer,
				candidateRepo.findByNumber(PhoneNumber.valueOf("931234567")).orElse(null));
		jobApplicationBootstrapper.applicationsForListingDemo(jobOffer,
				candidateRepo.findByNumber(PhoneNumber.valueOf("919876543")).orElse(null));


		jobApplicationBootstrapper.applicationsForListingDemo(jobOffer2,
				candidateRepo.findByNumber(PhoneNumber.valueOf("919876543")).orElse(null));
		jobApplicationBootstrapper.applicationForGradingDemo(jobOffer2,
				candidateRepo.findByNumber(PhoneNumber.valueOf("915672348")).orElse(null), ApplicationState.MR);

		// execute all bootstrapping
		boolean ret = true;
		for (final Action boot : actions) {
			System.out.println("Bootstrapping " + nameOfEntity(boot) + "...");
			ret &= boot.execute();
		}
		return ret;
	}

	private void registerEmailTest()
	{
		SystemUserBuilder userBuilder = UserBuilderHelper.builder();

		userBuilder.withUsername("1150609@isep.ipp.pt").withPassword("HelloTD1!").withName("Tiago", "Silva")
				.withEmail("1150609@isep.ipp.pt").withRoles(BaseRoles.CUSTOMER_MANAGER);

		SystemUser builderUser = userBuilder.build();

		SystemUser customerManagerUser = userRepository.save(builderUser);

		userBuilder.withUsername("1220716@isep.ipp.pt").withPassword("Test1!").withName("Test", "Ed")
				.withEmail("1220716@isep.ipp.pt").withRoles(BaseRoles.CUSTOMER);

		builderUser = userBuilder.build();
		builderUser.unassignRole(BaseRoles.CUSTOMER_MANAGER);
		SystemUser customerUser = userRepository.save(builderUser);

		Customer customer = customerFactory.createCustomer("TestForU", "TEST4U",
				"Tested help, 194", customerManagerUser, customerUser);
		customer = customerRepo.save(customer);

		JobOpeningBootstrapper jobOpeningBootstrapper = new JobOpeningBootstrapper();
		JobOffer offer = jobOpeningBootstrapper.jobOfferForTesting(customer);

		JobApplicationBootstrapper jobApplicationBootstrapper = new JobApplicationBootstrapper();

		Optional<Candidate> candidate = candidateRepo.findByNumber(new PhoneNumber("931234567"));
		JobApplication rankApp = jobApplicationBootstrapper.applicationForTesting(offer, candidate.get(), 17);

		Optional<Candidate> candidate2 = candidateRepo.findByNumber(new PhoneNumber("961234567"));
		JobApplication rankApp2 = jobApplicationBootstrapper.applicationForTesting(offer, candidate2.get(),12);

		Ranking rank = rankingRepository.rankingOfApplicationJobOffer(offer.identity(), rankApp.identity());
		rank.updatePlacement(new Placement(1));
		rankingRepository.save(rank);

		Ranking rank2 = rankingRepository.rankingOfApplicationJobOffer(offer.identity(), rankApp2.identity());
		rank.updatePlacement(new Placement(2));
		rankingRepository.save(rank2);

	}

	private void registerCandidate() {
		final SystemUserBuilder userBuilder = UserBuilderHelper.builder();
		userBuilder.withUsername("1220962@isep.ipp.pt").withPassword("!Awesome22").withName("John", "Doe")
				.withEmail("1220962@isep.ipp.pt").withRoles(BaseRoles.CANDIDATE);
		SystemUser newUser = userBuilder.build();

		SystemUser candidateUser = null;
		try {
			candidateUser = userRepository.save(newUser);
			assert candidateUser != null;
		} catch (ConcurrencyException | IntegrityViolationException e) {
			// ignoring exception. assuming it is just a primary key violation
			// due to the tentative of inserting a duplicated user
			LOGGER.warn("Assuming {} already exists (activate trace log for details)", newUser.username());
			LOGGER.trace("Assuming existing record", e);
		}

		Candidate candidate = candidateFactory.newCandidate(candidateUser, new PhoneNumber("961234567"));
		candidateRepo.save(candidate);

		userBuilder.withUsername("janedoe@email.org").withPassword("Jane!Password3").withName("Jane", "Doe")
				.withEmail("janedoe@email.com").withRoles(BaseRoles.CANDIDATE);
		newUser = userBuilder.build();
		try {
			candidateUser = userRepository.save(newUser);
			assert candidateUser != null;
		} catch (ConcurrencyException | IntegrityViolationException e) {
			// ignoring exception. assuming it is just a primary key violation
			// due to the tentative of inserting a duplicated user
			LOGGER.warn("Assuming {} already exists (activate trace log for details)", newUser.username());
			LOGGER.trace("Assuming existing record", e);
		}
		candidate = candidateFactory.newCandidate(candidateUser, new PhoneNumber("931234567"));
		candidateRepo.save(candidate);

		userBuilder.withUsername("jane.smith@email.org").withPassword("Jane!Password1").withName("Jane", "Smith")
				.withEmail("jane.smith@email.org").withRoles(BaseRoles.CANDIDATE);
		newUser = userBuilder.build();
		try {
			candidateUser = userRepository.save(newUser);
			assert candidateUser != null;
		} catch (ConcurrencyException | IntegrityViolationException e) {
			// ignoring exception. assuming it is just a primary key violation
			// due to the tentative of inserting a duplicated user
			LOGGER.warn("Assuming {} already exists (activate trace log for details)", newUser.username());
			LOGGER.trace("Assuming existing record", e);
		}
		candidate = candidateFactory.newCandidate(candidateUser, new PhoneNumber("919876543"));
		candidateRepo.save(candidate);

		userBuilder.withUsername("john.smith@email.org").withPassword("john!Password1").withName("John", "Smith")
				.withEmail("john.smith@email.org").withRoles(BaseRoles.CANDIDATE);
		newUser = userBuilder.build();
		try {
			candidateUser = userRepository.save(newUser);
			assert candidateUser != null;
		} catch (ConcurrencyException | IntegrityViolationException e) {
			// ignoring exception. assuming it is just a primary key violation
			// due to the tentative of inserting a duplicated user
			LOGGER.warn("Assuming {} already exists (activate trace log for details)", newUser.username());
			LOGGER.trace("Assuming existing record", e);
		}
		candidate = candidateFactory.newCandidate(candidateUser, new PhoneNumber("915672348"));
		candidateRepo.save(candidate);
	}

	private Customer registerCustomer() {

		final SystemUserBuilder cmBuilder = UserBuilderHelper.builder();
		cmBuilder.withUsername("jakeJ4U@email.org").withPassword("jobJake4##").withName("Jake", "Williams")
				.withEmail("jakeJ4U@email.org").withRoles(BaseRoles.CUSTOMER_MANAGER);
		final SystemUser cUser = cmBuilder.build();

		SystemUser customermanagerUser = null;
		try {
			customermanagerUser = userRepository.save(cUser);
			assert customermanagerUser != null;
		} catch (ConcurrencyException | IntegrityViolationException e) {
			// ignoring exception. assuming it is just a primary key violation
			// due to the tentative of inserting a duplicated user
			LOGGER.warn("Assuming {} already exists (activate trace log for details)", cUser.username());
			LOGGER.trace("Assuming existing record", e);
		}

		final SystemUserBuilder customerUserBuilder = UserBuilderHelper.builder();
		customerUserBuilder.withUsername("csalence@email.com").withPassword("CarlSalence#22")
				.withName("Carl", "Salence")
				.withEmail("csalence@email.com").withRoles(BaseRoles.CUSTOMER);
		final SystemUser newUser = customerUserBuilder.build();

		SystemUser representativeUser = null;
		try {
			representativeUser = userRepository.save(newUser);
			assert representativeUser != null;
		} catch (ConcurrencyException | IntegrityViolationException e) {
			// ignoring exception. assuming it is just a primary key violation
			// due to the tentative of inserting a duplicated user
			LOGGER.warn("Assuming {} already exists (activate trace log for details)", newUser.username());
			LOGGER.trace("Assuming existing record", e);
		}

		final Customer customer = new Customer(new CustomerCode("CUST23"), new CustomerName("Carl Salence"),
				new Address("Kingston Street, 2145"), customermanagerUser, representativeUser);

		return customerRepo.save(customer);

	}

	private void registerJobOffer() {

	}

	private Customer customerForListingDemo() {
		SystemUser customerManagerUser = userRepository.ofIdentity(Username.valueOf("cm@email.org")).orElse(null);
		if (customerManagerUser == null) {
			LOGGER.warn("Customer Manager System User not in the system yet.");
		}

		SystemUserBuilder userBuilder = UserBuilderHelper.builder();
		userBuilder.withUsername("jobs4u@jobs4u.org").withPassword("jobs4U$$$").withName("Jobs", "FourU")
				.withEmail("jobs4u@jobs4u.org").withRoles(BaseRoles.CUSTOMER);
		SystemUser builderUser = userBuilder.build(),
				customerUser = null;
		try {
			customerUser = userRepository.save(builderUser);
			assert customerUser != null;
		} catch (ConcurrencyException | IntegrityViolationException e) {
			// ignoring exception. assuming it is just a primary key violation
			// due to the tentative of inserting a duplicated user
			LOGGER.warn("Assuming {} already exists (activate trace log for details)", customerUser.username());
			LOGGER.trace("Assuming existing record", e);
		}

		Customer customer = customerFactory.createCustomer("JobsFouru", "JOBS4U",
				"Jobs4u avenue, 123", customerManagerUser, customerUser);
		return customerRepo.save(customer);

		// Candidate candidate =
		// candidateRepo.findByNumber(PhoneNumber.valueOf("961234567")).orElse(null);
		// if (candidate == null) {
		// LOGGER.warn("Candidate is not in the system yet.");
		// }
		// System.out.printf("Candidate number: %s\n", candidate.toString());

		// jobOfferBuilder.withCustomer(customer)
		// .withJobTitle("IT") //("Jobs4u IT Support")
		// .withContractType("Part-time")
		// .withJobMode("Remote")
		// .withVacancies(3)
		// .withDescription("Work") //("Come work 4 us")
		// .withAddress("Jobs4u avenue, 123");
		// JobOffer jobOffer = jobOfferBuilder.build();
		// // System.out.printf("Customer: \n%s\nJob Offer: \n%s\n",
		// customer.toString(), jobOffer.toString());
		// jobRepo.save(jobOffer);
		// /*JobOffer jobOffer = offerService.addJobOffer(customer, "Jobs4u IT Support",
		// "Part-time", "Remote", 3,
		// "Come work 4 us", "Jobs4u avenue, 123");*/
		// System.out.println();

		// /*applicationService.addApplication(jobOffer, candidate);*/

		// /*
		// * jobApplicationBuilder.withEmail(new ApplicationEmail("johndoe@email.com"))
		// * .withFilesPath(new ApplicationFilesPath("./processedFiles/John_Doe/"))
		// * .withAttachedFile(new
		// * ApplicationAttachedFile("./processedFiles/John_Doe/1-cv.txt"))
		// * .withJobOffer(jobOffer).withCandidate(candidate);
		// */
	}

	private RegisteredPlugin registerInterviewPluginForResponses() {
		RegisteredPlugin plugin = pluginFactory.registerPlugin(INTERVIEW_PLUGIN, "Programmer Interview", 2);
		return pluginRepo.save(plugin);
	}

	private RegisteredPlugin registerRegisterPluginForResponses() {
		RegisteredPlugin plugin = pluginFactory.registerPlugin(REQUIREMENT_PLUGIN, "Programmer Requirements", 1);
		return pluginRepo.save(plugin);
	}

	/**
	 * authenticate a super user to be able to register new users
	 *
	 */
	protected void authenticateForBootstrapping() {
		authenticationService.authenticate(POWERUSER, POWERUSER_A1);
		/*
		 * authenticationService.authenticate("johndoe@email.org", "John!Password6");
		 * authenticationService.authenticate("jakeJ4U@email.org", "jobJake4##");
		 * authenticationService.authenticate("jakeJ4U@email.org", "jobJake4##");
		 */
		Invariants.ensure(authz.hasSession());
	}

	private String nameOfEntity(final Action boot) {
		final String name = boot.getClass().getSimpleName();
		return Strings.left(name, name.length() - "Bootstrapper".length());
	}
}
