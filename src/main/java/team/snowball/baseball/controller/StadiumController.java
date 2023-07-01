package team.snowball.baseball.controller;

import team.snowball.baseball.code.Command;
import team.snowball.baseball.dto.QueryParseDto;
import team.snowball.baseball.handler.InvalidInputException;
import team.snowball.baseball.model.stadium.Stadium;
import team.snowball.baseball.service.StadiumService;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import static team.snowball.baseball.code.Command.*;
import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_FAILED_TO_FIND;
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

    @Override
    public void execute(QueryParseDto queryParseDto) {
        Command command = queryParseDto.getCommand();

        switch (command) {
            case CREATE -> stadiumService.create(getStadiumParams.apply(queryParseDto));
            case READ -> selectStadiumRead.accept(queryParseDto);
            case PUT -> stadiumService.update(getStadiumParams.apply(queryParseDto));
            case DELETE -> stadiumService.delete(getParamId.apply(queryParseDto));
            default -> throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }
    }

    private Consumer<QueryParseDto> selectStadiumRead = (queryParseDto -> {
        // 1. 파라미터에 id가 존재하는 경우
        if (queryParseDto.getParams().containsKey("id")) {
            stadiumService.read(getParamId.apply(queryParseDto));
            return;
        }
        // 2. 파라미터에 id가 존재하지 않는 경우
        stadiumService.read();
    });

    public static Function<QueryParseDto, Stadium> getStadiumParams = (queryParseDto) -> {
        String name = "";

        try {
            for (Map.Entry<String, String> entry : queryParseDto.getParams().entrySet()) {
                if (entry.getKey().equals("name") && entry.getValue() != null) {
                    name = entry.getValue();
                }
            }
        } catch (IllegalStateException | NullPointerException e) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }

        Stadium stadium = Stadium.builder()
                .name(name)
                .build();

       if (stadium == null) {
           throw new InvalidInputException(ERR_MSG_FAILED_TO_FIND.getErrorMessage());
       }

       return stadium;
    };
}
