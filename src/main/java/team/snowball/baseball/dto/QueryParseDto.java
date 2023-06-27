package team.snowball.baseball.dto;

import lombok.*;
import team.snowball.baseball.code.Command;
import team.snowball.baseball.code.Domain;

import java.util.Map;

/**
 * author         : Jason Lee
 * date           : 2023-06-26
 * description    :
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueryParseDto {

    private Domain domain;
    private Command command;
    private Map<String, String> params;

    @Override
    public String toString() {
        return "QueryParseDto{" +
                "domain=" + domain +
                ", command=" + command +
                ", params=" + params +
                '}';
    }
}
