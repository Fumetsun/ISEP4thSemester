package jobs4u.base.notificationmanagement.domain;



import eapli.framework.domain.model.ValueObject;

import java.io.Serializable;
import java.util.Objects;

public class NotificationInformation implements Serializable, ValueObject {
    private final String information;

    public NotificationInformation(String information) {
        if (information == null || information.isEmpty()) {
            throw new IllegalArgumentException("Information must not be null or empty");
        }
        this.information = information;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationInformation that = (NotificationInformation) o;
        return Objects.equals(information, that.information);
    }


    @Override
    public String toString() {
        return "NotificationInformation{" +
                "information='" + information + '\'' +
                '}';
    }

    public String getInformation() {
        return information;
    }
}
