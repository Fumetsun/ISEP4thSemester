package jobs4u.base.rankingmanagement.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.joboffermanagement.domain.JobRefCode;
import jobs4u.base.notificationmanagement.domain.Notification;
import jobs4u.base.rankingmanagement.domain.Ranking;
import jobs4u.base.rankingmanagement.domain.RankingDTO;

import java.util.Optional;

public interface RankingRepository extends DomainRepository<Long, Ranking> {

    default Optional<Ranking> findByRef(final Long id) {
        return ofIdentity(id);
    }

    public Iterable<Ranking> findAllRankings();

    public void setPlaceHolderValues(Iterable<Ranking> rankings);

    public Iterable<Ranking> getRankingsofJobOffer(JobOffer offer);

    Ranking rankingOfApplicationJobOffer(JobRefCode refCode, Integer appRefCode);

    Integer highestRankingOfJobOffer(JobOffer offer);
}
