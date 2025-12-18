package jobs4u.base.recruitmentprocessmanagement.domain;

import eapli.framework.domain.events.DomainEventBase;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.joboffermanagement.domain.JobRefCode;

public class OperationMadeEvent extends DomainEventBase {

    private final JobRefCode refCode;

    public OperationMadeEvent(JobRefCode refCode){
        this.refCode=refCode;
    }

    public JobRefCode getRefCode() {
        return refCode;
    }
}
