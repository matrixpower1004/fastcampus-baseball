package team.snowball.baseball.handler;

import static team.snowball.baseball.code.ErrorMessage.*;

/**
 * author         : Jason Lee
 * date           : 2023-06-29
 * description    :
 */
public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException() {
        super(ERR_MSG_INTERNAL_SERVER.getErrorMessage());
    }
    public InternalServerErrorException(String message) {
        super(message);
    }
}
