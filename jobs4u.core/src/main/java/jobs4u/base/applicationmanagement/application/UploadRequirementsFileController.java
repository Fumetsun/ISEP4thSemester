package jobs4u.base.applicationmanagement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
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

import java.io.IOException;
import java.util.Optional;

public class UploadRequirementsFileController {

    private final AuthorizationService autService = AuthzRegistry.authorizationService();
    private final ListJobOfferService offerService = new ListJobOfferService();
    private final PluginHandlerService pluginHandlerService = new PluginHandlerService();
    private final ListJobApplicationService listApplicationService = new ListJobApplicationService();
    private final JobApplicationManagementService applicationService = new JobApplicationManagementService();
    private final ImportFileService fileService = new ImportFileService();

    public Iterable<JobOfferDto> getJobOffers() {
        autService.ensureAuthenticatedUserHasAnyOf(BaseRoles.OPERATOR);
        return offerService.getOffers();
    }

    public boolean checkRequirements(JobOfferDto offerdto) {
        autService.ensureAuthenticatedUserHasAnyOf(BaseRoles.OPERATOR);
        Optional<RegisteredPlugin> plugin = offerService.getJobRequirementsOfJobOffer(new JobRefCode(Integer.parseInt(offerdto.getRefCode())));
        if (plugin.isEmpty())
            return false;
        return true;
    }

    public boolean validateResponses(String filePath) throws IllegalArgumentException {
        autService.ensureAuthenticatedUserHasAnyOf(BaseRoles.OPERATOR);
        return pluginHandlerService.activateCheckerFunction(filePath);
    }

    public Iterable<ApplicationDTO> getJobApplicationsOfJobOffer(JobOfferDto offerdto) {
        autService.ensureAuthenticatedUserHasAnyOf(BaseRoles.OPERATOR);
        return listApplicationService.getJobApplicationsOfJobOfferCode(new JobRefCode(Integer.parseInt(offerdto.getRefCode())));
    }

    public void saveResponses(ApplicationDTO appdto, String filePath) throws IOException {
        autService.ensureAuthenticatedUserHasAnyOf(BaseRoles.OPERATOR);
        JobApplication app = listApplicationService.getByRef(appdto.getRefCode()).orElse(null);
        String fileName;
        if (app == null)
            throw new IllegalArgumentException("Job Application not found.");
        try {
            fileName = fileService.importApplicantAnswers(filePath, app.getFilePath(), PluginType.JOBREQUIREMENTS);
        } catch (IOException e) {
            throw e;
        }
        applicationService.saveResponses(app, fileName);
    }

    public String checkIfApplicationHasRequirementsFile(ApplicationDTO app) {
        String[] appFiles = app.getAttachedFile().split("\n");

        for (int i = 0; i < appFiles.length; i++) {
            if (appFiles[i].contains("_JOBREQUIREMENTS")){
                return appFiles[i];
            }
        }
        return null;
    }

    public String deleteCurrentRequirementFile(ApplicationDTO applicationDTO, String file) {
        JobApplication app = listApplicationService.getByRef(applicationDTO.getJobRefCode()).orElse(null);
        if (app == null){
            throw new IllegalArgumentException("Job Application not found.");}
        return applicationService.removeFile(app, file);
    }
}
