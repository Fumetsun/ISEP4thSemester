package jobs4u.base.persistence.impl.inmemory;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.customermanagement.domain.CustomerCode;
import jobs4u.base.customermanagement.repositories.CustomerRepository;


import java.util.Optional;

public class InMemoryCustomerRepository extends InMemoryDomainRepository<Customer, CustomerCode>
        implements CustomerRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public Optional<Customer> findByRef(final CustomerCode code) {
        return Optional.of(data().get(code));
    }

    public Customer findByUser(SystemUser customer){
        System.out.println("Unsupported operation for in-memory");
        throw new UnsupportedOperationException("Unimplemented method 'findByUser'");
    }

    @Override
    public Iterable<Customer> findAllCustomers(){
        return findAll();
    }

	@Override
	public Iterable<Customer> findAllCustomersOfManager(SystemUser manager) {
		System.out.println("Unsupported operation for in-memory");
		throw new UnsupportedOperationException("Unimplemented method 'findAllCustomersOfManager'");
	}

}
