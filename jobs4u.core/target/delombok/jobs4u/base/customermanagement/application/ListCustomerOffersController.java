package jobs4u.base.customermanagement.application;

import jobs4u.base.customermanagement.ServerCustomerOffersHandler;
import jobs4u.base.notificationmanagement.domain.MessageDTO;

public class ListCustomerOffersController {
    public Iterable<MessageDTO> displayOffers(String password){
        ServerCustomerOffersHandler handler = new ServerCustomerOffersHandler(password);

        return handler.getMyOffers();
    }
}
