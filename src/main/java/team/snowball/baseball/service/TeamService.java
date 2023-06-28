package team.snowball.baseball.service;


import team.snowball.baseball.dao.TeamDao;
import team.snowball.baseball.model.team.Team;

import java.util.List;

/**
 * author         : Yongwon Kim
 * date           : 2023-06-28
 * description    :
 */
public class TeamService implements CommandService{

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

    @Override
    public void create(Team team) {
        teamDao.insert(team);
    }

    @Override
    public void read() {
        List<Team> teamList = teamDao.findAllTeams();
        for (Team team : teamList) {
            System.out.println(team);
        }
    }

    @Override
    public void update() {
        System.out.println("수정");
    }

    @Override
    public void delete() {
        System.out.println("삭제");
    }


}
