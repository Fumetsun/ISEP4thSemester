package jobs4u.base.persistence.impl.inmemory;

import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.customermanagement.domain.CustomerCode;
import jobs4u.base.customermanagement.repositories.CustomerRepository;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.joboffermanagement.domain.JobRefCode;
import jobs4u.base.rankingmanagement.domain.Ranking;
import jobs4u.base.rankingmanagement.repositories.RankingRepository;

import java.util.ArrayList;

public class InMemoryRankingRepository extends InMemoryDomainRepository<Ranking, Long>
        implements RankingRepository {
    @Override
    public Iterable<Ranking> findAllRankings() {
        return findAll();
    }

    @Override
    public void setPlaceHolderValues(Iterable<Ranking> rankings) {

    }

    @Override
    public Iterable<Ranking> getRankingsofJobOffer(JobOffer offer) {
        ArrayList<Ranking> ranks = (ArrayList<Ranking>) findAllRankings();

        for(Ranking rank: ranks){
            if(rank.offer() != offer){
                ranks.remove(rank);
            }
        }
        return ranks;
    }

    @Override
    public Ranking rankingOfApplicationJobOffer(JobRefCode refCode, Integer appRefCode) {
        ArrayList<Ranking> ranks = (ArrayList<Ranking>) findAllRankings();

        for(Ranking rank: ranks){
            if(rank.offer().reference() != refCode && rank.application().identity() != appRefCode ){
                ranks.remove(rank);
            }
        }
        return ranks.get(0);
    }

    @Override
    public Integer highestRankingOfJobOffer(JobOffer offer) {
        ArrayList<Ranking> ranks = (ArrayList<Ranking>) findAllRankings();

        int num = 0;

        for(Ranking rank: ranks){
            if(rank.offer() == offer && rank.placement().value()>num    ){
                num = rank.placement().value();
            }
        }
        return num;
    }
}
