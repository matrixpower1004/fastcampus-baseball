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
    void inset_test() {
        // Given
        Team team = Team.builder()
                .stadiumId(6)
                .name("LG")
                .build();

        // When
        int result = teamRepository.insert(team);

        // Then
        assertThat(result).isEqualTo(1);
    }

    @Test
    void findAllStadium_test() {
        // Given

        // When
        List<Team> teamList = teamRepository.findAllTeams();

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
                .stadiumId(1)
                .name("DI")
                .build();

        // When
        int result = teamRepository.update(team);

        // Then
        assertThat(result).isEqualTo(1);
    }

    @Test
    void nameDuplicate_test() {
        // Given
        Team team = Team.builder()
                .name("KA")
                .build();

        // When
        int result = TeamDao.getInstance().nameDuplicate(team);

        // Then
        assertThat(result).isEqualTo(0);
    }

    @Test
    void idDuplicate_test() {
        // Given
        Team team = Team.builder()
                .id(5L)
                .stadiumId(5)
                .build();

        // When
        int result = TeamDao.getInstance().idDuplicate(team);

        // Then
        assertThat(result).isEqualTo(0);
    }
}