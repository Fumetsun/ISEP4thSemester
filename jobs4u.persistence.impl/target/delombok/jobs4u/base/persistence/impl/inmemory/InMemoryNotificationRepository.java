package jobs4u.base.persistence.impl.inmemory;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import jobs4u.base.applicationmanagement.domain.JobApplication;
import jobs4u.base.applicationmanagement.repositories.JobApplicationRepository;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.notificationmanagement.domain.Notification;
import jobs4u.base.notificationmanagement.domain.NotificationState;
import jobs4u.base.notificationmanagement.repositories.NotificationRepository;
import org.aspectj.weaver.ast.Not;

import java.util.ArrayList;
import java.util.Optional;

public class InMemoryNotificationRepository extends InMemoryDomainRepository<Notification, Long>
        implements NotificationRepository {


    @Override
    public Iterable<Notification> findAllNotifications() {
        return findAll();
    }

    @Override
    public Iterable<Notification> findAllSentNotifications() {
        ArrayList<Notification> notifs = (ArrayList<Notification>) findAll();

        for(Notification notif: notifs){
            if(notif.state() == NotificationState.DELIVERED){
                notifs.remove(notif);
            }
        }
        return notifs;
    }

    @Override
    public Iterable<Notification> findCandidateNotifications(SystemUser candidate) {
        ArrayList<Notification> notifs = (ArrayList<Notification>) findAll();

        for(Notification notif: notifs){
            if(notif.receiver() != candidate){
                notifs.remove(notif);
            }
        }
        return notifs;
    }

    @Override
    public Iterable<Notification> findCandidateUnseenNotifications(SystemUser candidate) {
        ArrayList<Notification> notifs = (ArrayList<Notification>) findAll();

        for(Notification notif: notifs){
            if(notif.state() != NotificationState.SENT){
                notifs.remove(notif);
            }
        }
        return notifs;
    }
}
