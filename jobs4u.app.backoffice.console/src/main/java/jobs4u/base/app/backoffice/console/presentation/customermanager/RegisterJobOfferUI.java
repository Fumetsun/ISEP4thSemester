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
package jobs4u.base.app.backoffice.console.presentation.customermanager;

import java.util.*;

import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.joboffermanagement.application.RegisterJobOfferController;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.joboffermanagement.domain.ContractType;
import jobs4u.base.joboffermanagement.domain.JobMode;
import jobs4u.base.pluginhandler.domain.PluginType;
import jobs4u.base.pluginhandler.domain.RegisteredPlugin;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentPhase;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcess;

/**
 * UI for adding a Job Offer to the application.
 *
 *
 */
public class RegisterJobOfferUI extends AbstractUI {

    private final RegisterJobOfferController theController = new RegisterJobOfferController();

    @Override
    protected boolean doShow() {
        List<RegisteredPlugin> pluginList = theController.getPluginList();
        if(!theController.getCustomerList().iterator().hasNext()){
            System.out.printf("There isn't a customer to associate a Job Offer to.");
            return false;
        }

        Integer vacancies = 0;
        int option = 0;

        System.out.println("Choose a customer:");
        final Customer customer = showCustomerList();
        final String title = Console.readLine("\nJob Title:");
        System.out.println("\nChoose a Contract Type:");
        final ContractType type = showContractTypes();
        System.out.println("\nChoose a Job Mode:");
        final JobMode mode = showJobModes();
        do {
            vacancies = Console.readInteger("\nNumber Of Vacancies:");
            if(vacancies <= 0) System.out.println("Vacancies should be a positive number.");
        }while(vacancies <= 0);
        final String description = Console.readLine("\nJob Description:");
        final String address = Console.readLine("\nAddress:");

        RecruitmentProcess recruitmentProcess = createTheRecruitmentTable();

        if(!pluginList.isEmpty()) {
            System.out.println("Create with plugins?\n1 - Yes\n2 - No");
            do {
                option = Console.readInteger("Option:");
            } while (option!=1 && option!=2);
        }
        if(option == 1) {
            try {
                System.out.println("\nChoose an interview model: ");
                RegisteredPlugin interviewModel = showInterviewModels(pluginList);
                System.out.println("\nChoose a requirement: ");
                RegisteredPlugin requirements = showRequirementsList(pluginList);
                this.theController.createJobOffer(customer, title, type, mode, vacancies, description, address, interviewModel, requirements, recruitmentProcess);
                System.out.println("Job Offer created successfully\n");
                System.out.println("Customer: " + customer.customerName() + "\nTitle: " + title + "\nContract Type: " + type + "\nJob Mode: " + mode +
                        "\nNumber of Vacancies: " + vacancies + "\nAddress: " + address + "\nInterview Model: " + interviewModel.pluginName() + "\nRequirements: " +
                        requirements.pluginName());
            } catch (Exception e) {
                System.out.printf("An unexpected error has occurred.");
            }
        }else {
            try {
                this.theController.createJobOfferWithoutPlugins(customer, title, type, mode, vacancies, description, address, recruitmentProcess);
                System.out.println("Job Offer created successfully\n");
                System.out.println("Customer: " + customer.customerName() + "\nTitle: " + title + "\nContract Type: " + type + "\nJob Mode: " + mode +
                        "\nNumber of Vacancies: " + vacancies + "\nAddress: " + address);
            } catch (Exception e) {
                System.out.printf("An unexpected error has occurred.");
            }
        }
        return false;
    }

    private RecruitmentProcess createTheRecruitmentTable() {
        int numberOfPhases;
        do {
            numberOfPhases = Console.readInteger("How many phases will your recruitmentProcess have? ");
            if (numberOfPhases<=0) {System.out.println("[Error] The number can't be 0 nor negative.");}
        } while (numberOfPhases<=0);

        ArrayList<RecruitmentPhase> recruitmentPhases = new ArrayList<>();

        for (int i = 0; i < numberOfPhases; i++) {
            String name = Console.readLine("Name of phase number "+(i+1)+"#: ");
            String description = Console.readLine("Description of phase number "+(i+1)+"#: ");
            String datePeriod = Console.readLine("Date Period of phase number "+(i+1)+"# (example: '11/11/2000-12/12/2000'):");
            System.out.println("=== / ===");
            recruitmentPhases.add(new RecruitmentPhase(name,description,datePeriod));
        }

        return theController.createRecruitmentProcess(recruitmentPhases, recruitmentPhases.get(0));
    }

    public Customer showCustomerList(){
        Iterable<Customer> customers = theController.getCustomerList();
        List<Customer> customerList = new ArrayList<>();
        Integer option = 0;
        boolean validOption = false;
        for (Customer customer : customers) {
            System.out.println(customerList.size() + 1 + " - " + customer.customerName() + " (" + customer.customerCode() + ")");
            customerList.add(customer);
        }

        do{
            option = Console.readInteger("Option:");
            if((option-1) >= customerList.size()) System.out.println("Invalid option");
            else validOption = true;
        }while(!validOption);

        return customerList.get(option - 1);

    }

    public RegisteredPlugin showInterviewModels(List<RegisteredPlugin> pluginList){
        int option = 1;
        boolean validOption = false;
        List<RegisteredPlugin> interviewModels = new ArrayList<>();
        RegisteredPlugin placeholder = new RegisteredPlugin();
        for (RegisteredPlugin plugin : pluginList) {
            if(plugin.pluginType().equals(PluginType.INTERVIEW) && !plugin.identity().toString().equals("PlaceholderInterview.jar")){
                System.out.println(option++ + " - " + plugin.pluginName() + " (" + plugin.fileName() + ")");
                interviewModels.add(plugin);
            }
            if(plugin.identity().toString().equals("PlaceholderInterview.jar")){
                placeholder = plugin;
            }
        }
        if(interviewModels.isEmpty()){
            System.out.println("There are no interview models.\n");
            return placeholder;
        }

        do{
            option = Console.readInteger("Option:");
            if((option - 1) >= interviewModels.size()) System.out.println("Invalid option");
            else validOption = true;
        }while(!validOption);

        return interviewModels.get(option - 1);
    }

    public RegisteredPlugin showRequirementsList(List<RegisteredPlugin> pluginList){
        int option = 1;
        boolean validOption = false;
        List<RegisteredPlugin> requirements = new ArrayList<>();
        RegisteredPlugin placeholder = new RegisteredPlugin();
        for (RegisteredPlugin plugin : pluginList) {
            if(plugin.pluginType().equals(PluginType.JOBREQUIREMENTS) && !plugin.identity().toString().equals("PlaceholderRequirement.jar")){
                System.out.println(option++ + " - " + plugin.pluginName() + " (" + plugin.fileName() + ")");
                requirements.add(plugin);
            }
            if(plugin.identity().toString().equals("PlaceholderRequirement.jar")){
                placeholder = plugin;
            }

        }

        if(requirements.isEmpty()){
            System.out.println("There are no requirement specifications.\n");
            return placeholder;
        }

        do{
            option = Console.readInteger("Option:");
            if((option - 1) >= requirements.size()) System.out.println("Invalid option");
            else validOption = true;
        }while(!validOption);

        return requirements.get(option - 1);
    }

    public ContractType showContractTypes(){
        int option = 0;
        boolean validOption = false;
        System.out.println("1 - Full-Time");
        System.out.println("2 - Part-Time");

        do{
            option = Console.readInteger("Option:");
            if(option == 1) return ContractType.FULLTIME;
            else if(option == 2) return ContractType.PARTTIME;
        }while(!validOption);

        return null;
    }

    public JobMode showJobModes(){
        int option = 0;
        boolean validOption = false;
        System.out.println("1 - Remote");
        System.out.println("2 - Hybrid");
        System.out.println("3 - Onsite");

        do{
            option = Console.readInteger("Option:");
            switch(option){
                case 1:
                    return JobMode.REMOTE;
                case 2:
                    return JobMode.HYBRID;
                case 3:
                    return JobMode.ONSITE;
            }
        }while(!validOption);

        return null;
    }
    @Override
    public String headline() {
        return "Register a Job Offer";
    }
}
