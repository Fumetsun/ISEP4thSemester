package jobs4u.base.pluginhandler.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.representations.dto.DTOable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Version;

import java.io.File;

@Entity
public class RegisteredPlugin implements AggregateRoot<PluginFilePath>, DTOable<RegisteredPluginDTO> {

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    @EmbeddedId
    private PluginFilePath fileName;

    private PluginName pluginName;

    @Enumerated
    private PluginType pluginType;

    private static final String folderPath = "symbolTables";


    public RegisteredPlugin(PluginFilePath fileName, PluginName pluginName, PluginType pluginType) {
        this.fileName = fileName;
        this.pluginName = pluginName;
        this.pluginType = pluginType;
    }

    public RegisteredPlugin() {
    }

    public PluginFilePath fileName() {
        return this.fileName;
    }

    public PluginName pluginName() {
        return this.pluginName;
    }

    public PluginType pluginType() {
        return this.pluginType;
    }

    public static String folderPath() {
        return folderPath;
    }

    @Override
    public RegisteredPluginDTO toDTO(){
        return new RegisteredPluginDTO(this.fileName.toString(),this.pluginName.toString(),this.pluginType.toString(),folderPath);
    }

    @Override

    public boolean sameAs(Object other) {
        if (!(other instanceof RegisteredPlugin)) {
            return false;
        }
        RegisteredPlugin that = (RegisteredPlugin) other;
        if (this == that) {
            return true;
        } else
            return this.fileName.equals(that.fileName) &&
                    this.pluginName.equals(that.pluginName) &&
                    this.pluginType.equals(that.pluginType);
    }

    @Override
    public PluginFilePath identity() {
        return this.fileName;
    }

    @Override
    public String toString() {
        return "Plugin File Path: " + this.fileName.toString() + "\nPlugin Name: " + this.pluginName.toString() +"\nPlugin Type: " + this.pluginType.toString();
    }
}
