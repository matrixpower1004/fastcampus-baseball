package team.snowball.baseball.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team.snowball.baseball.handler.InvalidInputException;
import team.snowball.baseball.service.*;

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
    STADIUM("야구장", StadiumService::getInstance),
    TEAM("팀", TeamService::getInstance),
    PLAYER("선수", PlayerService::getInstance),
    OUT_PLAYER("퇴출", OutPlayerService::getInstance),
    POSITION_VIEW("포지션별", PositionService::getInstance);

    private final String domain;
    private final Supplier<CommandService> commandService;

    public static Domain findByDomainName(String request) {
        return Arrays.stream(Domain.values())
                .filter(d -> d.domain.equals(request.substring(0, d.domain.length())))
                .findFirst()
                .orElseThrow(InvalidInputException::new);
    }

    public static CommandService getDomain(Domain domain) {
        return domain.commandService.get();
    }

    public boolean isEquals(String request) {
        return this.domain.equals(request);
    }
}
