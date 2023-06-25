package team.snowball.baseball.handler;

import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_INVALID_DATABASE;

/**
 * author         : Jason Lee
 * date           : 2023-06-25
 * description    :
 */
public class DatabaseException extends RuntimeException {
    public DatabaseException() {
        super(ERR_MSG_INVALID_DATABASE.getErrorMessage());
    }

    public DatabaseException(String message) {
        super(message);
    }
}
