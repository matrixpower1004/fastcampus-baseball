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

    private PlayerRepository playerRepository = new PlayerDao();

    // TODO: 인메모리 DB가 아니라서 insert 후 rollback이 되지 않는다. 테스트를 위한 좋은 방법이 없을까?
    @Test
    void inset_success_test() {
        // Given
        Player player = Player.builder()
                .teamId(2)
                .name("박건우")
                .position("우익수")
                .build();

        // When
        int result = playerRepository.insert(player);

        // Then
        assertThat(result).isEqualTo(1);
    }

    @Test
    void findBy_teamId_test() {
        // Given
        int teamId = 2;

        // When
        List<Player> playerList = playerRepository.findByTeamId(teamId);

        // Then
        assertThat(playerList.size()).isNotNull();
        assertThat(playerList.get(0).getName()).isEqualTo("박민우");
    }

    @Test
    void delete_success_test() {
        // Given
        int id = 35;

        // When
        int result = playerRepository.delete(id);

        // Then
        assertThat(result).isEqualTo(1);
    }

}