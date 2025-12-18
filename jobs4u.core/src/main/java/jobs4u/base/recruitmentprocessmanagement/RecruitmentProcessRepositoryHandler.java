package jobs4u.base.recruitmentprocessmanagement;

import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcess;
import jobs4u.base.recruitmentprocessmanagement.domain.dto.RecruitmentProcessDTO;
import jobs4u.base.recruitmentprocessmanagement.repositories.RecruitmentProcessRepository;

public class RecruitmentProcessRepositoryHandler {


    private final RecruitmentProcessRepository repo = PersistenceContext.repositories().processes();

    public RecruitmentProcessDTO findByRef(final Integer ref) {
        return repo.findByRef(ref).get().toDTO();
    }

    public Iterable<RecruitmentProcess> findAllRecruitmentProcesses(){
        return repo.findAllRecruitmentProcesses();
    };

}
