package jobs4u.base.joboffermanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.joboffermanagement.domain.JobRefCode;
import jobs4u.base.joboffermanagement.domain.dto.JobOfferDto;
import jobs4u.base.joboffermanagement.repositories.JobOfferRepository;
import jobs4u.base.pluginhandler.domain.RegisteredPlugin;

public class ListJobOfferService {
	private final JobOfferManagementService service = new JobOfferManagementService();
	private final JobOfferRepository repo = PersistenceContext.repositories().jobOffers();

	public Iterable<JobOffer> getJobOffersBySystemUser(SystemUser user){return repo.findAllOffersOfManager(user);}
	// TODO: mudar para usar querry (ver customer listing)
	public Iterable<JobOffer> getJobOffersByUser(Customer customer) {
		return repo.findAllOffersOfCustomer(customer);
	}

	public Iterable<JobOfferDto> getJobOffersOfManager(SystemUser manager) {
		Iterable<JobOffer> offers = repo.findAllOffersOfManager(manager);
		List<JobOfferDto> ret = new ArrayList<>();
		offers.forEach(o -> ret.add(o.toDTO()));
		return ret;
	}

	public Optional<RegisteredPlugin> getInterviewModelOfJobOffer(JobRefCode code) {
		JobOffer offer = repo.findByRef(code).orElse(null);
		if (offer == null || offer.getInterview() == null)
			return null;
		return Optional.of(offer.getInterview());
	}

	public Optional<JobOffer> getByRefCode(JobRefCode code) {
		return repo.findByRef(code);
	}

	public Iterable<JobOfferDto> getOffers() {
		Iterable<JobOffer> offers = repo.findAllOffers();
		List<JobOfferDto> ret = new ArrayList<>();
		offers.forEach(o -> ret.add(o.toDTO()));

		return ret;
	}

	public Optional<RegisteredPlugin> getJobRequirementsOfJobOffer(JobRefCode jobRefCode) {
		JobOffer offer = repo.findByRef(jobRefCode).orElse(null);
		if (offer == null || offer.requirements() == null)
			return Optional.empty();
		return Optional.of(offer.requirements());
	}
}
