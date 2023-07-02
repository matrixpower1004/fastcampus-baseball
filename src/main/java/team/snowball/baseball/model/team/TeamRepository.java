package team.snowball.baseball.model.team;

import java.util.List;

/**
 * author         : Yongwon Kim
 * date           : 2023-06-28
 * description    :
 */
public interface TeamRepository {

    int save(Team team);

    List<Team> findAll();

    int delete(Long id);

    int update(Team team);

}
