package jobs4u.base.customermanagement.domain;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.infrastructure.pubsub.EventHandler;
import jobs4u.base.customermanagement.RegisterCustomerService;
import jobs4u.base.customermanagement.application.RegisterCustomerWatchdogController;

public class CustomerUserCreatedEventWatchDog implements EventHandler {

    private RegisterCustomerService service = new RegisterCustomerService();

    @Override
    public void onEvent(final DomainEvent domainevent) {
        assert domainevent instanceof CustomerUserCreatedEvent;

        final CustomerUserCreatedEvent event = (CustomerUserCreatedEvent) domainevent;

        final RegisterCustomerWatchdogController controller = new RegisterCustomerWatchdogController();
        Customer customerCreated = controller.registerCustomer(event);

        service.addCustomer(customerCreated);

        System.out.println("\n== Customer Created Successfully! ==\n" + customerCreated);

    }
}
