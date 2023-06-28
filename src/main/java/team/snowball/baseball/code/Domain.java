package team.snowball.baseball.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team.snowball.baseball.controller.*;
import team.snowball.baseball.handler.InvalidInputException;
import team.snowball.baseball.service.InputService;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * author         : Jason Lee
 * date           : 2023-06-26
 * description    :
 */
@Getter
@AllArgsConstructor
public enum Domain {
    STADIUM("야구장", StadiumController::getInstance),
    TEAM("팀", TeamController::getInstance),
    PLAYER("선수", PlayerController::getInstance),
    OUT_PLAYER("퇴출",OutPlayerController::getInstance),
    POSITION_BY("포지션별", PlayerController::getInstance);

    private final String domain;
    private final Supplier<ModelController> modelController;

    public static ModelController getController(Domain domain) {
        return domain.modelController.get();
    }
    public static Domain findByDomainName(String request) {
        return Arrays.stream(Domain.values())
                .filter(d -> d.domain.equals(request.substring(0, d.domain.length())))
                .findFirst()
                .orElseThrow(InvalidInputException::new);
    }
}
