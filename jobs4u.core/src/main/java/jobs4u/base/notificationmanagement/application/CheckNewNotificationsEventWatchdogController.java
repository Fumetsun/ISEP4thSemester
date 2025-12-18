package jobs4u.base.notificationmanagement.application;

import eapli.framework.infrastructure.authz.domain.model.Password;
import jobs4u.base.notificationmanagement.CheckNotificationHandler;

public class CheckNewNotificationsEventWatchdogController {

    public boolean checkForNotifications(String password){
        CheckNotificationHandler handler = new CheckNotificationHandler(password);
        return handler.checkNotifications();
    };
}
