package jobs4u.base.customermanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.representations.dto.DTOable;
import jakarta.persistence.*;
import jobs4u.base.common.domain.Address;
import jobs4u.base.customermanagement.domain.dto.CustomerDTO;

@Entity
public class Customer implements AggregateRoot<CustomerCode>, DTOable<CustomerDTO> {

	@EmbeddedId
	private CustomerCode customerCode;

	@Column(columnDefinition = "varchar(255)")
	private CustomerName customerName;

	private Address customerAddress;

	@ManyToOne
	private SystemUser customerManagerUser;

	@OneToOne
	private SystemUser customerUser;

	public Customer(CustomerCode code, CustomerName name, Address address, SystemUser manager, SystemUser user) {
		this.customerCode = code;
		this.customerName = name;
		this.customerAddress = address;
		this.customerManagerUser = manager;
		this.customerUser = user;
	}

	public Customer() {
	}

	public CustomerCode customerCode() {
		return this.customerCode;
	}

	public CustomerName customerName() {
		return this.customerName;
	}

	public Address address() {
		return this.customerAddress;
	}

	public SystemUser customerManagerUser() {
		return this.customerManagerUser;
	}

	public SystemUser customerUser() {
		return this.customerUser;
	}

	@Override
	public boolean sameAs(Object other) {
		if (!(other instanceof Customer)) {
			return false;
		}
		Customer that = (Customer) other;
		if (this == that) {
			return true;
		} else
			return this.customerCode.equals(that.customerCode) && this.customerUser.equals(that.customerUser)
					&& this.customerName.equals(that.customerName) && this.customerAddress.equals(that.customerAddress)
					&& this.customerManagerUser.equals(that.customerManagerUser);
	}

	@Override
	public CustomerCode identity() {
		return this.customerCode;
	}

	@Override
	public String toString() {
		return ("Customer Code: " + customerCode + "\nCustomer Name: " + customerName
				+ "\nCustomer Address: " + customerAddress +
				"\nCustomer User: " +
				"\n - Username: " + customerUser.username()
				+ "\nCustomer Manager: " +
				"\n - Username: " + customerManagerUser.username());
	}

	@Override
	public CustomerDTO toDTO() {
		return new CustomerDTO(customerCode.toString(), customerName.toString(), customerAddress.toString(),
				customerManagerUser.identity().toString(), customerUser.identity().toString());
	}

	@Override
	public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        final Customer that = (Customer) o;
		return this.customerCode.toString().equals(that.customerCode.toString());
	}
}
