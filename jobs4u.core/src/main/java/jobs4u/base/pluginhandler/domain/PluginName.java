package jobs4u.base.pluginhandler.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.regex.Pattern;

@Embeddable
public class PluginName implements ValueObject, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Pattern VALID_NAME_REGEX = Pattern.compile("(^[A-Z][a-zA-Z]*)( [A-Za-z]*)*");
    private final String name;


    public PluginName(String pluginName) {
        Preconditions.nonEmpty(pluginName, "Plugin Name should neither be null nor empty");
        Preconditions.matches(VALID_NAME_REGEX, pluginName, "Invalid Plugin Name: " + pluginName + "\nRestrictions: only letters are allowed and requires name to start with a capital letter.");
        this.name = pluginName;
    }

    public PluginName(){
        this.name="";
    }

    @Override
    public String toString() {
        return this.name;
    }

}
