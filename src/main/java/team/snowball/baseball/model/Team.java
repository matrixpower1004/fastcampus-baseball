package team.snowball.baseball.model;

import lombok.*;
import java.sql.Timestamp;

/**
 * author         : Yongwon Kim
 * date           : 2023-06-26
 * description    :
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {

    private Long id;
    private Integer stadiumId;
    private String name;
    private Timestamp createdA;

}
