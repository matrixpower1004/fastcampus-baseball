package team.snowball.baseball.dao;

import org.junit.jupiter.api.Test;
import team.snowball.baseball.model.player.Player;
import team.snowball.baseball.model.player.PlayerRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * author         : Jason Lee
 * date           : 2023-06-27
 * description    :
 */
class PlayerDaoTest {

    private static final PlayerRepository REPOSITORY = PlayerDao.getInstance();

    @Test
    void inset_success_test() {
        // Given
        Player player = Player.builder()
                .teamId(2L)
                .name("박건우")
                .position("우익수")
                .build();

        // When
        int result = REPOSITORY.insert(player);

        // Then
        assertThat(result).isEqualTo(1);
    }

    @Test
    void findBy_teamId_test() {
        // Given
        Long teamId = 2L;

        // When
        List<Player> playerList = REPOSITORY.findByTeamId(teamId);

        // Then
        assertThat(playerList.size()).isNotNull();
        assertThat(playerList.get(0).getName()).isEqualTo("박민우");
    }

    @Test
    void delete_success_test() {
        // Given
        Long id = 35L;

        // When
        int result = REPOSITORY.delete(id);

        // Then
        assertThat(result).isEqualTo(1);
    }

    @Test
    void find_by_id_test() {
        // Given
        Long id = 1L;

        // When
        Player player = REPOSITORY.findById(id);

        // Then
        assertThat(player.getId()).isEqualTo(id);
    }

}