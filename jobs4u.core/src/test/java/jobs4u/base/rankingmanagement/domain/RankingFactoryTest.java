package jobs4u.base.rankingmanagement.domain;

import jobs4u.base.applicationmanagement.domain.JobApplication;
import jobs4u.base.joboffermanagement.domain.JobOffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RankingFactoryTest {
    private RankingFactory rankingFactory;
    private JobApplication validApplication;
    private JobOffer validJobOffer;

    @BeforeEach
    void setUp() {
        rankingFactory = new RankingFactory();
        validApplication = new JobApplication(); // Assuming a valid JobApplication constructor or a mock object
        validJobOffer = new JobOffer(); // Assuming a valid JobOffer constructor or a mock object
    }

    @Test
    void ensureRankgWithValidParameters() {
        int placementValue = 1;
        Ranking ranking = rankingFactory.createRanking(placementValue, validApplication, validJobOffer);

        assertNotNull(ranking);
        assertEquals(placementValue, ranking.placement().value());
        assertEquals(validApplication, ranking.application());
        assertEquals(validJobOffer, ranking.offer());
    }

    @Test
    void ensureInvalidPlacementValue() {
        int invalidPlacementValue = -1;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            rankingFactory.createRanking(invalidPlacementValue, validApplication, validJobOffer);
        });

        assertEquals("Placement value must be positive.", exception.getMessage());
    }

    @Test
    void ensureRankingWithNullApplication() {
        int placementValue = 1;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            rankingFactory.createRanking(placementValue, null, validJobOffer);
        });

        assertEquals("Job application cannot be null.", exception.getMessage());
    }

    @Test
    void ensureCreateRankingWithNullJobOffer() {
        int placementValue = 1;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            rankingFactory.createRanking(placementValue, validApplication, null);
        });

        assertEquals("Job offer cannot be null.", exception.getMessage());
    }

    @Test
    void ensurePlacementWithValidParameters() {
        int initialPlacementValue = 1;
        Ranking ranking = rankingFactory.createRanking(initialPlacementValue, validApplication, validJobOffer);
        int newPlacementValue = 2;

        Ranking updatedRanking = rankingFactory.updatePlacement(ranking, newPlacementValue);

        assertNotNull(updatedRanking);
        assertEquals(newPlacementValue, updatedRanking.placement().value());
    }


}