package jobs4u.base.notificationmanagement.application;

import jobs4u.base.notificationmanagement.ResultsNotificationHandler;
import jobs4u.base.notificationmanagement.domain.MessageDTO;

public class PublishSelectionResultsController {

    public Iterable<MessageDTO> findAllOffers(String password) throws ClassNotFoundException{
        ResultsNotificationHandler handler = new ResultsNotificationHandler(password);
        return handler.getJobOffers();
    }

    public void publishResults(String offer, String password) {
        ResultsNotificationHandler handler = new ResultsNotificationHandler(offer, password);
        handler.emailCandidates();
    }

}
