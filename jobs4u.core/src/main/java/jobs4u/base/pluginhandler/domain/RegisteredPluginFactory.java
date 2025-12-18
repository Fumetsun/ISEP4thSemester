package jobs4u.base.pluginhandler.domain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class RegisteredPluginFactory {

    public RegisteredPlugin registerPlugin(String filePath, String name, int pluginType) {
        String newFilePath = copyFileToSymbolTablesFolder(filePath);

        String[] rootPath = newFilePath.split(RegisteredPlugin.folderPath());

        PluginFilePath pluginFilepATH = new PluginFilePath(RegisteredPlugin.folderPath()+"/"+rootPath[1].substring(1));
        PluginName pluginName = new PluginName(name);



        if (pluginType == 1) {
            return new RegisteredPlugin(pluginFilepATH, pluginName, PluginType.JOBREQUIREMENTS);
        } else {
            return new RegisteredPlugin(pluginFilepATH, pluginName, PluginType.INTERVIEW);
        }
    }


    private static String copyFileToSymbolTablesFolder(String filePath) {
        String currentDirectory = System.getProperty("user.dir"); //HOPEFULLY this works

        File sourceFile = new File(filePath);

        File symbolTablesFolder = new File(currentDirectory + File.separator + RegisteredPlugin.folderPath());

        if (!symbolTablesFolder.exists()) {
            symbolTablesFolder.mkdirs();
        }

        Path destinationPath = symbolTablesFolder.toPath().resolve(sourceFile.getName());

        try {
            Files.copy(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("\nFile copied successfully to " + destinationPath+"\n");
        } catch (IOException e) {
            System.err.println("\nError copying file: " + e.getMessage()+"\n");
        }

        return destinationPath.toString();
    }


}
