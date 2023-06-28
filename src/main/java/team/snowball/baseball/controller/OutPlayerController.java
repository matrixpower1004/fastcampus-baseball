package team.snowball.baseball.controller;

import team.snowball.baseball.code.Command;
import team.snowball.baseball.dto.QueryParseDto;
import team.snowball.baseball.handler.InvalidInputException;
import team.snowball.baseball.model.player.OutPlayer;
import team.snowball.baseball.model.player.Player;
import team.snowball.baseball.service.OutPlayerService;

import java.util.Map;
import java.util.function.Function;

import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_INVALID_PARAMETER;

/**
 * author         : Jason Lee
 * date           : 2023-06-28
 * description    :
 */
public class OutPlayerController implements ModelController {

    private static OutPlayerController outPlayerController;
    private static final OutPlayerService outPlayerService = OutPlayerService.getInstance();

    private OutPlayerController() {
    }

    public static OutPlayerController getInstance() {
        if (outPlayerController == null) {
            outPlayerController = new OutPlayerController();
        }
        return outPlayerController;
    }

    @Override
    public void execute(QueryParseDto queryParseDto) {
        if (queryParseDto.getCommand().equals(Command.CREATE)) {
            OutPlayer outPlayer = setOutPlayerParams.apply(queryParseDto);
            outPlayerService.create(outPlayer);
        }
        if (queryParseDto.getCommand().equals(Command.READ)) {
            outPlayerService.read();
        }
        if (queryParseDto.getCommand().equals(Command.PUT)) {
            OutPlayer outPlayer = setOutPlayerParams.apply(queryParseDto);
            outPlayerService.update(outPlayer);
        }
        if (queryParseDto.getCommand().equals(Command.DELETE)) {
            outPlayerService.delete();
        }
        // 여기까지 왔다면 잘못된 명령어를 입력한 케이스
        throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
    }

    public static Function<QueryParseDto, OutPlayer> setOutPlayerParams = (queryParseDto) -> {
        String id = null;
        String playerId = null;
        String reason = null;

        try {
            for (Map.Entry<String, String> entry : queryParseDto.getParams().entrySet()) {
                if (entry.getKey().equals("id")) {
                    id = entry.getValue();
                }
                if (entry.getKey().equals("playerId")) {
                    playerId = entry.getValue();
                }
                if (entry.getKey().equals("reason")) {
                    reason = entry.getValue();
                }
            }
        } catch (IllegalStateException e) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }

        OutPlayer outPlayer = null;
        try {
            outPlayer = OutPlayer.builder()
                    .id(Long.parseLong(id))
                    .playerId(Long.parseLong(playerId))
                    .reason(reason)
                    .build();
        } catch (NumberFormatException | NullPointerException e) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }

        if (outPlayer == null) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }

        return outPlayer;
    };
}
