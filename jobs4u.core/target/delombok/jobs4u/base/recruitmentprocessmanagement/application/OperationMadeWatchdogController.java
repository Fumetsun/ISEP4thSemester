package jobs4u.base.recruitmentprocessmanagement.application;

import jobs4u.base.joboffermanagement.JobOfferManagementService;
import jobs4u.base.joboffermanagement.domain.JobRefCode;
import jobs4u.base.recruitmentprocessmanagement.RecruitmentPhaseHandler;

public class OperationMadeWatchdogController {

    final JobOfferManagementService service = new JobOfferManagementService();
    final RecruitmentPhaseHandler handler = new RecruitmentPhaseHandler();

    public void incrementOperationCounter (JobRefCode refCode){
        handler.incrementOperationCounter(service.findByRef(refCode).get().recruitmentProcess().identity());
    }

}