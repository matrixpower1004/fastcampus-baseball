package team.snowball.baseball.dto;

import lombok.*;
import team.snowball.baseball.controller.Command;

import java.util.Map;

/**
 * author         : Jason Lee
 * date           : 2023-06-26
 * description    :
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class QueryDto {

    private Command command;
    private Map<String, String> params;

}
