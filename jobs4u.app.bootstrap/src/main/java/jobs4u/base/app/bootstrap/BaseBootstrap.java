/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package jobs4u.base.app.bootstrap;

import jobs4u.base.app.common.console.BaseApplication;
import jobs4u.base.clientusermanagement.application.eventhandlers.NewUserRegisteredFromSignupWatchDog;
import jobs4u.base.clientusermanagement.domain.events.NewUserRegisteredFromSignupEvent;
import jobs4u.base.clientusermanagement.domain.events.SignupAcceptedEvent;
import jobs4u.base.customermanagement.domain.CustomerUserCreatedEvent;
import jobs4u.base.customermanagement.domain.CustomerUserCreatedEventWatchDog;
import jobs4u.base.infrastructure.bootstrapers.BaseBootstrapper;
import jobs4u.base.infrastructure.bootstrapers.demo.BaseDemoBootstrapper;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.infrastructure.smoketests.BaseDemoSmokeTester;
import jobs4u.base.notificationmanagement.domain.CheckNewNotificationsEvent;
import jobs4u.base.notificationmanagement.domain.CheckNewNotificationsEventWatchdog;
import jobs4u.base.notificationmanagement.domain.JobApplicationNotificationEvent;
import jobs4u.base.notificationmanagement.domain.JobApplicationNotificationEventWatchdog;
import jobs4u.base.rankingmanagement.domain.RankingEvent;
import jobs4u.base.rankingmanagement.domain.RankingEventWatchdog;
import jobs4u.base.recruitmentprocessmanagement.domain.OperationMadeEvent;
import jobs4u.base.recruitmentprocessmanagement.domain.OperationMadeEventWatchDog;
import jobs4u.base.usermanagement.application.eventhandlers.SignupAcceptedWatchDog;
import jobs4u.base.usermanagement.domain.BasePasswordPolicy;
import eapli.framework.collections.util.ArrayPredicates;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.pubsub.EventDispatcher;

/**
 * Base Bootstrapping data app
 */
@SuppressWarnings("squid:S106")
public final class BaseBootstrap extends BaseApplication {
    /**
     * avoid instantiation of this class.
     */
    private BaseBootstrap() {
    }

    private boolean isToBootstrapDemoData;
    private boolean isToRunSampleE2E;

    public static void main(final String[] args) {

        AuthzRegistry.configure(PersistenceContext.repositories().users(), new BasePasswordPolicy(),
                new PlainTextEncoder());

        new BaseBootstrap().run(args);
    }

    @Override
    protected void doMain(String[] args) {
        args = new String[]{"-bootstrap:demo"};

        handleArgs(args);

        System.out.println("\n\n------- MASTER DATA -------");
        new BaseBootstrapper().execute();

        if (isToBootstrapDemoData) {
            System.out.println("\n\n------- DEMO DATA -------");
            new BaseDemoBootstrapper().execute();
        }
        if (isToRunSampleE2E) {
            System.out.println("\n\n------- BASIC SCENARIO -------");
            new BaseDemoSmokeTester().execute();
        }
    }

    private void handleArgs(final String[] args) {
        isToRunSampleE2E = ArrayPredicates.contains(args, "-smoke:basic");
        if (isToRunSampleE2E) {
            isToBootstrapDemoData = true;
        } else {
            isToBootstrapDemoData = ArrayPredicates.contains(args, "-bootstrap:demo");
        }
    }

    @Override
    protected String appTitle() {
        return "Bootstrapping Base data ";
    }

    @Override
    protected String appGoodbye() {
        return "Bootstrap data done.";
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doSetupEventHandlers(final EventDispatcher dispatcher) {
        dispatcher.subscribe(new CustomerUserCreatedEventWatchDog(), CustomerUserCreatedEvent.class);
        dispatcher.subscribe(new JobApplicationNotificationEventWatchdog(), JobApplicationNotificationEvent.class);
        dispatcher.subscribe(new CheckNewNotificationsEventWatchdog(), CheckNewNotificationsEvent.class);
        dispatcher.subscribe(new OperationMadeEventWatchDog(), OperationMadeEvent.class);
        dispatcher.subscribe(new RankingEventWatchdog(), RankingEvent.class);
    }
}
