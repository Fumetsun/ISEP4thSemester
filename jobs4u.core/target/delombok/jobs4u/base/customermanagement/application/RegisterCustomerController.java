package jobs4u.base.customermanagement.application;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserManagementService;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import jobs4u.base.clientusermanagement.application.PasswordGenerator;
import jobs4u.base.customermanagement.domain.CustomerUserCreatedEvent;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RegisterCustomerController {

    private final AuthorizationService authorizationService = AuthzRegistry.authorizationService();
    private final UserManagementService userSvc = AuthzRegistry.userService();
    private final EventPublisher dispatcher = InProcessPubSub.publisher();

    public SystemUser registerUser(String name, String companyCode, String companyAddress, String email){
        SystemUser createdUser = userSvc.registerNewUser(email, generatePassword(), "Customer", name, email, defineRole());
        createCustomerUserCreatedEvent(name, companyCode, companyAddress, getLoggedUser(), createdUser);
        return createdUser;
    }

    private SystemUser getLoggedUser(){
        return authorizationService.loggedinUserWithPermissions(BaseRoles.CUSTOMER_MANAGER).get();      //Technically impossible to *not* exist.
    }

    private Set<Role> defineRole(){
        Set<Role> role = new HashSet<>();
        role.add(BaseRoles.CUSTOMER_MANAGER);
        return role;
    }

    private String generatePassword(){
        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .usePunctuation(true)
                .build();
        Random random = new Random();
        return passwordGenerator.generate(random.nextInt(9) + 8);
    }

    private void createCustomerUserCreatedEvent(String name, String code, String address, SystemUser manager, SystemUser createdUser){
        final DomainEvent event = new CustomerUserCreatedEvent(name, code, address, manager, createdUser);
        dispatcher.publish(event);
    }
}
