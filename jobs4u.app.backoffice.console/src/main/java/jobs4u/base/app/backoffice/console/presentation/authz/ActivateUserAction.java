package jobs4u.base.app.backoffice.console.presentation.authz;

import eapli.framework.actions.Action;

public class ActivateUserAction implements Action {
    public boolean execute() {
        return new ActivateUserUI().show();
    }
}
