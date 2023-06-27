package team.snowball.baseball.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team.snowball.baseball.handler.InvalidInputException;

import java.util.Arrays;
import java.util.List;

/**
 * author         : Jason Lee
 * date           : 2023-06-26
 * description    :
 */
@Getter
@AllArgsConstructor
public enum Command {
    CREATE("등록"),
    READ("목록"),
    PUT("수정"),
    DELETE("삭제");

    private final String command;

    public static Command findByCommandName(String request) {
        return Arrays.stream(Command.values())
                .filter(c -> c.command.equals(request.substring(
                        request.length() - c.command.length(), request.length())))
                .findFirst()
                .orElseThrow(InvalidInputException::new);
    }

    public boolean isEquals(String request) {
        return this.command.equals(request);
    }
}
