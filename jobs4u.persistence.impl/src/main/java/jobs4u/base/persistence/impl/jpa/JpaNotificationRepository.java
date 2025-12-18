package jobs4u.base.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import jobs4u.base.Application;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.notificationmanagement.domain.Notification;
import jobs4u.base.notificationmanagement.domain.NotificationState;
import jobs4u.base.notificationmanagement.repositories.NotificationRepository;

import java.util.Optional;

public class JpaNotificationRepository extends JpaAutoTxRepository<Notification, Long, Long>
        implements NotificationRepository {
    public JpaNotificationRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "id");
    }

    public JpaNotificationRepository(TransactionalContext tx) {
        super(tx, "id");
    }

    @Override
    public Optional<Notification> ofIdentity(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteOfIdentity(Long entityId) {

    }


    @Override
    public Iterable<Notification> findAllNotifications() {
        return findAll();
    }

    @Override
    public Iterable<Notification> findAllSentNotifications() {
        return createQuery(
                "SELECT n FROM Notification n WHERE n.notifState = :state",
                Notification.class).setParameter("state", NotificationState.SENT).getResultList();
    }

    @Override
    public Iterable<Notification> findCandidateNotifications(SystemUser candidate) {
        return createQuery(
                "SELECT n FROM Notification n WHERE n.receiver = :receiving",
                Notification.class).setParameter("receiving", candidate).getResultList();
    }

    @Override
    public Iterable<Notification> findCandidateUnseenNotifications(SystemUser candidate) {
        return createQuery(
                "SELECT n FROM Notification n WHERE n.receiver = :receiving AND n.notifState = :state",
                Notification.class).setParameter("receiving", candidate).setParameter("state", NotificationState.SENT).getResultList();
    }

    @Override
    public <S extends Notification> S save(S entity) {
        return super.save(entity);


    }
}
