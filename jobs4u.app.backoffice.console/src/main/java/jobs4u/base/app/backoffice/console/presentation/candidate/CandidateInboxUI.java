package jobs4u.base.app.backoffice.console.presentation.candidate;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.notificationmanagement.InAppNotificationHandler;
import jobs4u.base.notificationmanagement.application.CandidateInboxController;
import jobs4u.base.notificationmanagement.domain.MessageDTO;
import jobs4u.base.notificationmanagement.domain.Notification;

public class CandidateInboxUI extends AbstractUI {

    CandidateInboxController ctrl = new CandidateInboxController();

    @Override
    protected boolean doShow() {
        String password = Console.readLine("Verify Identity to proceed:");


        System.out.println("1- View New Notifications");
        System.out.println("2- View All Notifications");
        Integer option = Console.readInteger("Option:");

        Iterable<MessageDTO> notifs=  ctrl.getMyNotifications(password,option);

        if(notifs != null){
            for(MessageDTO notif : notifs){
                System.out.println(notif.getData());
            }
        }else{
            System.out.println("Something went wrong when contacting follow-up server, common issues stem from WRONG PASSWORD.");
        }


        return true;
    }

    @Override
    public String headline() {
        return "-=-=-=- Inbox -=-=-=-";
    }
}
