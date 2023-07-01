package team.snowball.baseball.controller;

import team.snowball.baseball.code.Command;
import team.snowball.baseball.dto.QueryParseDto;
import team.snowball.baseball.handler.InvalidInputException;
import team.snowball.baseball.model.player.OutPlayer;
import team.snowball.baseball.service.OutPlayerService;

import java.util.Map;
import java.util.function.Consumer;
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
        Command command = queryParseDto.getCommand();

        switch (command) {
            case CREATE:
                outPlayerService.create(getOutPlayerParams.apply(queryParseDto));
                break;
            case READ:
                // READ에는 2가지 경우가 존재한다.
                selectOutPlayerRead.accept(queryParseDto);
                break;
            case PUT:
                outPlayerService.update(getOutPlayerParams.apply(queryParseDto));
                break;
            case DELETE:
                outPlayerService.delete(getParamId.apply(queryParseDto));
                break;
        }
    }

    Consumer<QueryParseDto> selectOutPlayerRead = (queryParseDto) -> {
        // 1. 파라미터에 id가 존재하는 경우
        if (queryParseDto.getParams().containsKey("playerId")) {
            outPlayerService.read(getParamId.apply(queryParseDto));
            return;
        }
        // 2. 파라미터가 없는 경우
        outPlayerService.read();
    };


    public static Function<QueryParseDto, OutPlayer> getOutPlayerParams = (queryParseDto) -> {
        String playerId = "";
        String reason = "";

        try {
            for (Map.Entry<String, String> entry : queryParseDto.getParams().entrySet()) {
                if (entry.getKey().equals("playerId") && entry.getValue() != null) {
                    playerId = entry.getValue();
                }
                if (entry.getKey().equals("reason") && entry.getValue() != null) {
                    reason = entry.getValue();
                }
            }
        } catch (IllegalStateException | NullPointerException e) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }

        OutPlayer outPlayer = OutPlayer.builder()
                .playerId(Long.valueOf(playerId))
                .reason(reason)
                .build();

        if (outPlayer == null) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }

        return outPlayer;
    };
}
