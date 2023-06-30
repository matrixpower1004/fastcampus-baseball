package team.snowball.baseball.controller;

import team.snowball.baseball.code.Command;
import team.snowball.baseball.dto.QueryParseDto;
import team.snowball.baseball.handler.InvalidInputException;
import team.snowball.baseball.model.stadium.Stadium;
import team.snowball.baseball.service.StadiumService;

import java.util.Map;
import java.util.function.Function;

import static team.snowball.baseball.code.ErrorMessage.*;

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
            Long id = getParamId.apply(queryParseDto);
            stadiumService.delete(id);
        }
    }

    public static Function<QueryParseDto, Stadium> setStadiumParams = (queryParseDto) -> {
        String name = "";

        try {
            for (Map.Entry<String, String> entry : queryParseDto.getParams().entrySet()) {
                if (entry.getKey().equals("name") && entry.getValue() != null) {
                    name = entry.getValue();
                }
            }
        } catch (IllegalStateException e) {
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
