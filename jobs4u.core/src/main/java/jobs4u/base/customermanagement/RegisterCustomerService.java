package jobs4u.base.customermanagement;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.customermanagement.repositories.CustomerRepository;
import jobs4u.base.infrastructure.persistence.PersistenceContext;

public class RegisterCustomerService {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private final CustomerRepository repo = PersistenceContext.repositories().customers();

    public boolean addCustomer(Customer customer) {
        if(customer == null){ return false; }
        repo.save(customer);
        return true;
    }

    public Iterable<Customer> allUsers() {
        return this.repo.findAll();
    }

}
