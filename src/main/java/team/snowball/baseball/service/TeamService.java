package team.snowball.baseball.service;


import team.snowball.baseball.dao.TeamDao;
import team.snowball.baseball.handler.InternalServerErrorException;
import team.snowball.baseball.model.team.Team;

import java.util.List;
import java.util.function.Consumer;

import static team.snowball.baseball.code.ConsoleMessage.MSG_SUCCESS_TO_REGISTER;
import static team.snowball.baseball.code.ErrorMessage.*;
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

    public void create(Team team) {
        if (team == null) {
            throw new InternalServerErrorException();
        }
        showResult.accept(teamDao.insert(team));
    }

    Consumer<Integer> showResult = (result) -> {
        String message;
        if (result == 1) {
            message = MSG_SUCCESS_TO_REGISTER.getMessage();
        } else if (result == 0) {
            message = ERR_MSG_OVERLAP_ID.getErrorMessage();
        } else if (result == -1){
            message = ERR_MSG_OVERLAP_NAME.getErrorMessage();
        } else {
            message = ERR_MSG_FAILED_TO_REGISTER.getErrorMessage();
        }
        System.out.println(message);
    };

    public void read() {
        List<Team> teamList = teamDao.findAllTeams();
        showTeamList(teamList);
    }

    public void update(Team team) {
        teamDao.update(team);
    }

    public void delete(Long id) {
        teamDao.delete(id);
    }

}
