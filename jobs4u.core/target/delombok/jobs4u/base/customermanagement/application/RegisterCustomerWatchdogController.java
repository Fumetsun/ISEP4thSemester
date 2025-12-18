package jobs4u.base.customermanagement.application;

import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.customermanagement.domain.CustomerFactory;
import jobs4u.base.customermanagement.domain.CustomerUserCreatedEvent;

public class RegisterCustomerWatchdogController {

    private CustomerFactory factory = new CustomerFactory();

    public Customer registerCustomer(CustomerUserCreatedEvent event){
        Customer customerCreated = factory.createCustomer(event.getName(), event.getCode(), event.getAddress(), event.getCustomerManager(), event.getCustomerUser());

        return customerCreated;
    }

}
