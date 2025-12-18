package jobs4u.base.persistence.impl.jpa;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import jobs4u.base.Application;
import jobs4u.base.applicationmanagement.domain.ApplicationState;
import jobs4u.base.applicationmanagement.domain.JobApplication;
import jobs4u.base.applicationmanagement.repositories.JobApplicationRepository;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.joboffermanagement.domain.JobRefCode;
import jobs4u.base.notificationmanagement.domain.JobApplicationNotificationEvent;
import jobs4u.base.rankingmanagement.domain.RankingEvent;
import jobs4u.base.usermanagement.domain.BaseRoles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

public class JpaJobApplicationRepository
		extends JpaAutoTxRepository<JobApplication, Integer, Integer>
		implements JobApplicationRepository {


	private final EventPublisher dispatcher = InProcessPubSub.publisher();

	public JpaJobApplicationRepository(final String puname) {
		super(puname, Application.settings().getExtendedPersistenceProperties(),
				"appRefCode");
	}


	public JpaJobApplicationRepository(TransactionalContext tx) {
		super(tx, "appRefCode");
	}

	@Override
	public Optional<JobApplication> findByRef(final Integer ref) {
		return ofIdentity(ref);
	}

	@Override
	public Iterable<JobApplication> findAllApplications() {
		return findAll();
	}

	@Override
	public Iterable<JobApplication> findAllApplicationsOfJobOffer(JobRefCode code) {
		return createQuery("select a from JobApplication a where a.joboffer.jobRefCode = :code", JobApplication.class)
				.setParameter("code", code).getResultList();
	}

	@Override
	public Optional<JobApplication> findByRefCode(Integer code) {
		JobApplication app = (createQuery("select a from JobApplication a where a.appRefCode = :appRefCode", JobApplication.class)
				.setParameter("appRefCode", code).getSingleResult());
		if (app == null)
			return null;
		return Optional.of(app);
	}

	@Override
	public Iterable<JobApplication> getCustomerManagerApplications(SystemUser user) {
		String query = "SELECT ja FROM JobApplication ja " +
				"WHERE ja.candidate.num IN (" +
				"SELECT jaInner.candidate.num " +
				"FROM JobApplication jaInner " +
				"INNER JOIN jaInner.joboffer jo " +
				"INNER JOIN jo.customer c " +
				"WHERE c.customerManagerUser.username = :managerEmail " +
				"AND (jaInner.state = :state1 OR jaInner.state = :state2))";

		Iterable<JobApplication> apps = createQuery(query, JobApplication.class)
				.setParameter("managerEmail", user.username()).setParameter("state1", ApplicationState.OPEN).setParameter("state2", ApplicationState.MR)
				.getResultList();

		return apps;
	}

	@Override
	public Iterable<JobApplication> findApplicationsByCandidate(SystemUser user) {
		Iterable<JobApplication> apps = (createQuery("select a from JobApplication a where a.candidate.user.username = :candidateUsername", JobApplication.class)
				.setParameter("candidateUsername", user.username()).getResultList());

		return apps;
	}


	@Override
	public <S extends JobApplication> S save(S entity) {
		final AuthorizationService authz = AuthzRegistry.authorizationService();


		TransactionalContext ctx = PersistenceContext.repositories().newTransactionalContext();


		S app = super.save(entity);

		if(entity.identity() == null){
			final DomainEvent eventRank = new RankingEvent(entity.getJoboffer(), app);

			ctx.beginTransaction();
			dispatcher.publish(eventRank);
			ctx.commit();

			final DomainEvent event = new JobApplicationNotificationEvent(entity.getCandidate().associatedUser(), authz.loggedinUserWithPermissions(BaseRoles.OPERATOR,BaseRoles.CUSTOMER_MANAGER, BaseRoles.POWER_USER).get(), "Application state Changed to: " + entity.getState(), LocalDate.now());
			ctx.beginTransaction();
			dispatcher.publish(event);
			ctx.commit();


			ofIdentity(entity.identity());
		}else{
			if(ofIdentity(entity.identity()).isPresent()){
				final DomainEvent event = new JobApplicationNotificationEvent(entity.getCandidate().associatedUser(), authz.loggedinUserWithPermissions(BaseRoles.OPERATOR,BaseRoles.CUSTOMER_MANAGER, BaseRoles.POWER_USER).get(), "Application state Changed to: " + entity.getState(), LocalDate.now());
				ctx.beginTransaction();
				dispatcher.publish(event);
				ctx.commit();
			}
		}


		return app;
	}
}
