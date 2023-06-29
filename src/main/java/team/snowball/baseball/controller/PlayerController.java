package team.snowball.baseball.controller;

import team.snowball.baseball.code.Command;
import team.snowball.baseball.code.Domain;
import team.snowball.baseball.dto.QueryParseDto;
import team.snowball.baseball.handler.InvalidInputException;
import team.snowball.baseball.model.player.Player;
import team.snowball.baseball.service.PlayerService;

import java.util.Map;
import java.util.function.Function;

import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_INVALID_PARAMETER;

/**
 * author         : Jason Lee
 * date           : 2023-06-28
 * description    :
 */
public class PlayerController implements ModelController {

    private static PlayerController playerController;
    private static final PlayerService playerService = PlayerService.getInstance();

    private PlayerController() {
    }

    public static PlayerController getInstance() {
        if (playerController == null) {
            playerController = new PlayerController();
        }
        return playerController;
    }

    @Override
    public void execute(QueryParseDto queryParseDto) {
        // 예외적인 명령인 포지션별목록을 처리
        if (queryParseDto.getDomain().equals(Domain.POSITION_BY)
                && queryParseDto.getCommand().equals(Command.READ)) {
            playerService.positionByReport();
        }
        if (queryParseDto.getCommand().equals(Command.CREATE)) {
            Player player = getPlayerParams.apply(queryParseDto);
            playerService.create(player);
        }
        if (queryParseDto.getCommand().equals(Command.READ)) {
            Player player = getPlayerParams.apply(queryParseDto);
            int teamId = player.getTeamId();
            System.out.println(teamId);
            playerService.read(teamId);
        }
        if (queryParseDto.getCommand().equals(Command.PUT)) {
            Player player = getPlayerParams.apply(queryParseDto);
            playerService.update(player);
        }
        if (queryParseDto.getCommand().equals(Command.DELETE)) {
            Long id = getParamId.apply(queryParseDto);
            playerService.delete(id);
        }
        // 여기까지 왔다면 잘못된 명령어를 입력한 케이스
        throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());

    }

    private Function<QueryParseDto, Player> getPlayerParams = (queryParseDto) -> {
        String teamId = "";
        String name = "";
        String position = "";

        try {
            for (Map.Entry<String, String> entry : queryParseDto.getParams().entrySet()) {
                if (entry.getKey().equals("teamId") && entry.getValue() != null) {
                    teamId = entry.getValue();
                }
                if (entry.getKey().equals("name") && entry.getValue() != null) {
                    name = entry.getValue();
                }
                if (entry.getKey().equals("position") && entry.getValue() != null) {
                    position = entry.getValue();
                }
            }
        } catch (IllegalStateException e) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }

        Player player = Player.builder()
                    .teamId(Integer.valueOf(teamId))
                    .name(name)
                    .position(position)
                    .build();

        if (player == null) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }

        return player;
    };

//    public static Function<QueryParseDto, Long> getParamId = (queryParseDto) -> {
//        String id = "";
//        try {
//            for (Map.Entry<String, String> entry : queryParseDto.getParams().entrySet()) {
//                if (entry.getKey().equals("id") && entry.getValue() != null ) {
//                    id = entry.getValue();
//                }
//            }
//        } catch (IllegalStateException e) {
//            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
//        }
//
//        if (id.isEmpty()) {
//            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
//        }
//
//        return Long.valueOf(id);
//    };
}
