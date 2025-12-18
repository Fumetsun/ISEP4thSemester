package jobs4u.base.rankingmanagement.domain;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.infrastructure.pubsub.EventHandler;
import jobs4u.base.notificationmanagement.domain.CheckNewNotificationsEvent;
import jobs4u.base.rankingmanagement.application.RankingEventWatchdogController;
import org.springframework.transaction.annotation.Transactional;

public class RankingEventWatchdog implements EventHandler {

    // Define RankingEventWatchdogController as a field in your class
    private final RankingEventWatchdogController ctrl = new RankingEventWatchdogController();

    @Override
    public synchronized void onEvent(DomainEvent domainEvent) {
        assert domainEvent instanceof RankingEvent;

        Integer number = ctrl.getHighestRank(((RankingEvent) domainEvent).getJobOffer());


        Ranking rank = ctrl.createRanking(domainEvent, number);

        rank = ctrl.save(rank);

        if(rank != null){
            System.out.println("Ranking updated!");
        }else{
            System.out.println("Ranking update failed!");
        }
    }

}
