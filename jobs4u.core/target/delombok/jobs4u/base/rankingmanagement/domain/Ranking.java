package jobs4u.base.rankingmanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.representations.dto.DTOable;
import jakarta.persistence.*;
import jobs4u.base.applicationmanagement.domain.JobApplication;
import jobs4u.base.joboffermanagement.domain.JobOffer;

@Entity
public class Ranking implements AggregateRoot<Long>, DTOable<RankingDTO> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rankId;

    @Embedded
    private Placement placement;

    @ManyToOne
    private JobApplication application;

    @ManyToOne
    private JobOffer jobOffer;

    public Ranking(Placement placement, JobApplication application, JobOffer jobOffer){
        this.placement = placement;
        this.application = application;
        this.jobOffer = jobOffer;
    }

    public Ranking() {
        //for ORM
    }

    @Override
    public boolean sameAs(Object other) {
        return false;
    }

    @Override
    public Long identity() {
        return rankId;
    }

    public JobOffer offer(){return this.jobOffer;}

    public JobApplication application(){return this.application;}

    public Placement placement(){return this.placement;}

    @Override
    public RankingDTO toDTO() {
        String appInfo = "Candidate: " + this.application.getCandidate().associatedUser().username().toString() + "| Application State: " + this.application.getState().toString() + "| Interview Result: " + this.application.getGrade();


        return new RankingDTO(this.jobOffer.reference().getRef(), this.application.identity(), appInfo, this.placement.value());
    }


    public Ranking updatePlacement(Placement placement) {
        this.placement = placement;
        return this;
    }
}
