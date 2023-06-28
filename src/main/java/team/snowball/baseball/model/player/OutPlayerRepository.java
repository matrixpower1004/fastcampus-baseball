package team.snowball.baseball.model.player;

import team.snowball.baseball.dto.OutPlayerRespDto;

import java.util.List;

/**
 * author         : Jason Lee
 * date           : 2023-06-27
 * description    :
 */
public interface OutPlayerRepository {
    int insert(OutPlayer outPlayer);

    List<OutPlayerRespDto> findAll();
}
