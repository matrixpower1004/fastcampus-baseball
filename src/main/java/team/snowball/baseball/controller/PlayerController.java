package team.snowball.baseball.controller;

import team.snowball.baseball.code.Command;
import team.snowball.baseball.code.Domain;
import team.snowball.baseball.dto.QueryParseDto;
import team.snowball.baseball.handler.InvalidInputException;
import team.snowball.baseball.model.player.Player;
import team.snowball.baseball.service.InputService;
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
        // 예외적인 명령인 포지션별 목록을 처리
        if (queryParseDto.getDomain().getDomain().equals(Domain.POSITION_BY)
                && queryParseDto.getCommand().equals(Command.READ)) {
            playerService.positionByReport();
        }
        if (queryParseDto.getCommand().equals(Command.CREATE)) {
            Player player = setPlayerParams.apply(queryParseDto);
            playerService.create(player);
        }
        if (queryParseDto.getCommand().equals(Command.READ)) {
            playerService.read();
        }
        if (queryParseDto.getCommand().equals(Command.PUT)) {
            Player player = setPlayerParams.apply(queryParseDto);
            playerService.update(player);
        }
        if (queryParseDto.getCommand().equals(Command.DELETE)) {
            playerService.delete();
        }
        // 여기까지 왔다면 잘못된 명령어를 입력한 케이스
        throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());

    }

    private Function<QueryParseDto, Player> setPlayerParams = (queryParseDto) -> {
        String id = null;
        String teamId = null;
        String name = null;
        String position = null;

        try {
            for (Map.Entry<String, String> entry : queryParseDto.getParams().entrySet()) {
                if (entry.getKey().equals("id")) {
                    id = entry.getValue();
                }
                if (entry.getKey().equals("teamId")) {
                    teamId = entry.getValue();
                }
                if (entry.getKey().equals("name")) {
                    name = entry.getValue();
                }
                if (entry.getKey().equals("position")) {
                    position = entry.getValue();
                }
            }
        } catch (IllegalStateException e) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }

        Player player = null;
        try {
            player = Player.builder()
                    .id(Long.parseLong(id))
                    .teamId(Integer.parseInt(teamId))
                    .name(name)
                    .position(position)
                    .build();
        } catch (NumberFormatException | NullPointerException e) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }

        if (player == null) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }

        return player;
    };
}
