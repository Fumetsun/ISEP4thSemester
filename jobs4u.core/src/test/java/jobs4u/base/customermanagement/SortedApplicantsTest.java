package jobs4u.base.customermanagement;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import jobs4u.base.applicationmanagement.domain.*;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.candidatemanagement.domain.CandidateFactory;
import jobs4u.base.candidatemanagement.domain.PhoneNumber;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.customermanagement.domain.CustomerFactory;
import jobs4u.base.joboffermanagement.domain.JobOfferBuilder;
import jobs4u.base.joboffermanagement.domain.ContractType;
import jobs4u.base.joboffermanagement.domain.JobMode;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.usermanagement.domain.BaseRoles;
import jobs4u.base.usermanagement.domain.UserBuilderHelper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SortedApplicantsTest {

    /**
     * Checks if sorting is descending starting from highest grade.
     */
    @Test
    void sortedApplicantsTest() {
        final CustomerFactory customerFactory = new CustomerFactory();
        final JobOfferBuilder jobOfferBuilder = new JobOfferBuilder();
        final CandidateFactory candidateFactory = new CandidateFactory();

        SystemUserBuilder userBuilder = UserBuilderHelper.builder();
        userBuilder.withUsername("jobs4u@jobs4u.org").withPassword("jobs4U$$$").withName("Jobs", "FourU")
                .withEmail("jobs4u@jobs4u.org").withRoles(BaseRoles.CUSTOMER);

        SystemUser builderUser = userBuilder.build();
        Customer customer = customerFactory.createCustomer("JobsFouru", "JOBS4U",
                "Jobs4u avenue, 123", builderUser, builderUser);

        jobOfferBuilder.withCustomer(customer)
                .withJobTitle("Lifeguard")
                .withContractType(ContractType.PARTTIME)
                .withJobMode(JobMode.ONSITE)
                .withVacancies(3)
                .withDescription("Come save lives 4 us")
                .withAddress("Jobs4u beach, 123");

        JobOffer job = jobOfferBuilder.build();

        userBuilder.withUsername("candidate1@jobs4u.org").withPassword("jobs4U$$$").withName("candidate", "one")
                .withEmail("candidate1@jobs4u.org").withRoles(BaseRoles.CANDIDATE);
        builderUser = userBuilder.build();

        List<JobApplication> sortedApps = new ArrayList<>();

        Candidate candidate1 = candidateFactory.newCandidate(builderUser, new PhoneNumber("929929929"));

        userBuilder.withUsername("candidate2@jobs4u.org").withPassword("jobs4U$$$").withName("candidate", "two")
                .withEmail("candidate2@jobs4u.org").withRoles(BaseRoles.CANDIDATE);
        builderUser = userBuilder.build();

        Candidate candidate2 = candidateFactory.newCandidate(builderUser, new PhoneNumber("923456789"));

        ApplicationBuilder builderApplication = new ApplicationBuilder();

        builderApplication.withJobOffer(job).withEmail(new ApplicationEmail("ola@email.org"))
                .withApplicationState(ApplicationState.GRADED).withInterviewGrade(new InterviewGrade(90))
                .withCandidate(candidate1).withFilesPath(new ApplicationFilesPath("processedFiles/candidate_one/"))
                .withAttachedFile(new ApplicationAttachedFile("./something.txt"));

        sortedApps.add(builderApplication.build());

        builderApplication.withJobOffer(job).withEmail(new ApplicationEmail("ol2a@email.org"))
                .withApplicationState(ApplicationState.GRADED).withInterviewGrade(new InterviewGrade(95))
                .withCandidate(candidate2).withFilesPath(new ApplicationFilesPath("processedFiles/candidate_two/"))
                .withAttachedFile(new ApplicationAttachedFile("./nothing.txt"));

        sortedApps.add(builderApplication.build());

        sortedApps.sort(Comparator.comparingInt(JobApplication::getGrade).reversed());
        assertEquals(95, sortedApps.get(0).getGrade());
    }
}