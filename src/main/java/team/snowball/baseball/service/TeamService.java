package team.snowball.baseball.service;


import team.snowball.baseball.dao.TeamDao;
import team.snowball.baseball.handler.InternalServerErrorException;
import team.snowball.baseball.model.team.Team;

import java.util.List;

import static team.snowball.baseball.code.ConsoleMessage.MSG_SUCCESS_TO_REGISTER;
import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_FAILED_TO_REGISTER;

/**
 * author         : Yongwon Kim
 * date           : 2023-06-28
 * description    :
 */
public class TeamService {

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

    public String save(Team team) {
        if (team == null) {
            throw new InternalServerErrorException();
        }
        int result = teamDao.save(team);
        return result == 1 ? MSG_SUCCESS_TO_REGISTER.getMessage() :
                ERR_MSG_FAILED_TO_REGISTER.getErrorMessage();
    }

    public List<Team> findAll() {
        return teamDao.findAll();
    }

    public String findById(Long id) {
        // 요구 사항에 없는 기능
        return null;
    }

    public String update(Team team) {
        // 요구 사항에 없는 기능
        if (team == null) {
            throw new InternalServerErrorException();
        }
        int result = teamDao.update(team);
        return result == 1 ? MSG_SUCCESS_TO_REGISTER.getMessage() :
                ERR_MSG_FAILED_TO_REGISTER.getErrorMessage();
    }

    public String delete(Long id) {
        // 요구 사항에 없는 기능
        int result = teamDao.delete(id);
        return result == 1 ? MSG_SUCCESS_TO_REGISTER.getMessage() :
                ERR_MSG_FAILED_TO_REGISTER.getErrorMessage();
    }

}
