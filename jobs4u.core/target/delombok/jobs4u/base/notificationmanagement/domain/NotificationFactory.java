package jobs4u.base.notificationmanagement.domain;

import eapli.framework.domain.model.DomainFactory;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import java.time.LocalDate;

public class NotificationFactory implements DomainFactory<Notification> {
    private SystemUser candidate;
    private SystemUser customerManager;

    private NotificationInformation notifInfo;

    private CreationDate creationDate;
    private NotificationTypes notifType;

    public Notification newNotification(SystemUser candidate, SystemUser customerManager, String notifInfo, NotificationTypes notifType) {
        if (candidate == null) {
            throw new IllegalArgumentException("Candidate must not be null");
        }
        if (customerManager == null) {
            throw new IllegalArgumentException("Customer Manager must not be null");
        }
        if (notifType == null) {
            throw new IllegalArgumentException("Notification type must not be null");
        }
        if (notifInfo == null) {
            throw new IllegalArgumentException("Notification type must not be null");
        }
        this.candidate = candidate;
        this.customerManager = customerManager;
        this.notifType = notifType;
        this.creationDate = new CreationDate(LocalDate.now());
        this.notifInfo = new NotificationInformation(notifInfo);

        return build();
    }

    @Override
    public Notification build() {
        if (candidate == null) {
            throw new IllegalStateException("Candidate is not set");
        }
        if (customerManager == null) {
            throw new IllegalStateException("Customer Manager is not set");
        }
        if (notifType == null) {
            throw new IllegalStateException("Notification type is not set");
        }
        if (creationDate == null) {
            throw new IllegalStateException("Creation Date is not set");
        }
        if (notifInfo == null) {
            throw new IllegalStateException("Creation Date is not set");
        }


        return new Notification(candidate, customerManager,notifInfo, creationDate, notifType);
    }
}
