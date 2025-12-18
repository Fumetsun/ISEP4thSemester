package jobs4u.base.app.backoffice.console.presentation.customermanager;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.customermanagement.application.RegisterCustomerController;

import java.util.concurrent.TimeUnit;

public class RegisterCustomerUI extends AbstractUI {

    private final RegisterCustomerController ctrl = new RegisterCustomerController();

    protected boolean doShow() {
        final String name = Console.readLine("Company Name:");
        final String customerCode = Console.readLine("Company Code:");
        final String customerAddress = Console.readLine("Company Address:");
        final String email = Console.readLine("Company Email:");

        try {
            SystemUser createdUser = this.ctrl.registerUser(name, customerCode, customerAddress, email);
            System.out.println("\n== User Created Successfully! ==\n");
        } catch (Exception e) {
            System.out.printf("[ERROR] " + e.getMessage());
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    @Override
    public String headline() {
        return "Register a Customer";
    }
}
