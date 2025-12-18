package jobs4u.base.app.backoffice.console.presentation.customermanager;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.recruitmentprocessmanagement.application.ChangeJobOfferPhaseController;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentPhase;
import jobs4u.base.joboffermanagement.domain.dto.JobOfferDto;
import jobs4u.base.recruitmentprocessmanagement.domain.dto.RecruitmentProcessDTO;

import java.util.ArrayList;
import java.util.List;

public class ChangeJobOfferPhaseUI extends AbstractUI {

    private final ChangeJobOfferPhaseController controller = new ChangeJobOfferPhaseController();

    @Override
    protected boolean doShow() {
        List<JobOfferDto> jobOffers = (ArrayList) controller.getJobOffers();
        if (jobOffers.size() != 0) {
            System.out.println("Please select the JobOffer you wish to change the current phase: ");
            for (int i = 0; i < jobOffers.size(); i++) {
                System.out.println((i+1) + ". " + jobOffers.get(i));
            }
            int answer = getAnswer(jobOffers.size());

            doShowPhase(jobOffers, answer-1);

        } else {
            System.out.println("Current User doesn't have any JobOffers associated to their customers.");
        }

        return false;
    }

    private void doShowPhase(List<JobOfferDto> jobOffers, int answer) {
        System.out.println("\n== JobOffer " + jobOffers.get(answer).getTitle() + " ==");

        RecruitmentProcessDTO recruitmentProcess = jobOffers.get(answer).getRecruitmentProcess();

        System.out.println("Current " + recruitmentProcess.getCurrentPhase());
        System.out.println("\nAll phases:");

        for (RecruitmentPhase phase : recruitmentProcess.getAllPhases()) {
            System.out.println("\t- " + phase.name());
        }

        int action = askPhaseAction();
        String operationResult = "ERROR";
        
        if (action == 1) {
            operationResult = controller.incrementPhase(recruitmentProcess);
        } else if (action == 2) {
            operationResult = controller.decreasePhase(recruitmentProcess);
        }
        
        if (action!=0){
            System.out.println(operationResult);
        }

    }

    private int askPhaseAction() {
        int action;
        System.out.println("\nWhat would you like to do? " +
                "\n1. Go to the next phase." +
                "\n2. Go to the previous phase." +
                "\n0. Go back.");
        do {
            action = Console.readInteger("\nAction: ");
            if (action != 0 && action != 1 && action != 2) {
                System.out.println("\n[ERROR] Number inputted does not exist. Try again.\n");
            }
        } while (action != 0 && action != 1 && action != 2);

        return action;
    }

    private int getAnswer(int size) {
        int answer;
        do {
            answer = Console.readInteger("Job Offer Number: ");
            if (answer <= 0 || answer > size) {
                System.out.println("\n[ERROR] Number inputted does not exist. Try again.\n");
            }
        } while (answer <= 0 || answer > size);
        return answer;
    }

    @Override
    public String headline() {
        return "Change Job Offer's Phase";
    }
}
