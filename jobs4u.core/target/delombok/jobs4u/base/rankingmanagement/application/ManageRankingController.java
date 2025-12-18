package jobs4u.base.rankingmanagement.application;

import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.joboffermanagement.domain.dto.JobOfferDto;
import jobs4u.base.rankingmanagement.ManageRankingService;
import jobs4u.base.rankingmanagement.domain.Ranking;
import jobs4u.base.rankingmanagement.domain.RankingDTO;

public class ManageRankingController {

    ManageRankingService rankSvc = new ManageRankingService();

    public Iterable<JobOfferDto> retrieveMyJobOffers(){
        return rankSvc.retrieveMyJobOffers();
    }

    public Iterable<RankingDTO> retrieveJobOfferRanking(Integer option){
        return rankSvc.retriveJobOfferRankings(option);
    }

    public Iterable<RankingDTO> updateRankings(Iterable<RankingDTO> rankings){
        return rankSvc.updateRankings(rankings);
    }
}
