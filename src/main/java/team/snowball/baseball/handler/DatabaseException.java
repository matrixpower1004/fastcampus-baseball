package team.snowball.baseball.handler;

import team.snowball.baseball.resource.ErrorMessage;

/**
 * author         : Jason Lee
 * date           : 2023-06-25
 * description    :
 */
public class DatabaseException extends RuntimeException {
    public DatabaseException() {
        super(ErrorMessage.ERR_MSG_INVALID_DATABASE);
    }

    public DatabaseException(String message) {
        super(message);
    }
}
