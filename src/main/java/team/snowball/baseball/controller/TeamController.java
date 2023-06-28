package team.snowball.baseball.controller;

import team.snowball.baseball.code.Command;
import team.snowball.baseball.dto.QueryParseDto;
import team.snowball.baseball.handler.InvalidInputException;
import team.snowball.baseball.model.team.Team;
import team.snowball.baseball.service.TeamService;

import java.util.Map;
import java.util.function.Function;

import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_INVALID_PARAMETER;
import static team.snowball.baseball.controller.PlayerController.getParamId;

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
        if (queryParseDto.getCommand().equals(Command.CREATE)) {
            Team team = setTeamParams.apply(queryParseDto);
            teamService.create(team);
        }
        if (queryParseDto.getCommand().equals(Command.READ)) {
            teamService.read();
        }
        if (queryParseDto.getCommand().equals(Command.PUT)) {
            Team team = setTeamParams.apply(queryParseDto);
            teamService.update(team);
        }
        if (queryParseDto.getCommand().equals(Command.DELETE)) {
            Long id = getParamId.apply(queryParseDto);
            teamService.delete(id);
        }
        // 여기까지 왔다면 잘못된 명령어를 입력한 케이스
        throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
    }

    public static Function<QueryParseDto, Team> setTeamParams = (queryParseDto) -> {
        String id = "";
        String stadiumId = "";
        String name = "";

        try {
            for (Map.Entry<String, String> entry : queryParseDto.getParams().entrySet()) {
                if (entry.getKey().equals("id") && entry.getValue() != null) {
                    id = entry.getValue();
                }
                if (entry.getKey().equals("stadiumId") && entry.getValue() != null) {
                    stadiumId = entry.getValue();
                }
                if (entry.getKey().equals("name") && entry.getValue() != null) {
                    name = entry.getValue();
                }
            }
        } catch (IllegalStateException e) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }

        Team team = Team.builder()
                    .id(Long.parseLong(id))
                    .stadiumId(Integer.parseInt(stadiumId))
                    .name(name)
                    .build();

        if (team == null) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }

        return team;
    };
}
