package team.snowball.baseball.controller;

import team.snowball.baseball.code.Command;
import team.snowball.baseball.dto.QueryParseDto;
import team.snowball.baseball.handler.InvalidInputException;
import team.snowball.baseball.service.CommandService;

import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_INVALID_COMMAND;

/**
 * author         : Jason Lee
 * date           : 2023-06-28
 * description    :
 */
public class CommandController {

    private static CommandController commandController;

    public CommandController() {}

    public static CommandController getInstance() {
        if (commandController == null) {
            commandController = new CommandController();
        }
        return commandController;
    }

    public void interpret(QueryParseDto queryParseDto, CommandService service) {
        if (queryParseDto.getCommand().equals(Command.CREATE)) {
            service.create();
        } else if (queryParseDto.getCommand().equals(Command.READ)) {
            service.read();
        } else if (queryParseDto.getCommand().equals(Command.PUT)) {
            service.update();
        } else if (queryParseDto.getCommand().equals(Command.DELETE)) {
            service.delete();
        } else {
            throw new InvalidInputException(ERR_MSG_INVALID_COMMAND.getErrorMessage());
        }
    }

} // end of class CommandController
