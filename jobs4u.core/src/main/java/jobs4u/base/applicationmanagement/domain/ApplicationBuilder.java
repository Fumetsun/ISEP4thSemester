package jobs4u.base.applicationmanagement.domain;

import eapli.framework.domain.model.DomainFactory;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import lombok.Getter;

public class ApplicationBuilder implements DomainFactory<JobApplication> {

    private JobApplication theApp;
    //private ApplicationRefCode refCode;
    @Getter
    private ApplicationEmail email;
    @Getter
    private ApplicationFilesPath filesPath;
    @Getter
    private ApplicationAttachedFile attachedFile;
    private ApplicationState applicationState;
    private InterviewGrade interviewGrade;
    private JobOffer jobOffer;
    private Candidate candidate;

    public ApplicationBuilder() {

    }

    public ApplicationBuilder withEmail(ApplicationEmail email) {
        this.email = email;
        return this;
    }

    /*public ApplicationBuilder withRefCode(ApplicationRefCode refCode) {
        this.refCode = refCode;
        return this;
    }*/

    public ApplicationBuilder withFilesPath(ApplicationFilesPath filesPath) {
        this.filesPath = filesPath;
        return this;
    }

    public ApplicationBuilder withAttachedFile(ApplicationAttachedFile attachedFile) {
        this.attachedFile = attachedFile;
        return this;
    }

    public ApplicationBuilder withJobOffer(JobOffer jobOffer) {
        this.jobOffer = jobOffer;
        return this;
    }

    public ApplicationBuilder withCandidate(Candidate candidate) {
        this.candidate = candidate;
        return this;
    }

    public ApplicationBuilder withApplicationState(ApplicationState applicationState) {
        this.applicationState = applicationState;
        return this;
    }

    public ApplicationBuilder withInterviewGrade(InterviewGrade interviewGrade) {
        this.interviewGrade = interviewGrade;
        return this;
    }

    public JobApplication build() {
        final JobApplication ret = buildOrThrow();

        theApp = null;
        return ret;

    }

    private JobApplication buildOrThrow() {
        if(theApp != null){
            return theApp;
        }

        if(/*refCode*/email != null && filesPath != null && attachedFile != null &&
                jobOffer != null && candidate != null && applicationState != null && interviewGrade != null){
            theApp = new JobApplication(/*refCode,*/email,filesPath,attachedFile,applicationState,interviewGrade,jobOffer,candidate);
            return theApp;
        }else{
            throw new IllegalStateException("Application Builder Failed!");
        }
    }


}
