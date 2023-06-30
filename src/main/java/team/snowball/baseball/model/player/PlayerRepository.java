package team.snowball.baseball.model.player;

import team.snowball.baseball.dto.PositionRespDto;

import java.util.List;

/**
 * author         : Jason Lee
 * date           : 2023-06-27
 * description    :
 */
public interface PlayerRepository {

    int insert(Player player);

    List<Player> findByTeamId(int id);

    int delete(Long id);

    void findLineByPosition();

}
