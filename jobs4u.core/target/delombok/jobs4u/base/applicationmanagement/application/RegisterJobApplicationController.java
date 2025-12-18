package jobs4u.base.applicationmanagement.application;

import eapli.framework.io.util.Console;
import jobs4u.base.applicationmanagement.JobApplicationManagementService;
import jobs4u.base.applicationmanagement.domain.JobApplication;
import jobs4u.base.candidatemanagement.CandidateManagementService;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.candidatemanagement.domain.PhoneNumber;
import jobs4u.base.joboffermanagement.JobOfferManagementService;
import jobs4u.base.joboffermanagement.domain.*;

import java.util.List;
import java.util.Optional;

public class RegisterJobApplicationController {
    private final JobOfferManagementService jobOffsvc = new JobOfferManagementService();
    private final CandidateManagementService candidatesvc = new CandidateManagementService();
    private JobApplicationManagementService jobAppsvc = new JobApplicationManagementService();


    public JobApplication createJobApplication(String jobOption, String candidateOption){
        Optional<JobOffer> jobOffer = jobOffsvc.findByRef(new JobRefCode(Integer.parseInt(jobOption)));



        if(jobOffer.isPresent()){
            if(isInteger(candidateOption)){
                Optional<Candidate> candidate = Optional.of(candidatesvc.findByNumber(new PhoneNumber(candidateOption)));
                return jobAppsvc.addApplication(jobOffer.get(), candidate.get());
            }else{



                return jobAppsvc.addApplication(jobOffer.get(), candidateOption);
            }
        }else{
            throw new IllegalStateException("Job Offer does not exist");

        }


    }

    public List<Iterable> getData(){
        return jobAppsvc.getData();
    }


    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Candidate importCandidateInfo(String lastName) {
        return candidatesvc.importCandidateInfo(Console.readLine("First Name:") + "_" + Console.readLine("Last Name"));

    }
}
