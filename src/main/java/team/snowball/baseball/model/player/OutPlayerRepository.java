package team.snowball.baseball.model.player;

import team.snowball.baseball.dto.OutPlayerRespDto;

/**
 * author         : Jason Lee
 * date           : 2023-06-27
 * description    :
 */
public interface OutPlayerRepository {
    int insert(OutPlayer outPlayer);

    OutPlayerRespDto finalAll();
}
