package jobs4u.base.usermanagement;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserManagementService;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.util.ArrayList;
import java.util.List;

public class FilteredUserListingService {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final UserManagementService userSvc = AuthzRegistry.userService();


    public Iterable<SystemUser> allBackofficeUsers() {
        Iterable<SystemUser> ITERATOR = userSvc.allUsers();
        List<SystemUser> usersBackOffice = new ArrayList<>();
        for(SystemUser c : ITERATOR){
            if(! (c.roleTypes().contains(BaseRoles.CUSTOMER) || c.roleTypes().contains(BaseRoles.CANDIDATE))){
                usersBackOffice.add(c);
            }
        }
        return usersBackOffice;
    }
}
