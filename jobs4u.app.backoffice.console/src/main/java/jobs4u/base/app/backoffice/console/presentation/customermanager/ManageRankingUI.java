package jobs4u.base.app.backoffice.console.presentation.customermanager;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.joboffermanagement.domain.dto.JobOfferDto;
import jobs4u.base.rankingmanagement.application.ManageRankingController;
import jobs4u.base.rankingmanagement.domain.RankingDTO;

import java.util.ArrayList;

public class ManageRankingUI extends AbstractUI {

    ManageRankingController ctrl = new ManageRankingController();

    @Override
    protected boolean doShow() {
        ArrayList<JobOfferDto> offers = (ArrayList<JobOfferDto>)ctrl.retrieveMyJobOffers();
        int i=1;
        for(JobOfferDto offer: offers){
            System.out.println(i + "# " + offer.toString());
            i++;
        }
        Integer placement=0;
        Integer option = Console.readInteger("Select a job offer to edit the ranking of: ");

        ArrayList<RankingDTO> ranks = (ArrayList<RankingDTO>) ctrl.retrieveJobOfferRanking(Integer.parseInt(offers.get(option-1).getRefCode()));

        boolean flag = true;

        if(ranks != null){
            while(flag){

                i=1;
                for(RankingDTO rank: ranks){
                    System.out.println(i + "# " + rank.toString());
                    i++;
                }

                option = Console.readInteger("Select a ranking to edit the placement of (0 to finalize / -1 to cancel): ");
                if(option != 0 && option != -1){
                    placement = Console.readInteger("Select a new placement: ");
                }

                if(option == 0){

                    ctrl.updateRankings(ranks);
                    flag = false;
                }else if(option == -1){
                    System.out.println("Operation cancelled, returning to user panel.");
                    flag= false;
                }else{
                    RankingDTO rankDtoFirst= ranks.get(option-1);
                    rankDtoFirst.setPlacement(placement);

                    RankingDTO rankDtoSecond= ranks.get(placement-1);
                    rankDtoSecond.setPlacement(option);

                    ranks.set(placement-1, rankDtoFirst);
                    ranks.set(option-1, rankDtoSecond);
                }

            }
        }else{
            System.out.println("This Job Offer has no candidates.");
        }


        return true;
    }

    @Override
    public String headline() {
        return "Edit Rankings of JobOffers";
    }


}
