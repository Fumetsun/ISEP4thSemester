package jobs4u.base.candidatemanagement.application;
import jobs4u.base.applicationmanagement.JobApplicationManagementService;
import jobs4u.base.applicationmanagement.domain.WordAnalysis;
import jobs4u.base.applicationmanagement.domain.dto.ApplicationDTO;
import jobs4u.base.candidatemanagement.GetCandidateService;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.candidatemanagement.domain.PhoneNumber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DisplayCandidateController {

    private final GetCandidateService candidateService = new GetCandidateService();

    private final JobApplicationManagementService appService = new JobApplicationManagementService();

    private final WordAnalysis wordAnalysis = new WordAnalysis();
    public List<Candidate> displayCandidates(){
        List<Candidate> candidateList = new ArrayList<>();
        for(Candidate candidate : candidateService.getCandidates()){
            candidateList.add(candidate);
        }
        return candidateList;
    }

    public List<ApplicationDTO> getCandidateApplications(PhoneNumber candidateNumber){
        Candidate candidate = candidateService.getCandidateByPhoneNumber(candidateNumber).get();
        return appService.applicationsOfCandidate(candidate.associatedUser());
    }

    public List<WordAnalysis.WordInfo> getWordCount(Set<String> files) throws InterruptedException, IOException {
        return wordAnalysis.countWords(files);
    }
}
