package team.snowball.baseball.controller;

import team.snowball.baseball.code.ParamList;
import team.snowball.baseball.dto.PositionRespDto;
import team.snowball.baseball.dto.QueryDto;
import team.snowball.baseball.handler.InvalidInputException;
import team.snowball.baseball.model.player.Player;
import team.snowball.baseball.service.PlayerService;
import team.snowball.baseball.view.PlayerReport;
import team.snowball.baseball.view.Report;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_INVALID_PARAMETER;
import static team.snowball.baseball.code.ParamList.*;
import static team.snowball.baseball.view.PlayerReport.showPlayerByTeam;

/**
 * author         : Jason Lee
 * date           : 2023-06-28
 * description    :
 */
public class PlayerController implements ModelController {

    private static final PlayerService playerService = PlayerService.getInstance();

    private static PlayerController playerController;

    private PlayerController() {
    }

    public static PlayerController getInstance() {
        if (playerController == null) {
            playerController = new PlayerController();
        }
        return playerController;
    }

    @Override
    public void findAll() {
        List<Player> playerList = playerService.findAll();
        PlayerReport.showPlayerAll(playerList);
    }

    public void findByTeamId(QueryDto queryDto) {
        if (isEmptyParams.test(queryDto)) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }
        Long teamId = Long.valueOf(queryDto.getParams().get(TEAM_ID.getKeyName()));
        List<Player> playerList = playerService.findByTeamId(teamId);
        showPlayerByTeam(playerList);
    }

    public void findById(QueryDto queryDto) {
        if (isEmptyParams.test(queryDto)) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }
        Long id  = getParamId.apply(queryDto);
        System.out.println("여기까지 오나?" + id);
        Player player = playerService.findById(id);
        PlayerReport.showPlayerOne(player);
    }

    public void save(QueryDto queryDto) {
        if (isEmptyParams.test(queryDto)) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }
        Player player = getPlayerParams.apply(queryDto);
        int result = playerService.save(player);
        Report.showSaveResult.accept(result);

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

    public void findByPosition() {
        PositionRespDto positionRespDto = playerService.findPositionBy();
        // todo: view 구현
        PlayerReport.showPlayersByPosition(positionRespDto);
    }

    private Function<QueryDto, Player> getPlayerParams = (queryDto) -> {
        String teamId = "";
        String name = "";
        String position = "";

        try {
            for (Map.Entry<String, String> entry : queryDto.getParams().entrySet()) {
                if (entry.getKey().equals(TEAM_ID.getKeyName()) &&
                        !entry.getValue().isEmpty()) {
                    teamId = entry.getValue();
                }
                if (entry.getKey().equals(NAME.getKeyName()) &&
                        !entry.getValue().isEmpty()) {
                    name = entry.getValue();
                }
                //todo: position을 enum으로 변경하여 입력값을 강제하자
                if (entry.getKey().equals(POSITION.getKeyName()) &&
                        !entry.getValue().isEmpty()) {
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
