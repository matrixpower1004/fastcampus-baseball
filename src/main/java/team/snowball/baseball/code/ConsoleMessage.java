package team.snowball.baseball.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * author         : Jason Lee
 * date           : 2023-06-25
 * description    :
 */
@Getter
@AllArgsConstructor
public enum ConsoleMessage {

    MSG_REQUEST_INPUT("어떤 기능을 요청하시겠습니까?"),
    MSG_GUIDE_END("** Press 'end', if you want to exit! **"),
    MSG_END("END"),
    MSG_INPUT_END("END is pressed. Exit this domain.menu.");

    private final String message;
}
