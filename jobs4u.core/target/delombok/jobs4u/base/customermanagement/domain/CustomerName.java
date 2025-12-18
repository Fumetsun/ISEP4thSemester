package jobs4u.base.customermanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.regex.Pattern;

@Embeddable
public class CustomerName implements ValueObject, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Pattern VALID_NAME_REGEX = Pattern.compile("(^[A-Z][a-zA-Z]*)( [A-Za-z]*)*");
    private final String name;


    public CustomerName(String customerName) {
        Preconditions.nonEmpty(customerName, "Customer Name should neither be null nor empty");
        Preconditions.matches(VALID_NAME_REGEX, customerName, "Invalid Customer Name: " + customerName + "\nRestrictions: only letters are allowed and requires name to start with a capital letter.");
        this.name = customerName;
    }

    public CustomerName(){
        this.name="";
    }

    @Override
    public String toString() {
        return this.name;
    }

}
