package team.snowball.baseball.controller;

import team.snowball.baseball.code.Domain;
import team.snowball.baseball.dto.QueryParseDto;

/**
 * author         : Jason Lee
 * date           : 2023-06-28
 * description    :
 */
public interface ModelController {
        void execute(QueryParseDto queryParseDto);
}
