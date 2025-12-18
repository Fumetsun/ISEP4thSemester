package jobs4u.base.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import jakarta.persistence.TypedQuery;
import jobs4u.base.Application;
import jobs4u.base.pluginhandler.domain.PluginFilePath;
import jobs4u.base.pluginhandler.domain.PluginType;
import jobs4u.base.pluginhandler.domain.RegisteredPlugin;
import jobs4u.base.pluginhandler.repositories.RegisteredPluginsRepository;

import java.util.Optional;

public class JpaRegisteredPluginsRepository extends JpaAutoTxRepository<RegisteredPlugin, PluginFilePath, PluginFilePath> implements RegisteredPluginsRepository {

    public JpaRegisteredPluginsRepository(final TransactionalContext autoTx) {
        super(autoTx, "pluginFileName");
    }

    public JpaRegisteredPluginsRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "pluginFileName");
    }


    @Override
    public Optional<RegisteredPlugin> findByRef(final PluginFilePath name) {
        return ofIdentity(name);
    }

    @Override
    public Iterable<RegisteredPlugin> findAllPlugins() {
        return findAll();
    }

    @Override
    public Iterable<RegisteredPlugin> findAllInterviews() {
        final TypedQuery<RegisteredPlugin> q = createQuery(
                "SELECT e FROM RegisteredPlugin e WHERE e.pluginType = :ptype",
                RegisteredPlugin.class);
        q.setParameter("ptype", PluginType.INTERVIEW);

        return q.getResultList();
    }

    @Override
    public Iterable<RegisteredPlugin> findAllJobRequirements() {
        final TypedQuery<RegisteredPlugin> q = createQuery(
                "SELECT e FROM RegisteredPlugin e WHERE e.pluginType = :ptype",
                RegisteredPlugin.class);
        q.setParameter("ptype", PluginType.JOBREQUIREMENTS);

        return q.getResultList();
    }

    @Override
    public Optional<RegisteredPlugin> getPluginByName(String fileName) {
        final TypedQuery<RegisteredPlugin> q = createQuery(
                "SELECT e FROM RegisteredPlugin e WHERE e.fileName = :ptype",
                RegisteredPlugin.class);
        q.setParameter("ptype", new PluginFilePath(fileName));

        return Optional.ofNullable(q.getSingleResult());
    }


    /*@Override
    public Optional<ClientUser> findByUsername(final Username name) {
        final Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        return matchOne("e.systemUser.username=:name", params);
    }

    @Override
    public Optional<ClientUser> findByMecanographicNumber(final MecanographicNumber number) {
        final Map<String, Object> params = new HashMap<>();
        params.put("number", number);
        return matchOne("e.mecanographicNumber=:number", params);
    }

    @Override
    public Iterable<ClientUser> findAllActive() {
        return match("e.systemUser.active = true");
    }*/
}