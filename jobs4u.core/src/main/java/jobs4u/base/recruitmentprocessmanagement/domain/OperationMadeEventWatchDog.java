package jobs4u.base.recruitmentprocessmanagement.domain;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.infrastructure.pubsub.EventHandler;
import jobs4u.base.recruitmentprocessmanagement.application.OperationMadeWatchdogController;

public class OperationMadeEventWatchDog implements EventHandler {

    final OperationMadeWatchdogController controller = new OperationMadeWatchdogController();

    @Override
    public void onEvent(final DomainEvent domainevent) {
        assert domainevent instanceof OperationMadeEvent;

        final OperationMadeEvent event = (OperationMadeEvent) domainevent;

        controller.incrementOperationCounter(event.getRefCode());
    }
}
