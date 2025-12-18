package jobs4u.base.candidatemanagement.application;

import jobs4u.base.pluginhandler.ManagePluginService;
import jobs4u.base.pluginhandler.PluginHandlerService;
import jobs4u.base.pluginhandler.domain.RegisteredPluginDTO;

public class ExportJobRequirementsTemplateController {
    private ManagePluginService service = new ManagePluginService();

    private PluginHandlerService handler = new PluginHandlerService();

    public Iterable<RegisteredPluginDTO> getAllJobRequirementPlugins() {
        return service.allJobRequirements();
    }

    public void generateTemplate(String symbolTable, String outputPath) {
        handler.activateTemplateFunction(symbolTable, outputPath, 1 /*REQUIREMENTS*/);
    }

}
