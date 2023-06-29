package team.snowball.baseball.model.team;

import java.util.List;

/**
 * author         : Yongwon Kim
 * date           : 2023-06-28
 * description    :
 */
public interface TeamRepository {

    int insert(Team team);

    List<Team> findAllTeams();

    int delete(Long id);

    int update(Team team);

}
