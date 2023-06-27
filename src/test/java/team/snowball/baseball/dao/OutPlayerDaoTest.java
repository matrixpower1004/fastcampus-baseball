package team.snowball.baseball.dao;

import org.junit.jupiter.api.Test;
import team.snowball.baseball.dto.OutPlayerRespDto;
import team.snowball.baseball.model.player.OutPlayer;
import team.snowball.baseball.model.player.OutPlayerRepository;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * author         : Jason Lee
 * date           : 2023-06-27
 * description    :
 */
class OutPlayerDaoTest {

    private OutPlayerRepository repository = new OutPlayerDao();

    @Test
    void inset_success_test() {
        // TODO: 플레이어 데이터를 삽입하고 id를 가져와서 테스트를 진행하는 코드로 변경해 보자.
        // Given
        OutPlayer outPlayer = OutPlayer.builder()
                .playerId(12L)
                .reason("은퇴")
                .build();

        // When
        int result = repository.insert(outPlayer);

        // Then
        assertThat(result).isEqualTo(1);
    }

    @Test
    void find_all_test() {
        // Given

        // When
        OutPlayerRespDto outPlayerRespDto = repository.finalAll();

        // Then
        assertThat(outPlayerRespDto).isNotNull();
    }
}
