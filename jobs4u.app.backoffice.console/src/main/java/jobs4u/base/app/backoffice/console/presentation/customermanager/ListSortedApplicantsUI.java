package jobs4u.base.app.backoffice.console.presentation.customermanager;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.applicationmanagement.application.ListSortedApplicantsController;
import jobs4u.base.applicationmanagement.domain.JobApplication;
import jobs4u.base.joboffermanagement.domain.JobOffer;

import java.util.List;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ListSortedApplicantsUI extends AbstractUI{

    public ListSortedApplicantsController theController = new ListSortedApplicantsController();

    public void showApplicantsSorted(List<JobApplication> jobApplications){
        System.out.printf("%n%-25s%-25s%-25s%-25s%n", "Ranking", "Name", "Email", "Grade");
        for(int i = 0; i < jobApplications.size(); i++) {
            System.out.printf("%-25s%-25s%-25s%-25s%n", "#"+(i + 1),
                    jobApplications.get(i).getCandidate().associatedUser().name(),
                    jobApplications.get(i).getCandidate().associatedUser().email(),
                    jobApplications.get(i).getGrade());
        }
    }

    public JobOffer showJobOffers(List<JobOffer> jobOffers){
        int option = 0;
        for(int i = 0; i < jobOffers.size(); i++){
            System.out.println(i+1 + "- " + jobOffers.get(i).customer().customerCode() + jobOffers.get(i).identity());
        }

        while(option <= 0){
            try{
                option = Console.readInteger("Option:");
            }catch(Exception e){
                System.out.println("Invalid argument!");
            }
        }
        return jobOffers.get(option-1);
    }

    @Override
    protected boolean doShow() {
        List<JobOffer> jobOffers = theController.findAllOffers();
        List<JobApplication> jobApplications = theController.getSortedApplicants(showJobOffers(jobOffers));
        if(!jobApplications.isEmpty()){
            showApplicantsSorted(jobApplications);
        }else{
            System.out.println("This offer does not have any applicants.");
        }
        return true;
    }

    @Override
    public String headline() {
        return "List applicants by order of grades";
    }
}
