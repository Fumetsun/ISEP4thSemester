package jobs4u.base.recruitmentprocessmanagement;

import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentPhase;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcess;
import jobs4u.base.recruitmentprocessmanagement.domain.dto.RecruitmentProcessDTO;
import jobs4u.base.recruitmentprocessmanagement.repositories.RecruitmentProcessRepository;

public class RecruitmentPhaseHandler {

    RecruitmentProcessRepository repo = PersistenceContext.repositories().processes();

    public String incrementPhase(RecruitmentProcessDTO process) {
        if (!checkIfItsLastPhase(process)) {
            int indexCurrent = searchForIndex(process);
            updateRecruitmentProcessPhase(process.getRecruitmentRefCode(), indexCurrent+1);
            return ("Operation Successful.");
        }
        return "Operation Failed: Current Phase is the last Phase. It's not possible to advance to the next one.";
    }

    private boolean checkIfItsLastPhase(RecruitmentProcessDTO process) {
        return process.getAllPhases().get(process.getAllPhases().size() - 1).name().equalsIgnoreCase(process.getCurrentPhase().name());
    }

    public String decreasePhase(RecruitmentProcessDTO process) {
        if (!checkIfOperationsHaveBeenMade(process)) {
            int indexCurrent = searchForIndex(process);
            if (!(indexCurrent==0)) {
                updateRecruitmentProcessPhase(process.getRecruitmentRefCode(), indexCurrent - 1);
                return ("Operation Successful.");
            } else {
                return "Operation Failed: Current Phase is the first phase. It's not possible to go to a previous phase, since it doesn't exist.";
            }
        }
        return "Operation Failed: Current Phase has already been operated. It is not allowed to go to the previous phase, unless no operation has been made in the current phase.";
    }

    private boolean checkIfOperationsHaveBeenMade(RecruitmentProcessDTO process) {
        return process.getCurrentPhase().numberOfOperations() != 0;
    }

    private int searchForIndex(RecruitmentProcessDTO process) {
        int index = 0;
        for (RecruitmentPhase phase : process.getAllPhases()) {
            if (phase.name().equalsIgnoreCase(process.getCurrentPhase().name())) {
                break;
            }
            index++;
        }
        return index;
    }


    public void updateRecruitmentProcessPhase(Integer processRef, Integer newPhaseRef){
        RecruitmentProcess process = repo.findByRef(processRef).get();
        process.updateCurrentPhase(newPhaseRef);
        repo.save(process);
    }

    public void incrementOperationCounter(Integer processRef){
        RecruitmentProcess process = repo.findByRef(processRef).get();

        int index = 0;
        for (RecruitmentPhase phase : process.phases()) {
            if (phase.name().equalsIgnoreCase(process.currentPhase().name())) {
                break;
            }
            index++;
        }

        process.phases().get(index).incrementOperationCount();
        process.currentPhase().incrementOperationCount();

        repo.save(process);
    }
}
