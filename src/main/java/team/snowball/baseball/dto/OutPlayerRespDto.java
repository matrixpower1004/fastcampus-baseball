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
@ToString
@Builder
public class OutPlayerRespDto {
    private Long id;
    private String name;
    private String position;
    private String reason;
    private Timestamp outDate;
}
