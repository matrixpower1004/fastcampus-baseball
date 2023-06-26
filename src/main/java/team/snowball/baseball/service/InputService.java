package team.snowball.baseball.service;

import team.snowball.baseball.code.Command;
import team.snowball.baseball.code.Domain;
import team.snowball.baseball.dto.QueryParseDto;
import team.snowball.baseball.handler.InvalidInputException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static team.snowball.baseball.code.Command.findByCommandName;
import static team.snowball.baseball.code.Domain.*;

/**
 * author         : Jason Lee
 * date           : 2023-06-26
 * description    :
 */
public class InputService {

    private static InputService inputService;

    private InputService() {}

    public static InputService getInstance() {
        if (inputService == null) {
            inputService = new InputService();
        }
        return inputService;
    }

    public QueryParseDto queryParse(String query) {

        // 입력 null 체크
        if (query.isBlank() || query == null) {
            throw new InvalidInputException();
        }

        // ?을 기준으로 1차로 나누기
        String[] parts = query.split("\\?");
        if (parts.length == 0) {
            throw new InvalidInputException();
        }

        // Query parameter 없이 명령어만 있다면, 명령어만 해석하여 리턴
        if (parts.length == 1 ) {
            System.out.println("명령어만 있습니다.");
            QueryParseDto queryParseDTO = QueryParseDto.builder()
                    .domain(findByDomainName(query))
                    .command(findByCommandName(query))
                    .build();
            System.out.println(queryParseDTO);
            return queryParseDTO;
        }

        // Query paramer 해석하여 리턴
        String commands = parts[0];
        String params = parts[1];

        // &를 기준으로 나누기
        if (params.indexOf("=") < 1) {
            throw new InvalidInputException();
        }

        QueryParseDto queryParseDTO = QueryParseDto.builder()
                .domain(findByDomainName(commands))
                .command(findByCommandName(commands))
                .params(Arrays.stream(params.split("&"))
                        .map((String it) -> splitQueryParams(it))
                        .collect(Collectors.toMap(m -> m.keySet().iterator().next(), m -> m.values().iterator().next())))
                .build();
        System.out.println(queryParseDTO);

        return queryParseDTO;
    }

    private HashMap<String, String> splitQueryParams(String it) {
        final int idx = it.indexOf("=");
        final String key = idx > 0 ? it.substring(0, idx) : it;
        final String value = idx > 0 && it.length() > idx + 1 ? it.substring(idx + 1) : null;
        return new HashMap<>(Map.of(key, value));
    }

}
