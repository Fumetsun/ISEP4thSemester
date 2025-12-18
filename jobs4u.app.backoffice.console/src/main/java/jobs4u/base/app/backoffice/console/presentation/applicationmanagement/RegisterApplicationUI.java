package jobs4u.base.app.backoffice.console.presentation.applicationmanagement;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.applicationmanagement.application.RegisterJobApplicationController;
import jobs4u.base.applicationmanagement.domain.JobApplication;
import jobs4u.base.candidatemanagement.domain.dto.CandidateDto;
import jobs4u.base.joboffermanagement.domain.dto.JobOfferDto;

import java.util.ArrayList;
import java.util.List;

public class RegisterApplicationUI extends AbstractUI {
    RegisterJobApplicationController ctrl = new RegisterJobApplicationController();

    @Override
    protected boolean doShow() {
        List<Iterable> data = ctrl.getData();
        ArrayList<JobOfferDto> offers = (ArrayList<JobOfferDto>) data.get(0);

        if(offers.isEmpty() || offers.size() == 0){
            System.out.println("\n\nThere are no open Job Offers. Please try again later.\n");
        }

        ArrayList<CandidateDto> candidates = (ArrayList<CandidateDto>) data.get(1);
        System.out.println("\n\n-=-=-=-  Available Candidates  -=-=-=-");
        int i=0;
        for(CandidateDto c : candidates){
            i++;
            System.out.println(i + "   |Phone Number: " + c.getPhoneNumber() + "  |User Info: " + c.getUserInfo());
        }
        i=0;
        System.out.println("\n\n-=-=-=-  Job Offers  -=-=-=-");
        for(JobOfferDto j : offers){
            i++;
            System.out.println( (i+ "   #" + j.getRefCode() +"#"+ j.getTitle() + " - " + " |Job Title:  " + j.getTitle() + " |Job Mode:  " + j.getJobMode() + "|Vacancies:  " + j.getNumberVacancies()));
        }


        System.out.println("\n\n(If none of these are the desired candidate please type 0)");
        String candidateOption = Console.readLine("Select a Candidate: ");

        if(!candidateOption.equals("0")){
            candidateOption = candidates.get(Integer.parseInt(candidateOption)-1).getPhoneNumber();
        }else{
            ctrl.importCandidateInfo(Console.readLine("First Name:") + "_" + Console.readLine("Last Name"));
            doShow();
            return true;
        }
        String jobOption = Console.readLine("Select a JobOffer: ");

        jobOption = offers.get(Integer.parseInt(jobOption)-1).getRefCode();

        JobApplication app  = ctrl.createJobApplication(jobOption,candidateOption);

        System.out.println(app.toString());



        return true;
    }

    @Override
    public String headline() {
        return "Register a Job Application";
    }

}
