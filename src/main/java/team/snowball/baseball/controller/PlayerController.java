package team.snowball.baseball.controller;

import team.snowball.baseball.dto.QueryDto;
import team.snowball.baseball.handler.InvalidInputException;
import team.snowball.baseball.model.player.Player;
import team.snowball.baseball.service.PlayerService;

import java.util.Map;
import java.util.function.Function;

import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_INVALID_PARAMETER;
import static team.snowball.baseball.code.ParamList.*;

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
    public void read() {
        playerService.read();
    }

    public void read(QueryDto queryDto) {
        if (isEmptyParams.test(queryDto)) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }
        Long teamId = Long.valueOf(queryDto.getParams().get("teamId"));
        playerService.read(teamId);
    }

    public void save(QueryDto queryDto) {
        if (isEmptyParams.test(queryDto)) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }
        Player player = getPlayerParams.apply(queryDto);
        playerService.save(player);
    }

    @Override
    public void update(QueryDto queryDto) {
        if (isEmptyParams.test(queryDto)) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }
        Player player = getPlayerParams.apply(queryDto);
        playerService.update(player);
    }

    @Override
    public void delete(QueryDto queryDto) {
        if (isEmptyParams.test(queryDto)) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }
        Long id = getParamId.apply(queryDto);
        playerService.delete(id);
    }

    public void readByPosition() {
        playerService.reqdPositionBy();
    }

    private Function<QueryDto, Player> getPlayerParams = (queryDto) -> {
        String teamId = "";
        String name = "";
        String position = "";

        try {
            for (Map.Entry<String, String> entry : queryDto.getParams().entrySet()) {
                if (entry.getKey().equals(TEAM_ID.keyName) &&
                        entry.getValue() != null) {
                    teamId = entry.getValue();
                }
                if (entry.getKey().equals(NAME.keyName) &&
                        entry.getValue() != null) {
                    name = entry.getValue();
                }
                if (entry.getKey().equals(POSITION.keyName) && entry.getValue() != null) {
                    position = entry.getValue();
                }
            }

            if (teamId.isEmpty() || name.isEmpty() || position.isEmpty()) {
                throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
            }

            Player player = Player.builder()
                    .teamId(Long.valueOf(teamId))
                    .name(name)
                    .position(position)
                    .build();

            if (player == null) {
                throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
            }

            return player;

        } catch (IllegalStateException | NullPointerException | NumberFormatException e) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }
    };
}
