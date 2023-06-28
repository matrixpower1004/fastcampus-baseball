package team.snowball.baseball.model.team;

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
    private Timestamp createdAt;

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", stadiumId=" + stadiumId +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
