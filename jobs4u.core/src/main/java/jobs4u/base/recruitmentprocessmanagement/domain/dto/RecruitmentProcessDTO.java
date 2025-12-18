package jobs4u.base.recruitmentprocessmanagement.domain.dto;

import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentPhase;

import java.util.ArrayList;
import java.util.List;

public class RecruitmentProcessDTO {

    private Integer recruitmentRefCode;

    private List<RecruitmentPhase> allPhases;

    private RecruitmentPhase currentPhase;

    public RecruitmentProcessDTO(Integer refCode, List<RecruitmentPhase> phases, RecruitmentPhase currentPhase){
        this.recruitmentRefCode=refCode;
        this.allPhases=phases;
        this.currentPhase=currentPhase;
    }

    public void updateCurrentPhase(Integer newPhase){
        this.currentPhase= allPhases.get(newPhase);
    }

    public List<RecruitmentPhase> getAllPhases() {
        return allPhases;
    }

    public Integer getRecruitmentRefCode() {
        return recruitmentRefCode;
    }

    public RecruitmentPhase getCurrentPhase() {
        return currentPhase;
    }
}
