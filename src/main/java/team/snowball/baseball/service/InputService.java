package team.snowball.baseball.service;

import team.snowball.baseball.controller.Command;
import team.snowball.baseball.dto.QueryDto;
import team.snowball.baseball.handler.InputEndException;
import team.snowball.baseball.handler.InvalidInputException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import static team.snowball.baseball.controller.Command.findByCommandName;
import static team.snowball.baseball.code.ConsoleMessage.MSG_GUIDE_END;
import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_INVALID_INPUT;
import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_INVALID_PARAMETER;
import static team.snowball.baseball.code.ParamList.isMatchKey;

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

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public String nextLIne() {
        System.out.println(MSG_GUIDE_END.getMessage());
        String query = "";
        try {
            query = br.readLine();
            if (query.toUpperCase().equals("END")) {
                throw new InputEndException();
            }
        } catch (IOException | NullPointerException e) {
            throw new InvalidInputException();
        }
        return query;
    }

    public QueryDto queryParse(String query) {
        if (query.isEmpty()) {
            throw new InvalidInputException();
        }

        // parameter 없이 입력되었다면 명령어만 해석하여 리턴
        if (query.indexOf("?") < 1) {
            QueryDto queryDto = getCommandOnly.apply(query);
            return queryDto;
        }

        // ?을 기준으로 1차로 나누기
        String command = "";
        String params = "";
        try {
            StringTokenizer st = new StringTokenizer(query, "?");
            while (st.hasMoreTokens()) {
                command = st.nextToken();
                params = st.nextToken();
            }

            QueryDto queryDto = getCommandWithParams.apply(command, params);
            return queryDto;

        } catch (NoSuchElementException e) {
            throw new InvalidInputException(ERR_MSG_INVALID_INPUT.getErrorMessage());
        }
    }

    private final Function<String, QueryDto> getCommandOnly = (String query) -> {
        return QueryDto.builder()
                .command(findByCommandName(query))
                .params(new HashMap<>()) // NullPointException 방지
                .build();
    };

    private final BiFunction<String, String, QueryDto> getCommandWithParams = (header, body) -> {
        Command command = findByCommandName(header);

        if (body.indexOf("=") < 1) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }

        Map<String, String> paramMap = getQueryMap(body);

        return QueryDto.builder()
                .command(command)
                .params(paramMap)
                .build();
    };

    private HashMap<String, String> getQueryMap(String params) {
        try {
            StringTokenizer paramToken = new StringTokenizer(params, "&");
            HashMap<String, String> paramMap = new HashMap<>();
            while (paramToken.hasMoreTokens()) {
                StringTokenizer keyWithValue = new StringTokenizer(paramToken.nextToken(), "=");
                String key = keyWithValue.nextToken();
                String value = keyWithValue.nextToken();

                if (!isMatchKey(key)) {
                    throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
                }
                if (key.isEmpty() || value.isEmpty()) {
                    throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
                }
                paramMap.put(key, value);
            }
            return paramMap;

        } catch (NoSuchElementException | NullPointerException e) {
            throw new InvalidInputException(ERR_MSG_INVALID_PARAMETER.getErrorMessage());
        }
    };
}
