package team.snowball.baseball.controller;

import team.snowball.baseball.code.Command;
import team.snowball.baseball.dto.QueryParseDto;
import team.snowball.baseball.handler.InvalidInputException;
import team.snowball.baseball.model.player.Player;
import team.snowball.baseball.service.PlayerService;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import static team.snowball.baseball.code.Domain.POSITION_BY;
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
        Command command = queryParseDto.getCommand();

        switch (command) {
            case CREATE -> playerService.create(getPlayerParams.apply(queryParseDto));
            /**
             * READ 에는 3가지 경우가 존재
             * 1. 특이 케이스인 '포지션별목록' 명령어를 처리하는 경우
             * 2. ?teamdId=1과 같이 특정 팀의 선수 목록을 출력하는 경우
             * 3. 옵션 없이 전체 선수 목록을 출력하는 경우
             */
            case READ -> selectPlayerRead.accept(queryParseDto);
            case PUT -> playerService.update(getPlayerParams.apply(queryParseDto));
            case DELETE -> playerService.delete(getParamId.apply(queryParseDto));
            default -> throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }
    }

    private Consumer<QueryParseDto> selectPlayerRead = (queryParseDto) -> {
        // 1. TeamId로 조회하는 경우
        if (queryParseDto.getParams().containsKey("teamId")) {
            Long id = getParamId.apply(queryParseDto);
            playerService.read(id);
            return;
        }
        // 2. 포지션변목록 보기
        if (queryParseDto.getDomain().equals(POSITION_BY)) {
            playerService.readPositionBy();
            return;
        }
        // 전체 선수 목록 보기
        playerService.read();
    };

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
        } catch (IllegalStateException | NullPointerException e) {
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
    };
}
