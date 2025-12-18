package jobs4u.base.app.backoffice.console.presentation.operator;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.candidatemanagement.application.ExportJobRequirementsTemplateController;
import jobs4u.base.pluginhandler.domain.RegisteredPluginDTO;

import java.util.ArrayList;
import java.util.List;

public class ExportJobRequirementsTemplateUI extends AbstractUI {

    private final ExportJobRequirementsTemplateController controller = new ExportJobRequirementsTemplateController();

    @Override
    protected boolean doShow() {
        List<RegisteredPluginDTO> plugins = (ArrayList) controller.getAllJobRequirementPlugins();
        int choice = -1, numberOfPlugins = 0;
        System.out.println("Please pick one of these plugins:");
        for (RegisteredPluginDTO p : plugins) {
            numberOfPlugins++;
            System.out.println(numberOfPlugins + ". -> " + p.toString());
        }
        if (numberOfPlugins == 0) {
            System.out.println("Currently, there are no plugins registered in the system.");
            return false;
        }
        do {
            choice = Console.readInteger("\nPlugin number:");
            if (choice < 0 || choice > numberOfPlugins) {
                System.out.println("Invalid choice. Try again.");
            }
        } while (choice < 0 || choice > numberOfPlugins);

        String outputFolder;

        outputFolder = Console.readLine("Please write the folder path you wish to save the template. Remember, it has to end with \\ or / (depends on system).");

        controller.generateTemplate(plugins.get(choice - 1).getFilePath(), outputFolder);

        return false;
    }

    @Override
    public String headline() {
        return "Export a Job Requirement Template";
    }
}
