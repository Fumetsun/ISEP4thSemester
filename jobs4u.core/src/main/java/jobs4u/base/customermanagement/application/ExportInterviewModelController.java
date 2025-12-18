package jobs4u.base.customermanagement.application;

import jobs4u.base.pluginhandler.ManagePluginService;
import jobs4u.base.pluginhandler.PluginHandlerService;
import jobs4u.base.pluginhandler.domain.RegisteredPluginDTO;

public class ExportInterviewModelController {
    private ManagePluginService service = new ManagePluginService();
    private PluginHandlerService handler = new PluginHandlerService();

    public Iterable<RegisteredPluginDTO> getAllInterviewPlugins() {
        return service.allInterviewPlugins();

    }

    public void generateTemplate(String symbolTable, String outputPath) {
        handler.activateTemplateFunction(symbolTable, outputPath, 2 /*INTERVIEW*/);
    }
}
