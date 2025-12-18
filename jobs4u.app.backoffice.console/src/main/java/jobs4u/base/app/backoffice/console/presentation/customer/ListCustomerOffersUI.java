package jobs4u.base.app.backoffice.console.presentation.customer;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.customermanagement.application.ListCustomerOffersController;
import jobs4u.base.notificationmanagement.domain.MessageDTO;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ListCustomerOffersUI extends AbstractUI{

    public ListCustomerOffersController theController = new ListCustomerOffersController();

    private void displayReceivedOffers(Iterable<MessageDTO> messages){
        System.out.printf("%n%-25s%-25s%-25s%-25s%n", "Job Offer", "Position", "Days since active", "Number of applicants");
        for(MessageDTO offer : messages){
            String[] offerInfo = offer.getData().split("/");
            System.out.printf("%-25s%-25s%-25s%-25s%n", offerInfo[0], offerInfo[1], offerInfo[2], offerInfo[3]);
        }
    }

    @Override
    protected boolean doShow() {
        String password = Console.readLine("Enter Password for confirmation:");
        try{
            displayReceivedOffers(theController.displayOffers(password));
        }catch(Exception e){
            System.out.println(e);
        }
        return true;
    }

    @Override
    public String headline() {
        return "Show job offer information";
    }
}
