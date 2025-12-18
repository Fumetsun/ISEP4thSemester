package jobs4u.base.app.backoffice.console.presentation.candidate;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.candidatemanagement.application.ListOwnCandidateApplicationsController;
import jobs4u.base.notificationmanagement.domain.MessageDTO;

import java.util.ArrayList;
import java.util.List;

public class ListOwnCandidateApplicationsUI extends AbstractUI {

    ListOwnCandidateApplicationsController ctrl = new ListOwnCandidateApplicationsController();

    @Override
    protected boolean doShow() {

        String password = Console.readLine("Enter Password for confirmation:");
        try{
            Iterable<MessageDTO> jobApplications = ctrl.listOwnApplications(password);
            for (MessageDTO message : jobApplications) {
                System.out.println(message.getData());
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }

    @Override
    public String headline() {
        return "List my own Applications";
    }
}
