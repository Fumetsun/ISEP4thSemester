package jobs4u.base.customermanagement;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.customermanagement.repositories.CustomerRepository;
import jobs4u.base.infrastructure.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

public class FilteredCustomerListingService {
    private final RegisterCustomerService customerService = new RegisterCustomerService();
	private final CustomerRepository repo = PersistenceContext.repositories().customers();


    public Iterable<Customer> allCustomersOfAManager(SystemUser manager) {
		Iterable<Customer> customers = repo.findAllCustomersOfManager(manager);
		return customers;
        /* Iterable<Customer> ITERATOR = customerService.allUsers();
        List<Customer> customersOfManager = new ArrayList<>();
        for(Customer c : ITERATOR){
            if((c.customerManagerUser().username().equals(manager.username()))){
                customersOfManager.add(c);
            }
        }
        return customersOfManager; */
    }

}
