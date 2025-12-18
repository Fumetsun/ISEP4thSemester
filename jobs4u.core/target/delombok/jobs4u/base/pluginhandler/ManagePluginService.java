package jobs4u.base.pluginhandler;

import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.pluginhandler.domain.RegisteredPlugin;
import jobs4u.base.pluginhandler.domain.RegisteredPluginDTO;
import jobs4u.base.pluginhandler.repositories.RegisteredPluginsRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ManagePluginService {

    private final RegisteredPluginsRepository repo = PersistenceContext.repositories().plugins();

    public boolean addPlugin(RegisteredPlugin plugin) {
        if(plugin == null){ return false; }
        repo.save(plugin);
        return true;
    }

    public Iterable<RegisteredPlugin> allPlugins() {
        return this.repo.findAll();
    }

    public Iterable<RegisteredPluginDTO> allPluginsDTO(){
        Iterable<RegisteredPlugin> plugins = allPlugins();
        List<RegisteredPluginDTO> ret = new ArrayList<>();
        for (RegisteredPlugin plugin : plugins) {
            ret.add(plugin.toDTO());
        }
        return ret;
    }

    public Iterable<RegisteredPluginDTO> allInterviewPlugins() {
        ArrayList<RegisteredPluginDTO> set = new ArrayList<>();
        Iterable<RegisteredPlugin> it = repo.findAllInterviews();

        for( RegisteredPlugin p : it){
            set.add(p.toDTO());
        }

        return set;
    }

    public Iterable<RegisteredPluginDTO> allJobRequirements() {
        ArrayList<RegisteredPluginDTO> set = new ArrayList<>();
        Iterable<RegisteredPlugin> it = repo.findAllJobRequirements();

        for( RegisteredPlugin p : it){
            set.add(p.toDTO());
        }

        return set;
    }
}
