package jobs4u.base.app.backoffice.console.presentation.languageengineer;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.pluginhandler.application.RegisterPluginController;
import jobs4u.base.pluginhandler.domain.RegisteredPlugin;

public class RegisterPluginUI extends AbstractUI {

    private RegisterPluginController controller = new RegisterPluginController();

    protected boolean doShow() {
        final String symbolTablePath = Console.readLine("Symbol Table full path: ");
        final String pluginName = Console.readLine("Name of the plugin: ");
        int pluginType;
        do {
             pluginType = Console.readInteger("Type of the plugin:\n-> 1 - Job Requirements\n-> 2 - Interview Model\nAnswer:");
             if (pluginType!= 1 && pluginType!= 2){
                 System.out.println("[Error] Please choose one of the 2 numbers.");
             }
        } while (pluginType!=1 && pluginType !=2);

        try {
            RegisteredPlugin createdPlugin = controller.registerPlugin(symbolTablePath, pluginName, pluginType);
            System.out.println("=== Plugin Successfully Registered ===\n" + createdPlugin.toString());
        } catch (Exception e) {
            System.out.printf("[ERROR] " + e.getMessage());
        }

        return false;
    }

    @Override
    public String headline() {
        return "Register a new Plugin";
    }

}
