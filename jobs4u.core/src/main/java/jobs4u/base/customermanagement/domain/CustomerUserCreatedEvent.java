package jobs4u.base.customermanagement.domain;

import eapli.framework.domain.events.DomainEventBase;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

public class CustomerUserCreatedEvent extends DomainEventBase {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final String code;
    private final String address;
    private final SystemUser customerManager;
    private final SystemUser customerUser;

    public CustomerUserCreatedEvent(String name, String code, String address, SystemUser manager, SystemUser createdUser){
        this.name=name;
        this.code=code;
        this.address=address;
        this.customerManager=manager;
        this.customerUser=createdUser;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public SystemUser getCustomerUser() {
        return customerUser;
    }

    public SystemUser getCustomerManager() {
        return customerManager;
    }

    public String getCode() {
        return code;
    }
}
