package jobs4u.base.notificationmanagement.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.joboffermanagement.domain.JobRefCode;
import jobs4u.base.notificationmanagement.domain.Notification;

import java.util.Optional;


public interface NotificationRepository extends DomainRepository<Long, Notification> {


    default Optional<Notification> findByRef(final Long id) {
        return ofIdentity(id);
    }

    public Iterable<Notification> findAllNotifications();

    public Iterable<Notification> findAllSentNotifications();

    public Iterable<Notification> findCandidateNotifications(SystemUser candidate);

    public Iterable<Notification> findCandidateUnseenNotifications(SystemUser candidate);

}
