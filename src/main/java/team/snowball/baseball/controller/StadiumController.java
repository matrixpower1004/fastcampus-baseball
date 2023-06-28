package team.snowball.baseball.controller;

import team.snowball.baseball.code.Command;
import team.snowball.baseball.dto.QueryParseDto;
import team.snowball.baseball.handler.InvalidInputException;
import team.snowball.baseball.model.player.Player;
import team.snowball.baseball.model.stadium.Stadium;
import team.snowball.baseball.service.StadiumService;

import java.util.Map;
import java.util.function.Function;

import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_INVALID_PARAMETER;

/**
 * author         : Jason Lee
 * date           : 2023-06-28
 * description    :
 */
public class StadiumController implements ModelController {

    private static StadiumController stadiumController;
    private static final StadiumService stadiumService = StadiumService.getInstance();


    private StadiumController() {
    }

    public static StadiumController getInstance() {
        if (stadiumController == null) {
            stadiumController = new StadiumController();
        }
        return stadiumController;
    }

    public void interpretCommand() {

    }

    @Override
    public void execute(QueryParseDto queryParseDto) {
        if (queryParseDto.getCommand().equals(Command.CREATE)) {
            Stadium stadium = setStadiumParams.apply(queryParseDto);
            stadiumService.create(stadium);
        }
        if (queryParseDto.getCommand().equals(Command.READ)) {
            stadiumService.read();
        }
        if (queryParseDto.getCommand().equals(Command.PUT)) {
            Stadium stadium = setStadiumParams.apply(queryParseDto);
            stadiumService.update(stadium);
        }
        if (queryParseDto.getCommand().equals(Command.DELETE)) {
            stadiumService.delete();
        }
        // 여기까지 왔다면 잘못된 명령어를 입력한 케이스
        throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
    }

    public static Function<QueryParseDto, Stadium> setStadiumParams = (queryParseDto) -> {
        String id = null;
        String name = null;

        try {
            for (Map.Entry<String, String> entry : queryParseDto.getParams().entrySet()) {
                if (entry.getKey().equals("id")) {
                    id = entry.getValue();
                }
                if (entry.getKey().equals("name")) {
                    name = entry.getValue();
                }
            }
        } catch (IllegalStateException e) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }

        Stadium stadium = null;
        try {
            stadium = Stadium.builder()
                    .id(Long.parseLong(id))
                    .name(name)
                    .build();
        } catch (NumberFormatException | NullPointerException e) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }

        if (stadium == null) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }

        return stadium;
    };
}
