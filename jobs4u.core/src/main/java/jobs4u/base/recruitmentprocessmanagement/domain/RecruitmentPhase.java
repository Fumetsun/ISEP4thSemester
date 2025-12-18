package jobs4u.base.recruitmentprocessmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.util.StringPredicates;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

import java.lang.annotation.Annotation;
import java.util.regex.Pattern;

@Embeddable
public class RecruitmentPhase implements ValueObject {

    private String name;
    private String description;
    private String phaseDatePeriod;
    private int numberOfOperations;

    private static final Pattern VALID_DATE_PERIOD_REGEX = Pattern.compile("^([1-2][0-9]|0[1-9]|3[01]|[1-9])[/](1[0-2]|0*[1-9])[/](20[0-9][0-9])[-]([1-2][0-9]|0*[1-9]|3[01])[/](1[0-2]|0*[1-9])[/](20[0-9][0-9])");

    public RecruitmentPhase() {
        this.name = "";
        this.description = "";
        this.phaseDatePeriod = "11/11/2000-12/12/2000";
        this.numberOfOperations = 0;
    }

    public RecruitmentPhase(String name, String description, String date) {
        if (StringPredicates.isNullOrEmpty(name)) {
            throw new IllegalArgumentException(
                    "The name should neither be null nor empty");
        }
        this.name = name;
        if (StringPredicates.isNullOrEmpty(description)) {
            throw new IllegalArgumentException(
                    "The description should neither be null nor empty");
        }
        this.description = description;

        Preconditions.nonEmpty(date, "Date Period should neither be null nor empty");
        Preconditions.matches(VALID_DATE_PERIOD_REGEX, date, "Invalid Date Name: " + date + "\nRestrictions: should follow this format (11/11/2000-12/12/2000)");

        this.phaseDatePeriod = date;

        this.numberOfOperations = 0;
    }

    public void incrementOperationCount(){
        this.numberOfOperations++;
    }

    @Override
    public String toString() {
        return "Phase '" + this.name + "':" +
                "\n\t-> Description: " + this.description +
                "\n\t-> Phase Date Period: " + this.phaseDatePeriod +
                "\n\t-> Number of Operations made: " + this.numberOfOperations;
    }

    public Class<? extends Annotation> annotationType() {
        return null;
    }

    public String name() {
        return this.name;
    }

    public String phaseDatePeriod(){ return this.phaseDatePeriod; }
    public int numberOfOperations(){
        return this.numberOfOperations;
    }

}

