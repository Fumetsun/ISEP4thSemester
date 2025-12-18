package jobs4u.base.pluginhandler.application;

import jobs4u.base.pluginhandler.ManagePluginService;
import jobs4u.base.pluginhandler.PluginHandlerService;
import jobs4u.base.pluginhandler.domain.RegisteredPluginDTO;

public class EvaluateAnswersController {

    private ManagePluginService service = new ManagePluginService();

    private PluginHandlerService handler = new PluginHandlerService();

    public Iterable<RegisteredPluginDTO> getAllPlugins() {
        return service.allPluginsDTO();
    }

    public void generateEvaluation(String symbolTable, String inputFile, String pluginType){
        if (pluginType.equalsIgnoreCase("REQUIREMENTS")){
            int grade = handler.activateEvaluationFunction(symbolTable, inputFile, 1 /*REQUIREMENTS*/);
            if (grade == 0) {
                System.out.println("Requirements Verification: Candidate Passed.");
            } else {
                System.out.println("Requirements Verification: Candidate Failed.");
            }
        } else {
            handler.activateEvaluationFunction(symbolTable, inputFile, 2 /*INTERVIEW*/);
        }
    }

}
