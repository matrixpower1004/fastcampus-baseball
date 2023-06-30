package team.snowball.baseball.service;


import team.snowball.baseball.dao.TeamDao;
import team.snowball.baseball.model.team.Team;
import team.snowball.baseball.view.Report;

import java.util.List;

/**
 * author         : Yongwon Kim
 * date           : 2023-06-28
 * description    :
 */
public class TeamService{

    private static final TeamDao teamDao = new TeamDao();

    public static TeamService teamService;

    private TeamService() {
    }

    public static TeamService getInstance() {
        if (teamService == null) {
            teamService = new TeamService();
        }
        return teamService;
    }

    public void create(Team team) {
        teamDao.insert(team);
    }

    public void read() {
        List<Team> teamList = teamDao.findAllTeams();
        Report.showTeam(teamList);
        //List<Team> teamList = teamDao.findAllTeams();
        //for (Team team : teamList) {
        //    System.out.println(team);
        //}
    }

    public void update(Team team) {
        teamDao.update(team);
    }

    public void delete(Long id) {
        teamDao.delete(id);
    }


}
