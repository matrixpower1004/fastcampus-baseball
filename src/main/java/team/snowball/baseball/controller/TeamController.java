package team.snowball.baseball.controller;

import team.snowball.baseball.code.Command;
import team.snowball.baseball.dto.QueryParseDto;
import team.snowball.baseball.handler.InvalidInputException;
import team.snowball.baseball.model.team.Team;
import team.snowball.baseball.service.TeamService;

import java.util.Map;
import java.util.function.Function;

import static team.snowball.baseball.code.Command.*;
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
    public void execute(QueryParseDto queryParseDto) {
        Command command = queryParseDto.getCommand();

        if (command.equals(CREATE)) {
            Team team = getTeamParams.apply(queryParseDto);
            teamService.create(team);
        }
        if (command.equals(READ)) {
            teamService.read();
        }
        if (command.equals(READ_BY)) {
            Long id = getParamId.apply(queryParseDto);
            teamService.read(id);
        }
        if (command.equals(PUT)) {
            Team team = getTeamParams.apply(queryParseDto);
            teamService.update(team);
        }
        if (command.equals(DELETE)) {
            Long id = getParamId.apply(queryParseDto);
            teamService.delete(id);
        }
    }

    public static Function<QueryParseDto, Team> getTeamParams = (queryParseDto) -> {
        String stadiumId = "";
        String name = "";

        try {
            for (Map.Entry<String, String> entry : queryParseDto.getParams().entrySet()) {
                if (entry.getKey().equals("stadiumId") && entry.getValue() != null) {
                    stadiumId = entry.getValue();
                }
                if (entry.getKey().equals("name") && entry.getValue() != null) {
                    name = entry.getValue();
                }
            }
        } catch (IllegalStateException | NullPointerException e) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }

        Team team = Team.builder()
                .stadiumId(Integer.parseInt(stadiumId))
                .name(name)
                .build();

        if (team == null) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }

        return team;
    };
}
