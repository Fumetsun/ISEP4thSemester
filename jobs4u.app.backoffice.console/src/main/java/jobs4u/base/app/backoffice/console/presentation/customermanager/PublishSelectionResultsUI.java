package jobs4u.base.app.backoffice.console.presentation.customermanager;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.notificationmanagement.application.PublishSelectionResultsController;
import jobs4u.base.notificationmanagement.domain.MessageDTO;

import java.util.ArrayList;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class PublishSelectionResultsUI extends AbstractUI{

    public PublishSelectionResultsController ctrl = new PublishSelectionResultsController();

    public String showJobOffers(Iterable<MessageDTO> jobOffers){
        int option = 0, i = 0;
        ArrayList<String> options = new ArrayList<>();
        for(MessageDTO offer : jobOffers){
            String[] offerInfo = offer.getData().split("/");
            options.add(offerInfo[1]);
            System.out.println(++i + "- " + offerInfo[0] + offerInfo[1]);
        }

        while(option <= 0){
            try{
                option = Console.readInteger("Choose a job offer to close:");
            }catch(Exception e){
                System.out.println("Invalid argument!");
            }
        }

        return options.get(option-1);
    }

    @Override
    protected boolean doShow() {

        String password = Console.readLine("Enter Password for confirmation:");
        try{
            Iterable<MessageDTO> jobOffers = ctrl.findAllOffers(password);

            if(!((ArrayList<MessageDTO>) jobOffers).isEmpty()){
                String offer = showJobOffers(jobOffers);
                ctrl.publishResults(offer, password);
            }else{
                System.out.println("Nothing to show.");
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return true;
    }

    @Override
    public String headline() {
        return "Publish job offer results";
    }
}
