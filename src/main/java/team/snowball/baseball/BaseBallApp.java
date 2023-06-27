package team.snowball.baseball;

import team.snowball.baseball.code.Domain;
import team.snowball.baseball.controller.CommandController;
import team.snowball.baseball.dto.QueryParseDto;
import team.snowball.baseball.handler.InputEndException;
import team.snowball.baseball.handler.InvalidInputException;
import team.snowball.baseball.service.CommandService;
import team.snowball.baseball.service.InputService;

import static team.snowball.baseball.code.ConsoleMessage.MSG_INPUT_END;
import static team.snowball.baseball.code.ConsoleMessage.MSG_REQUEST_INPUT;

/**
 * author         : Jason Lee
 * date           : 2023-06-25
 * description    :
 */
public class BaseBallApp {

    private static BaseBallApp baseBallApp;
    private static final InputService inputService = InputService.getInstance();
    private static final CommandController commandController = CommandController.getInstance();
    private static CommandService service;

    private BaseBallApp() {
        baseBallApp = this;
    }

    public static BaseBallApp getInstance() {
        if (baseBallApp == null) {
            baseBallApp = new BaseBallApp();
        }
        return baseBallApp;
    }

    public void run() {
        while (true) {
            try {
                System.out.println(MSG_REQUEST_INPUT.getMessage()); // 입력 요청 메시지 출력
                final String query = inputService.nextLIne();

                QueryParseDto queryParseDTO = inputService.queryParse(query);
                service = Domain.getDomain(queryParseDTO.getDomain());

                //TODO: 잊지 말고 지우기 (디버깅용)
//                System.out.println(service.getClass().getName());

                commandController.interpret(queryParseDTO, service);

            } catch (InputEndException e) {
                System.out.println(MSG_INPUT_END.getMessage());
                break;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

} //end of class
