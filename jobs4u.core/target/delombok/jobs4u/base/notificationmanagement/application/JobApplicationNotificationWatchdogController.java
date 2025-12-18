package jobs4u.base.notificationmanagement.application;

import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.notificationmanagement.domain.JobApplicationNotificationEvent;
import jobs4u.base.notificationmanagement.domain.Notification;
import jobs4u.base.notificationmanagement.domain.NotificationFactory;
import jobs4u.base.notificationmanagement.domain.NotificationTypes;
import jobs4u.base.notificationmanagement.repositories.NotificationRepository;
import org.springframework.transaction.annotation.Transactional;

public class JobApplicationNotificationWatchdogController {

    private final NotificationRepository notifRepo = PersistenceContext.repositories().notifications();


    public Notification createNotification(JobApplicationNotificationEvent event) {
        return new NotificationFactory().newNotification(event.getReceiver(), event.getSender(), event.getNotifInfo(), NotificationTypes.IN_APP);
    }


    public Notification saveNotification(Notification notification){
        return notifRepo.save(notification);
    }
}
