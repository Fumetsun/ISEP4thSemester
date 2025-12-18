package jobs4u.base.notificationmanagement.domain;

import eapli.framework.domain.events.DomainEventBase;
import eapli.framework.infrastructure.authz.domain.model.Password;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

public class CheckNewNotificationsEvent extends DomainEventBase {


    private String password;

    public CheckNewNotificationsEvent(String password){

        this.password = password;
    }



    public String getPassword() {
        return password;
    }
}
