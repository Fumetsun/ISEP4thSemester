package jobs4u.base.app.backoffice.console.presentation.customermanager;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.joboffermanagement.application.UpdateJobRequirementController;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.pluginhandler.domain.RegisteredPluginDTO;

import java.util.ArrayList;

public class UpdateJobRequirementUI extends AbstractUI {
	private final UpdateJobRequirementController ctrl = new UpdateJobRequirementController();

	@Override
	protected boolean doShow() {
		JobOffer offer;
		RegisteredPluginDTO plugin;

		System.out.println("-=-=-= Job Offers =-=-=-");
		int i = 0;
		ArrayList<JobOffer> offers = (ArrayList<JobOffer>) ctrl.getOffers();
		for (JobOffer j : offers) {
			System.out.println("#" + (i + 1) + " | " + j.toString() + "\n");
			i++;
		}

		int option = Console.readInteger("Select a job offer:");

		while (option < 1 || option > i) {
			option = Console.readInteger("Select a job offer:");
		}
		offer = offers.get(option - 1);

		System.out.println("-=-=-= Available Plugins =-=-=-");

		ArrayList<RegisteredPluginDTO> plugins = (ArrayList<RegisteredPluginDTO>) ctrl.allJobRequirements();
		i = 1;
		for (RegisteredPluginDTO plug : plugins) {
			System.out.println("#" + i + " | " + plug.toString() + "\n");
			i++;
		}

		option = Console.readInteger("Select a plugin:");

		while (option < 1 || option > i) {
			option = Console.readInteger("Select a plugin:");
		}
		plugin = plugins.get(option - 1);

		System.out.println(ctrl.updateJobOffer(offer, plugin));
		return false;
	}

	@Override
	public String headline() {
		return ("-=-=-= Update Job Requirement =-=-=-");
	}
}
