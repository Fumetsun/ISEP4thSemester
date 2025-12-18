package jobs4u.base.pluginhandler.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import jobs4u.base.pluginhandler.domain.PluginFilePath;
import jobs4u.base.pluginhandler.domain.RegisteredPlugin;

import java.util.Optional;

public interface RegisteredPluginsRepository extends DomainRepository<PluginFilePath, RegisteredPlugin> {
    Optional<RegisteredPlugin> findByRef(PluginFilePath name);

    Iterable<RegisteredPlugin> findAllPlugins();

    Iterable<RegisteredPlugin> findAllInterviews();

    Iterable<RegisteredPlugin> findAllJobRequirements();

    Optional<RegisteredPlugin> getPluginByName(String fileName);
}
