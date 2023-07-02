package team.snowball.baseball.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team.snowball.baseball.handler.InvalidInputException;

import java.util.Arrays;

/**
 * author         : Jason Lee
 * date           : 2023-07-01
 * description    :
 */
@Getter
@AllArgsConstructor
public enum ParamList {
    ID("id"),
    TEAM_ID("teamId"),
    NAME("name"),
    REASON("reason"),
    POSITION("position"),
    STADIUM_ID("stadiumId"),
    PLAYER_ID("playerId");

    final String keyName;

    public static ParamList findByKeyName(String request) {
        return Arrays.stream(ParamList.values())
                .filter(d -> d.keyName.equalsIgnoreCase(request))
                .findFirst()
                .orElseThrow(InvalidInputException::new);
    }

    public static boolean isMatchKey(String request) {
        return Arrays.stream(ParamList.values())
                .anyMatch(d -> d.keyName.equalsIgnoreCase(request));
    }
}
