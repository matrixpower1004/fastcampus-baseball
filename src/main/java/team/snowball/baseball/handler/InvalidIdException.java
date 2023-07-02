package team.snowball.baseball.handler;

import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_DUPLICATE_ID;

/**
 * author         : Jason Lee
 * date           : 2023-06-26
 * description    :
 */
public class InvalidIdException extends RuntimeException {
    public InvalidIdException() {
        super(ERR_MSG_DUPLICATE_ID.getErrorMessage());
    }

    public InvalidIdException(String message) {
        super(message);
    }
}
