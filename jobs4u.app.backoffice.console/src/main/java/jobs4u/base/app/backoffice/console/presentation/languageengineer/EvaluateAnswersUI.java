package jobs4u.base.app.backoffice.console.presentation.languageengineer;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.pluginhandler.application.EvaluateAnswersController;
import jobs4u.base.pluginhandler.domain.RegisteredPluginDTO;

import java.util.ArrayList;
import java.util.List;

public class EvaluateAnswersUI extends AbstractUI {

    private EvaluateAnswersController controller = new EvaluateAnswersController();

    @Override
    protected boolean doShow() {
        List<RegisteredPluginDTO> plugins = (ArrayList) controller.getAllPlugins();
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

        String answer;
        do {
            answer = Console.readLine("Please write your file path with the answers. Remember, the file has to be a .txt.");
            if (!answer.endsWith(".txt")){
                System.out.println("[Error] Doesn't end with .txt. Try again.");
            }
        } while (!answer.endsWith(".txt"));

        controller.generateEvaluation(plugins.get(choice - 1).getFilePath(), answer, plugins.get(choice - 1).getPluginType());

        return false;
    }

    @Override
    public String headline() {
        return "Evaluate Answers";
    }
}
