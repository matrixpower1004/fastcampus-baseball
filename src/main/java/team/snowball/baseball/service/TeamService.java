package team.snowball.baseball.service;


import team.snowball.baseball.dao.TeamDao;
import team.snowball.baseball.model.team.Team;

import java.util.List;

import static team.snowball.baseball.view.Report.showTeamList;

/**
 * author         : Yongwon Kim
 * date           : 2023-06-28
 * description    :
 */
public class TeamService{

    private static final TeamDao teamDao = TeamDao.getInstance();

    public static TeamService teamService;

    private TeamService() {
    }

    public static TeamService getInstance() {
        if (teamService == null) {
            teamService = new TeamService();
        }
        return teamService;
    }

    public void save(Team team) {
        teamDao.insert(team);
    }

    public void read() {
        List<Team> teamList = teamDao.findAllTeams();
        showTeamList(teamList);
    }

    public void read(Long id) {
        // 요구 사항에 없는 기능
    }

    public void update(Team team) {
        // 요구 사항에 없는 기능
        teamDao.update(team);
    }

    public void delete(Long id) {
        // 요구 사항에 없는 기능
        teamDao.delete(id);
    }

}
