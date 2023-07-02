package team.snowball.baseball.controller;

import team.snowball.baseball.code.ParamList;
import team.snowball.baseball.dto.QueryDto;
import team.snowball.baseball.handler.InvalidInputException;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_INVALID_PARAMETER;

/**
 * author         : Jason Lee
 * date           : 2023-06-28
 * description    :
 */
public interface ModelController {

    void findAll();
    void findById(QueryDto queryDto);
    void save(QueryDto queryDto);
    void update(QueryDto queryDto);
    void delete(QueryDto queryDto);

    Predicate<QueryDto> isEmptyParams = (queryDto) -> queryDto.getParams().isEmpty();

    Function<QueryDto, Long> getParamId = (queryParseDto) -> {
        String id = "";
        try {
            for (Map.Entry<String, String> entry : queryParseDto.getParams().entrySet()) {
                if (entry.getKey().equals(ParamList.ID.getKeyName()) && entry.getValue() != null) {
                    id = entry.getValue();
                }
            }
        } catch (IllegalStateException | NullPointerException e) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }

        if (id.isEmpty()) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }

        return Long.valueOf(id);
    };
}
