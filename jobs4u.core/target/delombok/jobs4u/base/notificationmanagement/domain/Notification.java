package jobs4u.base.notificationmanagement.domain;

import java.io.Serializable;
import java.util.Objects;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.representations.dto.DTOable;
import jakarta.persistence.*;
import eapli.framework.domain.model.AggregateRoot;

@Entity
public class Notification implements AggregateRoot<Long>, Serializable, DTOable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private SystemUser sender;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private SystemUser receiver;

    NotificationTypes notifType;

    NotificationState notifState;

    NotificationInformation notfInfo;

    @Embedded
    private CreationDate creationDate;

    public Notification(final SystemUser sender, final SystemUser receiver,final NotificationInformation notifInfo, final CreationDate creationDate, final NotificationTypes notifType) {

        this.sender = sender;
        this.receiver = receiver;
        this.creationDate = creationDate;
        this.notifType = notifType;
        this.notfInfo = notifInfo;

        if(notifType == NotificationTypes.EMAIL){
            this.notifState = NotificationState.DELIVERED;
        }else{
            this.notifState = NotificationState.SENT;
        }
    }

    protected Notification() {
        //for ORM
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(sender, that.sender) &&
                Objects.equals(receiver, that.receiver) &&
                Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sender, receiver, creationDate);
    }

    @Override
    public Long identity() {
        return this.id;
    }

    @Override
    public boolean sameAs(Object other) {
        return equals(other);
    }

    @Override
    public String toString() {
        return String.format("Notification[id=%d, candidate=%s, customerManager=%s, creationDate=%s]",
                id, sender.username().toString(), receiver.username().toString(), creationDate.toString());
    }

    public NotificationState state(){return this.notifState;}

    public SystemUser sender(){return this.sender;}

    public SystemUser receiver(){return this.receiver;}

    public CreationDate creation(){return this.creationDate;}

    public NotificationInformation notifInformation(){return this.notifInformation();}

    public void updateNotifState(NotificationState notifState){
        this.notifState = notifState;
    }

    @Override
    public MessageDTO toDTO() {
        String notif = "Notification from " + this.sender.username() + ": " + this.notfInfo.toString();

        byte[] notifData = notif.getBytes();
        int notifDataLen = notifData.length;
        int l = notifDataLen & 0xFF;
        int m = (notifDataLen >> 8) & 0xFF;
        return new MessageDTO(notif,l, m);
    }
}
