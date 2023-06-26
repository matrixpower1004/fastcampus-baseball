package team.snowball.baseball.handler;

import static team.snowball.baseball.code.ConsoleMessage.MSG_INPUT_END;

/**
 * author         : Jason Lee
 * date           : 2023-06-26
 * description    :
 */
public class InputEndException extends RuntimeException {
    public InputEndException() {
        super(MSG_INPUT_END.getMessage());
    }

    public InputEndException(String message) {
        super(message);
    }
}
