package jobs4u.base.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import jakarta.persistence.TypedQuery;
import jobs4u.base.Application;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.joboffermanagement.domain.JobRefCode;
import jobs4u.base.rankingmanagement.domain.Placement;
import jobs4u.base.rankingmanagement.domain.Ranking;
import jobs4u.base.rankingmanagement.domain.RankingFactory;
import jobs4u.base.rankingmanagement.repositories.RankingRepository;

public class JpaRankingRepository
        extends JpaAutoTxRepository<Ranking, Long, Long>
        implements RankingRepository {


    public JpaRankingRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "rankId");
    }

    public JpaRankingRepository(TransactionalContext tx) {
        super(tx, "rankId");
    }



    @Override
    public Iterable<Ranking> findAllRankings() {
        return findAll();
    }

    @Override
    public void setPlaceHolderValues(Iterable<Ranking> rankings) {
        for(Ranking rank : rankings){
            RankingFactory rankFactory = new RankingFactory();

            save(rankFactory.updatePlacement(rank,rank.placement().value() * -1));
        }
    }

    @Override
    public Iterable<Ranking> getRankingsofJobOffer(JobOffer offer) {

        final TypedQuery<Ranking> q = createQuery("SELECT e FROM Ranking e WHERE  e.jobOffer = :offer ORDER BY e.placement ASC",
                Ranking.class);
        q.setParameter("offer", offer);

        return q.getResultList();
    }

    @Override
    public Ranking rankingOfApplicationJobOffer(JobRefCode refCode, Integer appRefCode) {
        final TypedQuery<Ranking> q = createQuery("SELECT e FROM Ranking e WHERE  e.jobOffer.jobRefCode = :offer AND e.application.appRefCode = :app",
                Ranking.class);
        q.setParameter("offer", refCode);
        q.setParameter("app", appRefCode);

        return q.getSingleResult();
    }

    @Override
    public Integer highestRankingOfJobOffer(JobOffer offer) {
        TypedQuery<Integer> q = createQuery(
                "SELECT MAX(e.placement.value) FROM Ranking e WHERE e.jobOffer = :offer",
                Integer.class);
        q.setParameter("offer", offer);

        if(q.getSingleResult() == null){
            return 1;
        }else{
            int thing = q.getSingleResult() +1;
            return thing;
        }

    }

}
