package jobs4u.base.pluginhandler.application;

import jobs4u.base.pluginhandler.ManagePluginService;
import jobs4u.base.pluginhandler.PluginHandlerService;
import jobs4u.base.pluginhandler.domain.RegisteredPluginDTO;

public class ExportTemplateController {

    private ManagePluginService service = new ManagePluginService();

    private PluginHandlerService handler = new PluginHandlerService();

    public Iterable<RegisteredPluginDTO> getAllPlugins() {
        return service.allPluginsDTO();
    }

    public void generateTemplate(String symbolTable, String outputPath, String pluginType){
        if (pluginType.equalsIgnoreCase("JOBREQUIREMENTS")){
            handler.activateTemplateFunction(symbolTable, outputPath, 1 /*REQUIREMENTS*/);
        } else {
            handler.activateTemplateFunction(symbolTable, outputPath, 2 /*INTERVIEW*/);
        }
    }

}
