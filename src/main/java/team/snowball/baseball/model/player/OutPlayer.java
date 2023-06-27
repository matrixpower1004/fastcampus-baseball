package team.snowball.baseball.model.player;

import lombok.*;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * author         : Jason Lee
 * date           : 2023-06-25
 * description    :
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutPlayer {
    private Long id;
    private Long playerId;
    private String reason;

    private Timestamp createdAt;
}
