package jobs4u.base.applicationmanagement.application;

// import java.io.File;
import java.io.IOException;
import java.util.Optional;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.applicationmanagement.JobApplicationManagementService;
import jobs4u.base.applicationmanagement.ListJobApplicationService;
import jobs4u.base.applicationmanagement.domain.JobApplication;
import jobs4u.base.applicationmanagement.domain.dto.ApplicationDTO;
import jobs4u.base.common.ImportFileService;
import jobs4u.base.joboffermanagement.ListJobOfferService;
import jobs4u.base.joboffermanagement.domain.JobRefCode;
import jobs4u.base.joboffermanagement.domain.dto.JobOfferDto;
import jobs4u.base.pluginhandler.PluginHandlerService;
import jobs4u.base.pluginhandler.domain.PluginType;
import jobs4u.base.pluginhandler.domain.RegisteredPlugin;
import jobs4u.base.usermanagement.domain.BaseRoles;

public class SaveResponsesController {
	private final AuthorizationService autService = AuthzRegistry.authorizationService();
	private final ListJobOfferService offerService = new ListJobOfferService();
	private final PluginHandlerService pluginHandlerService = new PluginHandlerService();
	private final ListJobApplicationService listApplicationService = new ListJobApplicationService();
	private final JobApplicationManagementService applicationService = new JobApplicationManagementService();
	private final ImportFileService fileService = new ImportFileService();

	public Iterable<JobOfferDto> getJobOffers() {
		autService.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
		SystemUser manager = autService.loggedinUserWithPermissions(BaseRoles.CUSTOMER_MANAGER).get();
		return offerService.getJobOffersOfManager(manager);
	}

	public boolean checkInterview(JobOfferDto offerdto) {
		autService.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
		Optional<RegisteredPlugin> plugin = offerService
				.getInterviewModelOfJobOffer(new JobRefCode(Integer.parseInt(offerdto.getRefCode())));
		if (plugin.isEmpty())
			return false;
		return true;
	}

	public boolean validateResponses(String filePath) throws IllegalArgumentException {
		autService.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
		return pluginHandlerService.activateCheckerFunction(filePath);
	}

	public Iterable<ApplicationDTO> getJobApplicationsOfJobOffer(JobOfferDto offerdto) {
		autService.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
		return listApplicationService
				.getJobApplicationsOfJobOfferCode(new JobRefCode(Integer.parseInt(offerdto.getRefCode())));
	}

	public boolean getJobApplicationByDTO(ApplicationDTO appdto) {
		autService.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
		Optional<JobApplication> temp = listApplicationService.getByRef(appdto.getRefCode());
		if (temp.isEmpty())
			return false;
		return true;

	}

	public boolean checkAnswerAlreadyExists(ApplicationDTO appdto) {
		autService.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
		JobApplication app = listApplicationService.getByRef(appdto.getRefCode()).orElse(null);
		String[] fls = app.getFileRef().split("\n");
		for (String s : fls) {
			if (s.contains("_INTERVIEW"))
				return true;
		}
		return false;
	}

	public boolean deleteOldAnswers(ApplicationDTO appdto) {
		autService.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
		JobApplication app = listApplicationService.getByRef(appdto.getRefCode()).orElse(null);
		String file = "";
		String[] fls = app.getFileRef().split("\n");
		for (String s : fls) {
			if (s.contains("_INTERVIEW"))
				file = s;
		}
		if (file.isEmpty())
			return false;
		try {
			fileService.removeFile(app.getFilePath(), file);
		} catch (IOException e) {
			System.out.println("Error removing file.");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void saveResponses(ApplicationDTO appdto, String filePath) throws IOException {
		autService.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);

		JobApplication app = listApplicationService.getByRef(appdto.getRefCode()).orElse(null);

		String fileName = "";
		if (app == null)
			throw new IllegalArgumentException("Job Application not found.");
		try {
			fileName = fileService.importApplicantAnswers(filePath, app.getFilePath(), PluginType.INTERVIEW);
		} catch (IOException e) {
			throw e;
		}
		applicationService.saveResponses(app, fileName);
	}
}
