package jobs4u.base.customermanagement.domain;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import jobs4u.base.joboffermanagement.domain.JobOfferBuilder;
import jobs4u.base.joboffermanagement.domain.ContractType;
import jobs4u.base.joboffermanagement.domain.JobMode;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.joboffermanagement.domain.NumberOfVacancies;
import jobs4u.base.usermanagement.domain.BaseRoles;
import jobs4u.base.usermanagement.domain.UserBuilderHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JobOfferTest {

    /**
     * Verifies if the job offer is successfully built.
     */
    @Test
    void jobBuilderTest(){
        final CustomerFactory customerFactory = new CustomerFactory();
        final JobOfferBuilder jobOfferBuilder = new JobOfferBuilder();

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
        assertNotNull(job);
    }

    /**
     * Verifies if the job ref code is null, to be generated in DB.
     */
    @Test
    void jobReferenceIsNull(){
        final CustomerFactory customerFactory = new CustomerFactory();
        final JobOfferBuilder jobOfferBuilder = new JobOfferBuilder();

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
        assertEquals(job.identity().toString(), "null");
    }

    /**
     * Verifies if number of vacancies can't be negative numbers.
     */
    @Test
    void vacancyQuantityTest(){
        try{
            NumberOfVacancies num = new NumberOfVacancies(-2);
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
    }
}