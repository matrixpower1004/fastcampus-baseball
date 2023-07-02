package team.snowball.baseball.controller;

import team.snowball.baseball.code.ParamList;
import team.snowball.baseball.dto.QueryDto;
import team.snowball.baseball.handler.InvalidInputException;
import team.snowball.baseball.model.team.Team;
import team.snowball.baseball.service.TeamService;
import team.snowball.baseball.view.TeamReport;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_INVALID_PARAMETER;

/**
 * author         : Jason Lee
 * date           : 2023-06-28
 * description    :
 */
public class TeamController implements ModelController {

    private static TeamController teamController;
    private static final TeamService teamService = TeamService.getInstance();

    private TeamController() {
    }

    public static TeamController getInstance() {
        if (teamController == null) {
            teamController = new TeamController();
        }
        return teamController;
    }

    @Override
    public void findAll() {
        List<Team> teamList = teamService.findAll();
        TeamReport.showTeamList(teamList);
    }

    public void findById(QueryDto queryDto) {
        if (isEmptyParams.test(queryDto)) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }
        Long id = getParamId.apply(queryDto);
        String result = teamService.findById(id);
        System.out.println(result);
    }

    public void save(QueryDto queryDto) {
        if (isEmptyParams.test(queryDto)) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }
        Team team = getTeamParams.apply(queryDto);
        teamService.save(team);
    }

    public void update(QueryDto queryDto) {
        if (isEmptyParams.test(queryDto)) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }
        Team team = getTeamParams.apply(queryDto);
        String result = teamService.update(team);
        System.out.println(result);
    }

    public void delete(QueryDto queryDto) {
        if (isEmptyParams.test(queryDto)) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }
        if (queryDto.getParams().containsKey(ParamList.ID.getKeyName())) {
            Long id = getParamId.apply(queryDto);
            String result = teamService.delete(id);
            System.out.println(result);
        }
    }

    private Function<QueryDto, Team> getTeamParams = (queryParseDto) -> {
        String stadiumId = "";
        String name = "";

        try {
            for (Map.Entry<String, String> entry : queryParseDto.getParams().entrySet()) {
                if (entry.getKey().equals(ParamList.STADIUM_ID.getKeyName()) &&
                        !entry.getValue().isEmpty()) {
                    stadiumId = entry.getValue();
                }
                if (entry.getKey().equals(ParamList.NAME.getKeyName()) &&
                        !entry.getValue().isEmpty()) {
                    name = entry.getValue();
                }
            }
        } catch (IllegalStateException | NullPointerException | NumberFormatException e) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }

        Team team = Team.builder()
                .stadiumId(Long.valueOf(stadiumId))
                .name(name)
                .build();

        if (team == null) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }

        return team;
    };
}
