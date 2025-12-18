package jobs4u.base.notificationmanagement.domain;

import eapli.framework.domain.model.ValueObject;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class CreationDate implements Serializable, ValueObject {


    private LocalDate date;

    public CreationDate(final LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        this.date = date;
    }

    protected CreationDate(){
        //for ORM
    }

    public LocalDate date() {
        return this.date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreationDate that = (CreationDate) o;
        return date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    @Override
    public String toString() {
        return date.toString();
    }
}

