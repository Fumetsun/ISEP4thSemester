package jobs4u.base.persistence.impl.inmemory;

import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import jobs4u.base.pluginhandler.domain.PluginFilePath;
import jobs4u.base.pluginhandler.domain.PluginType;
import jobs4u.base.pluginhandler.domain.RegisteredPlugin;
import jobs4u.base.pluginhandler.repositories.RegisteredPluginsRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class InMemoryRegisteredPluginsRepository extends InMemoryDomainRepository<RegisteredPlugin, PluginFilePath>
        implements RegisteredPluginsRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public Optional<RegisteredPlugin> findByRef(final PluginFilePath name) {
        return Optional.of(data().get(name));
    }

    @Override
    public Iterable<RegisteredPlugin> findAllPlugins(){
        return findAll();
    }

    @Override
    public Iterable<RegisteredPlugin> findAllInterviews() {
        Set<RegisteredPlugin> set = new HashSet<>();

        for(RegisteredPlugin p: findAllPlugins()){
            if(p.pluginType() == PluginType.INTERVIEW){
                set.add(p);
            }
        }
        return set;
    }

    @Override
    public Iterable<RegisteredPlugin> findAllJobRequirements() {
        Set<RegisteredPlugin> set = new HashSet<>();

        for(RegisteredPlugin p: findAllPlugins()){
            if(p.pluginType() == PluginType.JOBREQUIREMENTS){
                set.add(p);
            }
        }
        return set;
    }

    @Override
    public Optional<RegisteredPlugin> getPluginByName(String fileName) {
        return Optional.empty();
    }

}
