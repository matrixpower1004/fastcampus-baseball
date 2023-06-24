package team.snowball.baseball.resource;

/**
 * author         : Jason Lee
 * date           : 2023-06-25
 * description    :
 */
public interface ErrorMessage {
    String ERR_MSG_INVALID_DATABASE = "The database has encountered an issue. Please try again";
    String ERR_MSG_INVALID_INPUT_EMPTY = "Empty Input. Please input something.";
    String ERR_MSG_INVALID_INPUT_RANGE = "Out of range for input. Please try again.";
    String ERR_MSG_INVALID_INPUT_TYPE = "Invalid Type for Input. Please try again.";
    String ERR_MSG_DUPLICATE_ID = "Input id is Duplicated. Please input different id.";
    String ERR_MSG_GROUP_CUSTOMER_EMPTY = "There are no customers in this group.";
    String ERR_MSG_INPUT_END = "END is pressed. Exit this domain.menu.";
    String END_MSG = "END";
}
