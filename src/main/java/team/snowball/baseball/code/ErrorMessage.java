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
    ERR_MSG_INTERNAL_SERVER("The server has encountered an issue. Please try again"),
    ERR_MSG_INVALID_INPUT("The input is empty or invalid. Please try again."),
    ERR_MSG_INVALID_COMMAND("An uninterpretable command. Please try again."),
    ERR_MSG_DUPLICATE_ID("Duplicate input ID. Please provide a unique ID."),
    ERR_MSG_INVALID_PARAMETER("The parameter value has been entered incorrectly. Please try again."),
    ERR_MSG_FAILED_TO_REGISTER("The data registration failed. Please try again."),
    ERR_MSG_FAILED_TO_DELETE("The data deletion failed. Please try again."),
    ERR_MSG_FAILED_TO_UPDATE("The data update failed. Please try again."),
    ERR_MSG_FAILED_TO_FIND("The data search failed. Please try again."),
    ERR_MSG_FAILED_TO_FIND_TEAM_NAME("The team name search failed."),
    ERR_MSG_OVERLAP_NAME("The duplicate name detected."),
    ERR_MSG_OVERLAP_ID("The duplicate id detected.");

    private final String errorMessage;
}
