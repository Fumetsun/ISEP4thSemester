package jobs4u.base.customermanagement.domain;

import eapli.framework.infrastructure.authz.domain.model.Name;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.common.domain.Address;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.customermanagement.domain.CustomerCode;
import jobs4u.base.customermanagement.domain.CustomerName;

public class CustomerFactory {

    public Customer createCustomer(String name, String code, String address, SystemUser customerManager, SystemUser customerUser){
        CustomerName customerName = new CustomerName(name);
        CustomerCode customerCode = new CustomerCode(code);
        Address customerAddress = new Address(address);

        return new Customer(customerCode, customerName, customerAddress, customerManager, customerUser);
    }
}
