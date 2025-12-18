package jobs4u.base.notificationmanagement.domain;

import eapli.framework.domain.events.DomainEventBase;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import java.time.LocalDate;

public class JobApplicationNotificationEvent extends DomainEventBase {

    SystemUser sender;
    SystemUser receiver;
    String notifInfo;
    LocalDate creationDate;

    public JobApplicationNotificationEvent(SystemUser sender, SystemUser receiver, String info, LocalDate date){
        this.sender = sender;
        this.receiver = receiver;
        this.notifInfo = info;
        this.creationDate = date;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public String getNotifInfo() {
        return notifInfo;
    }

    public SystemUser getReceiver() {
        return receiver;
    }

    public SystemUser getSender() {
        return sender;
    }
}
