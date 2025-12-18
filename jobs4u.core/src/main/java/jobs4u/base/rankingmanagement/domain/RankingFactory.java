package jobs4u.base.rankingmanagement.domain;

import jobs4u.base.applicationmanagement.domain.JobApplication;
import jobs4u.base.joboffermanagement.domain.JobOffer;

public class RankingFactory {
    /**
     * Creates a new Ranking instance.
     *
     * @param placementValue The placement value.
     * @param application The job application.
     * @param jobOffer The job offer.
     * @return A new Ranking instance.
     * @throws IllegalArgumentException if any parameter is invalid.
     */
    public Ranking createRanking(int placementValue, JobApplication application, JobOffer jobOffer) {
        if (placementValue <= 0) {
            throw new IllegalArgumentException("Placement value must be positive.");
        }
        if (application == null) {
            throw new IllegalArgumentException("Job application cannot be null.");
        }
        if (jobOffer == null) {
            throw new IllegalArgumentException("Job offer cannot be null.");
        }

        Placement placement = new Placement(placementValue);
        return new Ranking(placement, application, jobOffer);
    }

    public Ranking updatePlacement(Ranking rank, int placementValue){
        return rank.updatePlacement(new Placement(placementValue));
    }
}
