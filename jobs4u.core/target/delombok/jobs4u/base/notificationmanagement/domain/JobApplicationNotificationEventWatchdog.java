package jobs4u.base.notificationmanagement.domain;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.infrastructure.pubsub.EventHandler;
import jobs4u.base.notificationmanagement.application.JobApplicationNotificationWatchdogController;
import org.springframework.transaction.annotation.Transactional;

public class JobApplicationNotificationEventWatchdog implements EventHandler {
    final JobApplicationNotificationWatchdogController controller = new JobApplicationNotificationWatchdogController();

    @Override
    public synchronized void onEvent(final DomainEvent domainevent) {
        assert domainevent instanceof JobApplicationNotificationEvent;

        final JobApplicationNotificationEvent event = (JobApplicationNotificationEvent) domainevent;

        Notification notif = controller.createNotification(event);

        controller.saveNotification(notif);

        System.out.println("Notification created, candidate will be informed on next app startup");

    }
}
