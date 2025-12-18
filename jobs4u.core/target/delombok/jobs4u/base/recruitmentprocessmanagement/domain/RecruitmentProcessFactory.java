package jobs4u.base.recruitmentprocessmanagement.domain;

import java.util.ArrayList;
import java.util.List;

public class RecruitmentProcessFactory {

    public RecruitmentProcess createRecruitmentProcess(List<RecruitmentPhase> process, RecruitmentPhase current){
        return new RecruitmentProcess(process, current);
    }

    public RecruitmentProcess createRecruitmentProcess(){
        return new RecruitmentProcess();
    }

}
