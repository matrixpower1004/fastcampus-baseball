package team.snowball.baseball.dto;

import lombok.*;

import java.util.Map;

/**
 * author         : Yongwon Kim
 * date           : 2023-06-28
 * description    :
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PositionRespDto {

    private String position;
    private Map<String, String> players;

    @Override
    public String toString() {
        return "PositionRespDto{" +
                "position='" + position + '\'' +
                ", players=" + players +
                '}';
    }
}
