package team.snowball.baseball.controller;

import team.snowball.baseball.code.ParamList;
import team.snowball.baseball.dto.QueryDto;
import team.snowball.baseball.handler.InvalidInputException;
import team.snowball.baseball.model.stadium.Stadium;
import team.snowball.baseball.service.StadiumService;
import team.snowball.baseball.view.StadiumReport;

import java.util.List;
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

    @Override
    public void findAll() {
        List<Stadium> stadiums = stadiumService.findAll();
        StadiumReport.showStadiumList(stadiums);
    }

    public void findById(QueryDto queryDto) {
        if (isEmptyParams.test(queryDto)) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }
        Long id = getParamId.apply(queryDto);
        Stadium stadium = stadiumService.findById(id);
        StadiumReport.showStadium(stadium);
    }

    public void save(QueryDto queryDto) {
        if (isEmptyParams.test(queryDto)) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }
        Stadium stadium = getStadiumParams.apply(queryDto);
        stadiumService.save(stadium);
    }

    public void update(QueryDto queryDto) {
        if (isEmptyParams.test(queryDto)) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }

        Stadium stadium = getStadiumParams.apply(queryDto);
        String result = stadiumService.update(stadium);
        System.out.println(result);
    }

    public void delete(QueryDto queryDto) {
        if (isEmptyParams.test(queryDto)) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }
        Long id = getParamId.apply(queryDto);
        String result = stadiumService.delete(id);
        System.out.println(result);
    }

    private Function<QueryDto, Stadium> getStadiumParams = (queryParseDto) -> {
        String name = "";

        try {
            for (Map.Entry<String, String> entry : queryParseDto.getParams().entrySet()) {
                if (entry.getKey().equals(ParamList.NAME.getKeyName()) &&
                        !entry.getValue().isEmpty()) {
                    name = entry.getValue();
                }
            }
        } catch (IllegalStateException | NullPointerException | NumberFormatException e) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }

        return Stadium.builder()
                .name(name)
                .build();
    };
}
