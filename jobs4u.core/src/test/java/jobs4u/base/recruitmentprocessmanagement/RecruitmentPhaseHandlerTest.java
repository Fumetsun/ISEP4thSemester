package jobs4u.base.recruitmentprocessmanagement;

import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentPhase;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcess;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcessFactory;
import jobs4u.base.recruitmentprocessmanagement.domain.dto.RecruitmentProcessDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecruitmentPhaseHandlerTest {

    private final RecruitmentProcessFactory processFactory = new RecruitmentProcessFactory();

    public RecruitmentProcessDTO createProcessForTesting(int currentPhase){
        List<RecruitmentPhase> process = new ArrayList<>();

        process.add(new RecruitmentPhase("Planning","Planning what we will evaluate", "09/1/2000-09/02/2000"));
        process.add(new RecruitmentPhase("Interview","Interview the candidates", "09/3/2000-09/04/2000"));
        process.add(new RecruitmentPhase("Debating","Debating between Members", "09/4/2000-09/05/2000"));
        process.add(new RecruitmentPhase("Interview 2","Interview the finalists", "09/5/2000-09/06/2000"));

        RecruitmentProcess recruitmentProcess = processFactory.createRecruitmentProcess(process, process.get(currentPhase));
        return recruitmentProcess.toDTO();
    }

    @Test
    void testIfIncrementPhaseWorks() {
        RecruitmentProcessDTO recruitmentProcessDTO = createProcessForTesting(0);

        assertEquals("Operation Successful.", incrementPhase(recruitmentProcessDTO)); //Check output message.
        assertEquals("Interview",recruitmentProcessDTO.getCurrentPhase().name()); //Check if it actually changed.
    }

    @Test
    void testIfIncrementPhaseJustifiesCorrectly() {
        RecruitmentProcessDTO recruitmentProcessDTO = createProcessForTesting(3);

        assertEquals("Operation Failed: Current Phase is the last Phase. It's not possible to advance to the next one.", incrementPhase(recruitmentProcessDTO)); //Checks output message.
        assertEquals("Interview 2",recruitmentProcessDTO.getCurrentPhase().name()); //Check if it changed anything.
    }

    @Test
    void testIfDecreasePhaseWorks() {
        RecruitmentProcessDTO recruitmentProcessDTO = createProcessForTesting(1);

        assertEquals("Operation Successful.", decreasePhase(recruitmentProcessDTO)); //Check output message.
        assertEquals("Planning",recruitmentProcessDTO.getCurrentPhase().name()); //Check if it actually changed.
    }

    @Test
    void testIfDecreasePhaseJustifiesCorrectly_Limit() {
        RecruitmentProcessDTO recruitmentProcessDTO = createProcessForTesting(0);

        assertEquals("Operation Failed: Current Phase is the first phase. It's not possible to go to a previous phase, since it doesn't exist.", decreasePhase(recruitmentProcessDTO)); //Check output message.
        assertEquals("Planning",recruitmentProcessDTO.getCurrentPhase().name()); //Check if it changed anything.
    }

    @Test
    void testIfDecreasePhaseJustifiesCorrectly_Operations() {
        RecruitmentProcessDTO recruitmentProcessDTO = createProcessForTesting(1);

        recruitmentProcessDTO.getCurrentPhase().incrementOperationCount();

        assertEquals("Operation Failed: Current Phase has already been operated. It is not allowed to go to the previous phase, unless no operation has been made in the current phase.", decreasePhase(recruitmentProcessDTO)); //Check output message.
        assertEquals("Interview",recruitmentProcessDTO.getCurrentPhase().name()); //Check if it changed anything.
    }

    @Test
    void testIfUpdateCurrentRecruitmentProcessPhaseWorks() {
        RecruitmentProcessDTO recruitmentProcessDTO = createProcessForTesting(0);

        incrementPhase(recruitmentProcessDTO);
        assertEquals("Interview",recruitmentProcessDTO.getCurrentPhase().name()); //Check if it actually changed.

        decreasePhase(recruitmentProcessDTO);
        assertEquals("Planning",recruitmentProcessDTO.getCurrentPhase().name()); //Check if it actually changed.

        incrementPhase(recruitmentProcessDTO);
        incrementPhase(recruitmentProcessDTO);
        assertEquals("Debating",recruitmentProcessDTO.getCurrentPhase().name()); //Check if it actually changed.

        incrementPhase(recruitmentProcessDTO);
        assertEquals("Interview 2",recruitmentProcessDTO.getCurrentPhase().name()); //Check if it actually changed.
    }

    // Classes from the Handler WITHOUT the use of the repository ==========================================================================================
    public String incrementPhase(RecruitmentProcessDTO process) {
        if (!checkIfItsLastPhase(process)) {
            int indexCurrent = searchForIndex(process);
            //updateRecruitmentProcessPhase(process.getRecruitmentRefCode(), indexCurrent + 1);
            process.updateCurrentPhase(indexCurrent+1);
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
            if (!(indexCurrent == 0)) {
                //updateRecruitmentProcessPhase(process, indexCurrent - 1);
                process.updateCurrentPhase(indexCurrent-1);
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

}