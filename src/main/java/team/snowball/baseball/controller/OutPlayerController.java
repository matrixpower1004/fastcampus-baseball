package team.snowball.baseball.controller;

import team.snowball.baseball.dto.QueryDto;
import team.snowball.baseball.handler.InvalidInputException;
import team.snowball.baseball.model.player.OutPlayer;
import team.snowball.baseball.service.OutPlayerService;

import java.util.Map;
import java.util.function.Function;

import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_INVALID_PARAMETER;
import static team.snowball.baseball.code.ParamList.PLAYER_ID;
import static team.snowball.baseball.code.ParamList.REASON;

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
    public void findAll() {
        outPlayerService.find();
    }

    public void findById(QueryDto queryDto) {
        if (isEmptyParams.test(queryDto)) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }
        Long playerId = getParamId.apply(queryDto);
        outPlayerService.find(playerId);
    }

    public void save(QueryDto queryDto) {
        if (isEmptyParams.test(queryDto)) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }
        OutPlayer outPlayer = getOutPlayerParams.apply(queryDto);
        outPlayerService.save(outPlayer);
    }

    public void update(QueryDto queryDto) {
        if (isEmptyParams.test(queryDto)) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }
        OutPlayer outPlayer = getOutPlayerParams.apply(queryDto);
        outPlayerService.update(outPlayer);
    }

    public void delete(QueryDto queryDto) {
        if (isEmptyParams.test(queryDto)) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }
        Long playerId = getParamId.apply(queryDto);
        outPlayerService.delete(playerId);
    }

    public static Function<QueryDto, OutPlayer> getOutPlayerParams = (queryParseDto) -> {
        String playerId = "";
        String reason = "";

        try {
            for (Map.Entry<String, String> entry : queryParseDto.getParams().entrySet()) {
                if (entry.getKey().equals(PLAYER_ID.getKeyName()) && entry.getValue() != null) {
                    playerId = entry.getValue();
                }
                if (entry.getKey().equals(REASON.getKeyName()) && entry.getValue() != null) {
                    reason = entry.getValue();
                }
            }

            return OutPlayer.builder()
                    .playerId(Long.valueOf(playerId))
                    .reason(reason)
                    .build();

        } catch (IllegalStateException | NullPointerException| NumberFormatException e) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }
    };
} // end of class