package team.snowball.baseball.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.Date;

/**
 * author         : Jason Lee
 * date           : 2023-06-27
 * description    :
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutPlayerRespDto {
    private Long id;
    private String name;
    private String position;
    private String reason;
    private Timestamp outDate;

    @Override
    public String toString() {
        return "OutPlayerRespDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", reason='" + reason + '\'' +
                ", outDate=" + outDate +
                '}';
    }
}
