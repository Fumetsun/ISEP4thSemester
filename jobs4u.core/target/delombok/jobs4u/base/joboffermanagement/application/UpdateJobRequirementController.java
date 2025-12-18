package jobs4u.base.joboffermanagement.application;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import jobs4u.base.customermanagement.domain.CustomerUserCreatedEvent;
import jobs4u.base.joboffermanagement.JobOfferManagementService;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.pluginhandler.ManagePluginService;
import jobs4u.base.pluginhandler.domain.RegisteredPluginDTO;
import jobs4u.base.recruitmentprocessmanagement.domain.OperationMadeEvent;

public class UpdateJobRequirementController {
	private final ManagePluginService plugSvc = new ManagePluginService();
	private final JobOfferManagementService jobSvc = new JobOfferManagementService();

	public Iterable<RegisteredPluginDTO> allJobRequirements() {
		return plugSvc.allJobRequirements();
	}

	private final EventPublisher dispatcher = InProcessPubSub.publisher();

	public Iterable<JobOffer> getOffers() {
		return jobSvc.getOffers();
	}

	public JobOffer updateJobOffer(JobOffer jobOffer, RegisteredPluginDTO plugin) {
		final DomainEvent event = new OperationMadeEvent(jobOffer.identity());
		dispatcher.publish(event);
		return jobSvc.updateJobOfferPlugins(jobOffer, plugin);
	}
}
