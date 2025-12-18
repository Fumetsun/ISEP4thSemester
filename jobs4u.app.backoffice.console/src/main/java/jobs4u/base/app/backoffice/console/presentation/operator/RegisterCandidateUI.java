package jobs4u.base.app.backoffice.console.presentation.operator;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.candidatemanagement.application.RegisterCandidateController;

public class RegisterCandidateUI extends AbstractUI {
	private final RegisterCandidateController ctrl = new RegisterCandidateController();

	@Override
	protected boolean doShow() {
		final int mode = Console.readInteger("(1) Manual Insert\n(2) File import");
		String number = "999999999",
				email = "placeholder@email.com",
				firstName = "John",
				lastName = "Doe",
				file = "placeholder.txt";

		switch (mode) {
			case 1:
				number = Console.readLine("Phone number:");
				email = Console.readLine("Email:");
				firstName = Console.readLine("First Name:");
				lastName = Console.readLine("Last Name:");
				break;
			case 2:
				firstName = Console.readLine("First Name:");
				lastName = Console.readLine("Last Name:");
				file = firstName + "_" + lastName;
				break;
			default:
			 	System.out.printf("Option not supported, returning.\n");
				return false;
		}

		try {
			if (mode == 2)
				this.ctrl.importCandidateInfo(file);
			else
				this.ctrl.registerCandidate(number, email, firstName, lastName);
		} catch (Exception e) {
			System.out.printf("Unexpected error, %s\n%s", e.toString(), e.getStackTrace());
		}
		return false;
	}

	@Override
	public String headline() {
		return "Register a Candidate";
	}
}
