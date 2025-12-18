package jobs4u.base.notificationmanagement.application;

import jobs4u.base.notificationmanagement.InAppNotificationHandler;
import jobs4u.base.notificationmanagement.domain.MessageDTO;

public class CandidateInboxController {


    public Iterable<MessageDTO> getMyNotifications(String password, Integer option) {
        InAppNotificationHandler handler = new InAppNotificationHandler(password, option);

        return handler.getMyNotifications();
    }
}
