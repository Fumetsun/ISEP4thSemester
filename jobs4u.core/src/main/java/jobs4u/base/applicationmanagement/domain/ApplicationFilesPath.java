package jobs4u.base.applicationmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.util.StringPredicates;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.ToString;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
public class ApplicationFilesPath implements ValueObject, Comparable<ApplicationFilesPath> {


    private String path;

    @Transient
    private final String directoryPattern = ".*[/\\\\]$";

    @Transient
    private final Pattern pattern = Pattern.compile(directoryPattern);
    @Transient
    private Matcher matcher;

    public ApplicationFilesPath(String path) {
        matcher = pattern.matcher(path);
        Preconditions.ensure(matcher.matches(), "[ERROR] - Not a valid directory.");
        this.path = path;
    }

    public ApplicationFilesPath() {

    }

    @Override
    public int compareTo(ApplicationFilesPath o) {
        return this.path.compareTo(o.path);
    }

    @Override
    public String toString() {
        return path;
    }
}
