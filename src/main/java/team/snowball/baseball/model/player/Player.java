package team.snowball.baseball.model.player;

import lombok.*;

import java.sql.Timestamp;

/**
 * author         : Jason Lee
 * date           : 2023-06-25
 * description    :
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {
    private Long id;
    private Long teamId;
    private String name;
    private String position;

    private Timestamp createdAt;
}
