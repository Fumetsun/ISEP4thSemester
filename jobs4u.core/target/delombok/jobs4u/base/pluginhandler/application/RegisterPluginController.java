package jobs4u.base.pluginhandler.application;

import jobs4u.base.pluginhandler.ManagePluginService;
import jobs4u.base.pluginhandler.domain.RegisteredPlugin;
import jobs4u.base.pluginhandler.domain.RegisteredPluginFactory;

public class RegisterPluginController {

    public ManagePluginService service = new ManagePluginService();

    public RegisteredPlugin registerPlugin(String filePath, String pluginName, int pluginType){
        RegisteredPluginFactory factory = new RegisteredPluginFactory();
        RegisteredPlugin createdPlugin = factory.registerPlugin(filePath,pluginName,pluginType);
        service.addPlugin(createdPlugin);
        return createdPlugin;
    }

}
