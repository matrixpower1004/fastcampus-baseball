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

    private final TeamDao teamDao;

    public static TeamService teamService;

    private TeamService() {
        teamDao = new TeamDao();
    }

    public static TeamService getInstance() {
        if (teamService == null) {
            teamService = new TeamService();
        }
        return teamService;
    }

    @Override
    public void create() {
        Team team = new Team();
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
