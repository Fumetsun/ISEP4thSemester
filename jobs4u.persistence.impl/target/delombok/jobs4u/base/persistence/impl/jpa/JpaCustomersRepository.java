package jobs4u.base.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import jakarta.persistence.TypedQuery;
import jobs4u.base.Application;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.customermanagement.domain.CustomerCode;
import jobs4u.base.customermanagement.repositories.CustomerRepository;

import java.util.Optional;

public class JpaCustomersRepository extends JpaAutoTxRepository<Customer, CustomerCode, CustomerCode>
		implements CustomerRepository {

	public JpaCustomersRepository(final TransactionalContext autoTx) {
		super(autoTx, "customerCode");
	}

	public JpaCustomersRepository(final String puname) {
		super(puname, Application.settings().getExtendedPersistenceProperties(),
				"customerCode");
	}

	@Override
	public Optional<Customer> findByRef(final CustomerCode code) {
		return ofIdentity(code);
	}

	@Override
	public Customer findByUser(SystemUser customer) {
		return createQuery(
					"select c from Customer c where c.customerUser = :customer", Customer.class)
		.setParameter("customer", customer).getSingleResult();
	}

	@Override
	public Iterable<Customer> findAllCustomers() {
		return findAll();
	}

	@Override
	public Iterable<Customer> findAllCustomersOfManager(SystemUser manager) {
		// example: select * from customer where customermanageruser_username =
		// 'cm@email.org'
		// final TypedQuery<Customer> query =
		return createQuery(
				"select c from Customer c where c.customerManagerUser = :manager", Customer.class)
				.setParameter("manager", manager/*.username().toString()*/).getResultList();
		// return query.getResultList();
		// TODO needs checking (?)
	}

	;

	/*
	 * @Override
	 * public Optional<ClientUser> findByUsername(final Username name) {
	 * final Map<String, Object> params = new HashMap<>();
	 * params.put("name", name);
	 * return matchOne("e.systemUser.username=:name", params);
	 * }
	 * 
	 * @Override
	 * public Optional<ClientUser> findByMecanographicNumber(final
	 * MecanographicNumber number) {
	 * final Map<String, Object> params = new HashMap<>();
	 * params.put("number", number);
	 * return matchOne("e.mecanographicNumber=:number", params);
	 * }
	 * 
	 * @Override
	 * public Iterable<ClientUser> findAllActive() {
	 * return match("e.systemUser.active = true");
	 * }
	 */
}
