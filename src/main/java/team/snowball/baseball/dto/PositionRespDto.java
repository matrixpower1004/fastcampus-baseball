package team.snowball.baseball.dto;

import lombok.*;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * author         : Jason Lee
 * date           : 2023-06-29
 * description    :
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PositionRespDto {
    // Map의 단점인 순서 보장이 안되는 것을 보완하기 위해 LinkedHashMap 사용
    private List<String> teamNames;
    private LinkedHashMap<String, List<String>> playersByPosition;
}
