package jobs4u.base.notificationmanagement.domain;

public enum NotificationState {

    SENT, DELIVERED;

    @Override
    public String toString() {
        switch (this) {
            case DELIVERED:
                return "Notification DELIVERED";
            case SENT:
                return "Notification SENT";
            default:
                return this.name();
        }
    }
}
