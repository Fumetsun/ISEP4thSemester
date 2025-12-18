package jobs4u.base.rankingmanagement.domain;

import eapli.framework.domain.events.DomainEventBase;
import jobs4u.base.applicationmanagement.domain.JobApplication;
import jobs4u.base.joboffermanagement.domain.JobOffer;

public class RankingEvent extends DomainEventBase {

    private JobOffer jobOffer;
    private JobApplication jobApplication;

    public RankingEvent(JobOffer offer, JobApplication application){
        this.jobOffer = offer;
        this.jobApplication = application;
    }

    public JobApplication getJobApplication() {
        return jobApplication;
    }

    public JobOffer getJobOffer() {
        return jobOffer;
    }
}
