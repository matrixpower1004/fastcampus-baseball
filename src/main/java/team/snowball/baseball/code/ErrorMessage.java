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
public enum ErrorMessage {
    ERR_MSG_INVALID_DATABASE("The database has encountered an issue. Please try again"),
    ERR_MSG_INVALID_INPUT("The input is empty or invalid. Please try again."),
    ERR_MSG_DUPLICATE_ID("Input id is Duplicated. Please input different id.");

    private final String errorMessage;
}
