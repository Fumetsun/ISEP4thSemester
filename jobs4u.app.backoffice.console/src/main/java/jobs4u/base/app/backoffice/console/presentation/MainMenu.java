/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package jobs4u.base.app.backoffice.console.presentation;

import jobs4u.base.Application;
import jobs4u.base.app.backoffice.console.presentation.applicationmanagement.RegisterApplicationUI;
import jobs4u.base.app.backoffice.console.presentation.authz.*;
import jobs4u.base.app.backoffice.console.presentation.candidate.CandidateInboxUI;
import jobs4u.base.app.backoffice.console.presentation.candidate.ListOwnCandidateApplicationsUI;
import jobs4u.base.app.backoffice.console.presentation.customer.ListCustomerOffersUI;
import jobs4u.base.app.backoffice.console.presentation.customermanager.*;
import jobs4u.base.app.backoffice.console.presentation.languageengineer.EvaluateAnswersUI;
import jobs4u.base.app.backoffice.console.presentation.languageengineer.ExportTemplateUI;
import jobs4u.base.app.backoffice.console.presentation.languageengineer.RegisterPluginUI;
import jobs4u.base.app.backoffice.console.presentation.operator.ExportJobRequirementsTemplateUI;
import jobs4u.base.app.backoffice.console.presentation.operator.RegisterCandidateUI;
import jobs4u.base.app.backoffice.console.presentation.operator.UploadRequirementsFileUI;
import jobs4u.base.app.common.console.presentation.authz.MyUserMenu;
import jobs4u.base.usermanagement.domain.BaseRoles;
import eapli.framework.actions.Actions;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ExitWithMessageAction;
import eapli.framework.presentation.console.menu.HorizontalMenuRenderer;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;

/**
 * TODO split this class in more specialized classes for each menu
 *
 * @author Paulo Gandra Sousa
 */
public class MainMenu extends AbstractUI {

    private static final String RETURN_LABEL = "Return ";

    private static final int EXIT_OPTION = 0;

    // USERS
    private static final int ADD_USER_OPTION = 1;
    private static final int LIST_USERS_OPTION = 2;
    private static final int DEACTIVATE_USER_OPTION = 3;
    private static final int ACTIVATE_USER_OPTION = 4;

    // CUSTOMER MANAGER
    private static final int ADD_CUSTOMER = 1;
    private static final int ADD_JOB_OFFER_OPTION = 2;
    private static final int CHANGE_JOB_OFFER_PHASE = 3;
	private static final int LIST_APPLICATIONS = 4;
    private static final int EXPORT_INTERVIEW_TEMPLATE = 5;
    private static final int DISPLAY_CANDIDATE_INFORMATION = 6;
	private static final int UPDATE_JOB_REQUIREMENT = 7;
	private static final int SAVE_CANDIDATE_RESPONSES = 8;
    private static final int NOTIFY_CANDIDATES = 9;
    private static final int VALIDATE_APPLICATIONS=10;
	private static final int GRADE_INTERVIEWS = 11;
    private static final int RANK_CANDIDATES = 12;
    private static final int LIST_SORTED_APPLICANTS = 13;
    private static final int CLOSE_JOB_OFFER = 14;

	// OPERATOR
	private static final int ADD_CANDIDATE_OPTION = 1;
    private static final int ADD_APPLICATION_OPTION = 2;
    private static final int EXPORT_JOBREQUIREMENT_TEMPLATE = 3;
    private static final int IMPORT_JOBREQUIREMENT_APPLICATION=4;

    //LANGUAGE ENGINEER
    private static final int REGISTER_PLUGIN = 1;
    private static final int EXPORT_TEMPLATE = 2;
    private static final int EVALUATE_ANSWERS = 3;

    //CANDIDATE

    private static final int VIEW_INBOX = 1;
    private static final int LIST_OWN_APPLICATIONS = 2;

    //CUSTOMER

    private static final int LIST_CUSTOMER_OFFERS = 1;

    // SETTINGS
    private static final int SET_KITCHEN_ALERT_LIMIT_OPTION = 1;

    // MAIN MENU
    private static final int MY_USER_OPTION = 1;
    private static final int CUSTOMER_MANAGER_OPTIONS = 2;
    private static final int OPERATOR_OPTIONS = 3;
    private static final int LANGUAGE_ENGINEER_OPTIONS = 4;

    private static final int CANDIDATE_OPTIONS = 5;

    private static final int CUSTOMER_OPTIONS = 6;
    private static final int SETTINGS_OPTION = 9;

    private static final String SEPARATOR_LABEL = "--------------";

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    @Override
    public boolean show() {
        drawFormTitle();
        return doShow();
    }

    /**
     * @return true if the user selected the exit option
     */
    @Override
    public boolean doShow() {
        final Menu menu = buildMainMenu();
        final MenuRenderer renderer;
        if (Application.settings().isMenuLayoutHorizontal()) {
            renderer = new HorizontalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        } else {
            renderer = new VerticalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        }
        return renderer.render();
    }

    @Override
    public String headline() {

        return authz.session().map(s -> "Base [ @" + s.authenticatedUser().identity() + " ]")
                .orElse("Base [ ==Anonymous== ]");
    }

    private Menu buildMainMenu() {
        final Menu mainMenu = new Menu();

        final Menu myUserMenu = new MyUserMenu();

        if (!Application.settings().isMenuLayoutHorizontal()) {
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.POWER_USER)) {
            final Menu usersMenu = buildUsersMenu();
            final Menu settingsMenu = buildAdminSettingsMenu();
            mainMenu.addSubMenu(SETTINGS_OPTION, settingsMenu);
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.ADMIN)) {
            final Menu settingsMenu = buildAdminSettingsMenu();
            mainMenu.addSubMenu(SETTINGS_OPTION, settingsMenu);
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.CUSTOMER_MANAGER)) {
            //final Menu settingsMenu = buildCustomerManagerMenu(); <-- why was this even here
            mainMenu.addSubMenu(CUSTOMER_MANAGER_OPTIONS, buildCustomerManagerMenu());
            mainMenu.addSubMenu(MY_USER_OPTION, myUserMenu);
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.CANDIDATE)) {
            //final Menu settingsMenu = buildCustomerManagerMenu(); <-- why was this even here
            mainMenu.addSubMenu(CANDIDATE_OPTIONS, buildCandidateMenu());
            mainMenu.addSubMenu(MY_USER_OPTION, myUserMenu);
        }

		if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.OPERATOR)) {
			//final Menu settingsMenu = buildOperatorMenu();
			mainMenu.addSubMenu(OPERATOR_OPTIONS, buildOperatorMenu());
			mainMenu.addSubMenu(MY_USER_OPTION, myUserMenu);
		}

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.LANGUAGE_ENGINEER)) {
            mainMenu.addSubMenu(LANGUAGE_ENGINEER_OPTIONS, buildLanguageEngineerMenu());
            mainMenu.addSubMenu(MY_USER_OPTION, myUserMenu);
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.CUSTOMER)) {
            mainMenu.addSubMenu(CUSTOMER_OPTIONS, buildCustomerMenu());
            mainMenu.addSubMenu(MY_USER_OPTION, myUserMenu);
        }

        if (!Application.settings().isMenuLayoutHorizontal()) {
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
        }

        mainMenu.addItem(EXIT_OPTION, "Exit", new ExitWithMessageAction("Bye, Bye"));

        return mainMenu;
    }

    private Menu buildCandidateMenu() {
        final Menu menu = new Menu("Candidate Panel >");
        menu.addItem(VIEW_INBOX, "View Inbox", new CandidateInboxUI()::show);
        menu.addItem(LIST_OWN_APPLICATIONS, "List my Applications", new ListOwnCandidateApplicationsUI()::show);
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);


        return menu;
    }

    private Menu buildAdminSettingsMenu() {
        final Menu menu = new Menu("Admin Panel >");

        menu.addItem(ADD_USER_OPTION, "Add User", new RegisterUserUI()::show);
        menu.addItem(LIST_USERS_OPTION, "List all Users", new ListUsersAction());
        menu.addItem(DEACTIVATE_USER_OPTION, "Deactivate User", new DeactivateUserAction());
        menu.addItem(ACTIVATE_USER_OPTION, "Activate User", new ActivateUserAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildUsersMenu() {
        final Menu menu = new Menu("Users >");

        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildCustomerManagerMenu() {
        final Menu menu = new Menu("Customer Manager >");

        menu.addItem(ADD_CUSTOMER, "Register a Customer", new RegisterCustomerUI()::show);
        menu.addItem(ADD_JOB_OFFER_OPTION, "Register a Job Offer", new RegisterJobOfferUI()::show);
        menu.addItem(CHANGE_JOB_OFFER_PHASE, "Change a Job Offer's Phase", new ChangeJobOfferPhaseUI()::show);
		menu.addItem(LIST_APPLICATIONS, "List Applications for a Job offer", new ListApplicationsUI()::show);
        menu.addItem(EXPORT_INTERVIEW_TEMPLATE, "Export a Template to Collect Interview Answers", new ExportInterviewTemplateUI()::show);
        menu.addItem(DISPLAY_CANDIDATE_INFORMATION, "Display a candidate's information", new DisplayCandidateUI()::show);
        menu.addItem(UPDATE_JOB_REQUIREMENT, "Update a Job Offer's requirement specifications", new UpdateJobRequirementUI()::show);
        menu.addItem(SAVE_CANDIDATE_RESPONSES, "Save a candidate's responses to their application", new SaveResponsesUI()::show);
        menu.addItem(NOTIFY_CANDIDATES, "Notify a Candidate of their Job Application verification", new CandidateNotificationUI()::show);
		menu.addItem(VALIDATE_APPLICATIONS, "Validate JobOffer's Applications (JobRequirements)", new ValidateJobOfferApplicationsUI()::show);
        menu.addItem(GRADE_INTERVIEWS, "Grade interview answers", new GradeInterviewsUI()::show);
        menu.addItem(RANK_CANDIDATES, "Rank Candidates of a Job Offer", new ManageRankingUI()::show);
        menu.addItem(LIST_SORTED_APPLICANTS, "List applicants by grade from a job offer", new ListSortedApplicantsUI()::show);
        menu.addItem(CLOSE_JOB_OFFER, "Publish job offer results", new PublishSelectionResultsUI()::show);
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

	private Menu buildOperatorMenu() {
		final Menu menu = new Menu("Operator >");
		menu.addItem(ADD_CANDIDATE_OPTION, "Register a candidate", new RegisterCandidateUI()::show);
        menu.addItem(ADD_APPLICATION_OPTION, "Register a job application", new RegisterApplicationUI()::show);
        menu.addItem(EXPORT_JOBREQUIREMENT_TEMPLATE, "Export JobRequirement Template", new ExportJobRequirementsTemplateUI()::show);
        menu.addItem(IMPORT_JOBREQUIREMENT_APPLICATION, "Import JobRequirement of a Candidate", new UploadRequirementsFileUI()::show);
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
		return menu;
	}

    private Menu buildLanguageEngineerMenu() {
        final Menu menu = new Menu("Language Engineer >");
        menu.addItem(REGISTER_PLUGIN, "Register a plugin", new RegisterPluginUI()::show);
        menu.addItem(EXPORT_TEMPLATE, "Export a template", new ExportTemplateUI()::show);
        menu.addItem(EVALUATE_ANSWERS, "Evaluate answers", new EvaluateAnswersUI()::show);
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
        return menu;
    }

    private Menu buildCustomerMenu() {
        final Menu menu = new Menu("Customer >");
        menu.addItem(LIST_CUSTOMER_OFFERS, "Show job offer information", new ListCustomerOffersUI()::show);
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
        return menu;
    }
}
