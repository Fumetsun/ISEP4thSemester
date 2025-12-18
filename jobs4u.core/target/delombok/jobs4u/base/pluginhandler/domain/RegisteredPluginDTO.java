package jobs4u.base.pluginhandler.domain;

public class RegisteredPluginDTO {


    private String fileName;

    private String pluginName;

    private String pluginType;

    private String folderPath;

    public RegisteredPluginDTO(String fileName, String pluginName, String pluginType, String folderPath){
        this.fileName=fileName;
        this.pluginName=pluginName;
        this.pluginType=pluginType;
        this.folderPath=folderPath;
    }

    @Override
    public String toString() {
        return "Plugin File Name: " + this.fileName + "\nPlugin Name: " + this.pluginName +"\nPlugin Type: " + this.pluginType + "\nPlugin Folder Path: "+this.folderPath;
    }

    public String getFilePath() {
        return fileName;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public String getPluginName() {
        return pluginName;
    }

    public String getPluginType() {
        return pluginType;
    }
}
