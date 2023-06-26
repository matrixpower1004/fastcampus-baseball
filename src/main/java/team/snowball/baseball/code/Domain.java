package team.snowball.baseball.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team.snowball.baseball.handler.InvalidInputException;

import java.util.Arrays;
import java.util.List;

/**
 * author         : Jason Lee
 * date           : 2023-06-26
 * description    :
 */
@Getter
@AllArgsConstructor
public enum Domain {
    STADIUM("야구장"),
    TEAM("팀"),
    PLAYER("선수"),
    OUT_PLAYER("퇴출");

    private final String domain;

    public static Domain findByDomainName(String request) {
        return Arrays.stream(Domain.values())
                .filter(d -> d.domain.equals(request.substring(0, d.domain.length())))
                .findFirst()
                .orElseThrow(InvalidInputException::new);
    }

    public boolean isEquals(String request) {
        return this.domain.equals(request);
    }
}
