package jobs4u.base.applicationmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.util.StringPredicates;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;


@Embeddable
public class ApplicationEmail implements ValueObject, Comparable<ApplicationEmail> {

    private String email;



    public ApplicationEmail(String email) {
        if (StringPredicates.isNullOrEmpty(email)) {
            throw new IllegalArgumentException(
                    "The application email should not be empty or null");
        }

        this.email = email;
    }

    public ApplicationEmail() {

    }



    @Override
    public String toString() {
        return email;
    }

    @Override
    public int compareTo(ApplicationEmail o) {
        return this.email.compareTo(o.email);
    }
}
