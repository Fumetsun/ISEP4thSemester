package jobs4u.base.rankingmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.UniqueConstraint;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Placement implements ValueObject, Serializable {

    @Column(name = "placement_value")
    private Integer value;



    // Constructor to initialize the placement value
    public Placement(int value) {
        this.value = value;
    }

    protected Placement() {
        //for ORM
    }

    public int value() {return value;}

    // Override equals and hashCode for value object comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Placement placement = (Placement) o;
        return value == placement.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    // Optional: toString method for debugging purposes
    @Override
    public String toString() {
        return "Placement{" +
                "value=" + value +
                '}';
    }
}
