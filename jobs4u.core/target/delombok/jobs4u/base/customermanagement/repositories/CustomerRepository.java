package jobs4u.base.customermanagement.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.customermanagement.domain.CustomerCode;

import java.util.Optional;

public interface CustomerRepository extends DomainRepository<CustomerCode, Customer> {

    Optional<Customer> findByRef(CustomerCode code);

    Customer findByUser(SystemUser customer);

    Iterable<Customer> findAllCustomers();

	Iterable<Customer> findAllCustomersOfManager(SystemUser manager);
}
