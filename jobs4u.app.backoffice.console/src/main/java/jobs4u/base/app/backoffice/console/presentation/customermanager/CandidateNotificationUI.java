package jobs4u.base.app.backoffice.console.presentation.customermanager;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.applicationmanagement.domain.JobApplication;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.notificationmanagement.application.CandidateNotificationController;

import java.util.ArrayList;
import java.util.Scanner;

public class CandidateNotificationUI extends AbstractUI {

    CandidateNotificationController ctrl = new CandidateNotificationController();
    @Override
    protected boolean doShow() {
        ArrayList<JobApplication> apps = (ArrayList<JobApplication>) ctrl.getMyCandidates();

        System.out.println("-=-=-=- Candidate Notification Interface -=-=-=-");
        while(true){
            int i=1;

            for(JobApplication app: apps){
                System.out.println(i + "#" + app.toString());
                i++;
            }

            Integer choice = Console.readInteger("Select a Job Application (0 to exit)");

            if(choice == 0){
                return true;
            }
            String password = Console.readLine("Enter Password for confirmation:");
            ctrl.sendEmail(apps.get(choice-1), password);
            System.out.println("Email request forwarded to follow up server!");
        }


    }

    @Override
    public String headline() {
        return "Notify a Candidate of Their Requirement Verification";
    }
}
