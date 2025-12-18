package jobs4u.base.recruitmentprocessmanagement.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcess;

import java.util.Optional;

public interface RecruitmentProcessRepository extends DomainRepository<Integer, RecruitmentProcess> {

    default Optional<RecruitmentProcess> findByRef(final Integer ref) {
        return ofIdentity(ref);
    }

    public Iterable<RecruitmentProcess> findAllRecruitmentProcesses();

}
