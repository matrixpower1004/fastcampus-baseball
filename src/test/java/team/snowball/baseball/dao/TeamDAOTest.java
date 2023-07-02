package team.snowball.baseball.dao;

import org.junit.jupiter.api.Test;
import team.snowball.baseball.model.team.Team;
import team.snowball.baseball.model.team.TeamRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * author         : Yongwon Kim
 * date           : 2023-06-28
 * description    :
 */
class TeamDAOTest {
    private TeamRepository teamRepository = TeamDao.getInstance();

    @Test
    void save_test() {
        // Given
        Team team = Team.builder()
                .stadiumId(6L)
                .name("LG")
                .build();

        // When
        int result = teamRepository.save(team);

        // Then
        assertThat(result).isEqualTo(1);
    }

    @Test
    void findAllStadium_test() {
        // Given

        // When
        List<Team> teamList = teamRepository.findAll();

        // Then
        assertThat(teamList).isNotEmpty();
    }

    @Test
    void delete_test() {
        // Given
        Long id = 4L;

        // When
        int result = teamRepository.delete(id);

        // Then
        assertThat(result).isEqualTo(1);
    }

    @Test
    void update_test() {
        // Given
        Team team = Team.builder()
                .id(1L)
                .stadiumId(1L)
                .name("DI")
                .build();

        // When
        int result = teamRepository.update(team);

        // Then
        assertThat(result).isEqualTo(1);
    }

    @Test
    void idDuplicate_Id_test() {
        // Given
        Long stadiumId = 5L;

        // When
        int result = TeamDao.getInstance().idDupeStadiumId(stadiumId);

        // Then
        assertThat(result).isEqualTo(0);
    }
}