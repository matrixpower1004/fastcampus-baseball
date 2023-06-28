package team.snowball.baseball.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import team.snowball.baseball.dao.StadiumDao;
import team.snowball.baseball.model.Stadium;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StadiumServiceTest {

    private StadiumService stadiumService;

    @BeforeEach
    public void setUp(){
        StadiumDao stadiumDao = new StadiumDao();
        stadiumService = new StadiumService(stadiumDao);
    }

    @Test
    public void CreateStadium() {
        // Given
        String stadiumName = "임시야구장1";

        // When
        stadiumService.createStadium(stadiumName);

        // Then
        List<Stadium> stadiums = stadiumService.getAllStadiums();
        boolean isStadiumCreated = false;
        for (Stadium stadium : stadiums) {
            if (stadium.getName().equals(stadiumName)) {
                isStadiumCreated = true;
                break;
            }
        }
        assertTrue(isStadiumCreated);
    }

    @Test
    public void getAllStadiums() {
        // Given

        // When
        List<Stadium> stadiums = stadiumService.getAllStadiums();

        // Then
        assertThat(stadiums).isNotEmpty();
    }
}