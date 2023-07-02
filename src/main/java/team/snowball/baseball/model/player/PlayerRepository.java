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

    List<Player> findByTeamId(Long id);

    int delete(Long id);

    PositionRespDto findLineByPosition();

    List<Player> findAll();

    Player findById(Long id);
}
