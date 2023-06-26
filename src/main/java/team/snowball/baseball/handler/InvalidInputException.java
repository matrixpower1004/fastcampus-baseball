package team.snowball.baseball.handler;

import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_INVALID_INPUT;

/**
 * author         : Jason Lee
 * date           : 2023-06-26
 * description    :
 */
public class InvalidInputException extends RuntimeException {
    public InvalidInputException() {
        super(ERR_MSG_INVALID_INPUT.getErrorMessage());
    }

    public InvalidInputException(String message) {
        super(message);
    }
}
