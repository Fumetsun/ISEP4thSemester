package jobs4u.base.pluginhandler.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import java.util.regex.Pattern;

public class PluginFilePath implements ValueObject, Comparable<PluginFilePath>{

    private static final long serialVersionUID = 1L;
    private static final Pattern VALID_NAME_REGEX = Pattern.compile("^([a-zA-z]|[0-9]|[_-]|:|/)+(.txt)$");
    private String fileName;

    public PluginFilePath(final String pluginFilePath) {
        Preconditions.nonEmpty(pluginFilePath, "File Path should neither be null nor empty");
        Preconditions.matches(VALID_NAME_REGEX, pluginFilePath, "Invalid File Path: " + pluginFilePath + "\nRestrictions: the filename has to end with a '.txt'. The available characters are: letters, numbers, '_', '-'. ");

        this.fileName = pluginFilePath;
    }

    public PluginFilePath() {

    }

    @Override
    public int compareTo(PluginFilePath o) {
        return this.fileName.compareTo(o.fileName);
    }

    @Override
    public String toString() {
        return this.fileName;
    }
}
