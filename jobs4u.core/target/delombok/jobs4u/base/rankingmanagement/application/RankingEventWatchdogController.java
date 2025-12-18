package jobs4u.base.rankingmanagement.application;

import eapli.framework.domain.events.DomainEvent;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.rankingmanagement.domain.Ranking;
import jobs4u.base.rankingmanagement.domain.RankingEvent;
import jobs4u.base.rankingmanagement.domain.RankingFactory;
import jobs4u.base.rankingmanagement.repositories.RankingRepository;

public class RankingEventWatchdogController {

    private final RankingRepository rankRepo = PersistenceContext.repositories().rankings();
    private RankingFactory factory = new RankingFactory();


    public Integer getHighestRank(JobOffer jobOffer) {
        return rankRepo.highestRankingOfJobOffer(jobOffer);
    }

    public Ranking save(Ranking rank) {
        return rankRepo.save(rank);
    }

    public Ranking createRanking(DomainEvent domainEvent, Integer number){

        return factory.createRanking(number, ((RankingEvent) domainEvent).getJobApplication(), ((RankingEvent) domainEvent).getJobOffer());
    }
}
