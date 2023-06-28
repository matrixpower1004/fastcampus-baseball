package team.snowball.baseball;

import team.snowball.baseball.code.Domain;
import team.snowball.baseball.controller.ModelController;
import team.snowball.baseball.dto.QueryParseDto;
import team.snowball.baseball.handler.InputEndException;
import team.snowball.baseball.handler.InvalidInputException;
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
    private static ModelController controller;

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
                controller = Domain.getController(queryParseDTO.getDomain());
                controller.execute(queryParseDTO);

            } catch (InputEndException e) {
                System.out.println(MSG_INPUT_END.getMessage());
                break;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    } // end of run()

} //end of class
