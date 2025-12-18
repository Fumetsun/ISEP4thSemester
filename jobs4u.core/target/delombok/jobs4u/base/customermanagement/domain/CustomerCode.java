package jobs4u.base.customermanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.util.StringPredicates;
import eapli.framework.validations.Preconditions;
import jobs4u.base.clientusermanagement.domain.MecanographicNumber;

import java.util.regex.Pattern;

public class CustomerCode implements ValueObject, Comparable<CustomerCode> {

    private static final long serialVersionUID = 1L;

    private static final Pattern VALID_NAME_REGEX = Pattern.compile("^([A-Z]|[0-9]){1,10}$");
    private String code;

    public CustomerCode(final String customerCode) {
        Preconditions.nonEmpty(customerCode, "Customer Code should neither be null nor empty");
        Preconditions.matches(VALID_NAME_REGEX, customerCode, "Invalid Customer Name: " + customerCode + "\nRestrictions: only numbers and capital letters are allowed. \nLimit: 10 characters");

        this.code = customerCode;
    }

    public CustomerCode() {

    }

    @Override
    public int compareTo(CustomerCode o) {
        return this.code.compareTo(o.code);
    }

    @Override
    public String toString() {
        return this.code;
    }
}
