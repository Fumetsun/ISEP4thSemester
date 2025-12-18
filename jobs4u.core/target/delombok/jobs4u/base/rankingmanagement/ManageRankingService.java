package jobs4u.base.rankingmanagement;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jakarta.persistence.TypedQuery;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import jobs4u.base.joboffermanagement.domain.JobRefCode;
import jobs4u.base.joboffermanagement.domain.dto.JobOfferDto;
import jobs4u.base.joboffermanagement.repositories.JobOfferRepository;
import jobs4u.base.rankingmanagement.domain.Placement;
import jobs4u.base.rankingmanagement.domain.Ranking;
import jobs4u.base.rankingmanagement.domain.RankingDTO;
import jobs4u.base.rankingmanagement.domain.RankingFactory;
import jobs4u.base.rankingmanagement.repositories.RankingRepository;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.util.ArrayList;

public class ManageRankingService {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final JobOfferRepository jobOfferRepo= PersistenceContext.repositories().jobOffers();

    private final RankingRepository rankRepo = PersistenceContext.repositories().rankings();

    public Iterable<JobOfferDto> retrieveMyJobOffers(){
        SystemUser me = authz.loggedinUserWithPermissions(BaseRoles.CUSTOMER_MANAGER).get();

        Iterable<JobOffer> offersRaw = jobOfferRepo.findAllOffersOfManager(me);

        ArrayList<JobOfferDto> offers = new ArrayList<>();

        for(JobOffer offer : offersRaw){
            offers.add(offer.toDTO());
        }

        return offers;
    }


    public Iterable<RankingDTO> retriveJobOfferRankings(Integer option){
        JobOffer offer = jobOfferRepo.ofIdentity(new JobRefCode(option)).get();

        ArrayList<Ranking> ranksRaw = (ArrayList<Ranking>) rankRepo.getRankingsofJobOffer(offer);
        ArrayList<RankingDTO> ranks = new ArrayList<>();

        for(Ranking r : ranksRaw){
            ranks.add(r.toDTO());
        }


        return ranks;
    }

    public Iterable<RankingDTO> updateRankings(Iterable<RankingDTO> rankings){

        Ranking rank = null;
        for(RankingDTO rankingDTO : rankings){
            rank = (rankRepo.rankingOfApplicationJobOffer(new JobRefCode(rankingDTO.getJobRefCode()), rankingDTO.getAppRefCode()));
            rank = rank.updatePlacement(new Placement(rankingDTO.getPlacement()));
            rankRepo.save(rank);
        }


        ArrayList<Ranking> ranks= (ArrayList<Ranking>) rankRepo.getRankingsofJobOffer(rank.offer());

        ArrayList<RankingDTO> newRankings = new ArrayList<>();

        for(Ranking r : ranks){
            newRankings.add(r.toDTO());
        }

        return newRankings;
    }


}
