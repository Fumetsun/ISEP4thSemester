package jobs4u.base.recruitmentprocessmanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.representations.dto.DTOable;
import jakarta.persistence.*;
import jobs4u.base.recruitmentprocessmanagement.domain.dto.RecruitmentProcessDTO;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
public class RecruitmentProcess implements AggregateRoot<Integer>, DTOable<RecruitmentProcessDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recruitmentRefCode;

    @ElementCollection
    private List<RecruitmentPhase> allPhases;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "CurrentPhase_name")),
            @AttributeOverride(name = "description", column = @Column(name = "CurrentPhase_description")),
            @AttributeOverride(name = "phaseDatePeriod", column = @Column(name = "CurrentPhase_phaseDatePeriod")),
            @AttributeOverride(name = "numberOfOperations", column = @Column(name = "CurrentPhase_numberOfOperations"))
    })
    private RecruitmentPhase currentPhase;

    public RecruitmentProcess(List<RecruitmentPhase> phases, RecruitmentPhase currentPhase){
        this.allPhases=phases;
        this.currentPhase=currentPhase;
    }

    protected RecruitmentProcess() {
        // for ORM only
    }

    public List<RecruitmentPhase> phases() {
        return this.allPhases;
    }

    public RecruitmentPhase currentPhase(){
        return this.currentPhase;
    }

    public void updateCurrentPhase(Integer newPhase){
        this.currentPhase= allPhases.get(newPhase);
    }

    public boolean addPhase(RecruitmentPhase phase){
        if (!this.allPhases.contains(phase)){
            this.allPhases.add(phase);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof RecruitmentProcess)) {
            return false;
        }
        RecruitmentProcess that = (RecruitmentProcess) other;
        if (this == that) {
            return true;
        } else
            return this.recruitmentRefCode.intValue()==that.recruitmentRefCode.intValue();
    }

    @Override
    public Integer identity() {
        return this.recruitmentRefCode;
    }

    @Override
    public RecruitmentProcessDTO toDTO() {
        return new RecruitmentProcessDTO(this.recruitmentRefCode, this.allPhases, this.currentPhase);
    }
}
