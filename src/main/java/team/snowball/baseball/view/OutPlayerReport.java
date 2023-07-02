package team.snowball.baseball.view;

import team.snowball.baseball.dto.OutPlayerRespDto;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static team.snowball.baseball.view.Report.*;

/**
 * author         : Jason Lee
 * date           : 2023-07-02
 * description    :
 */
public class OutPlayerReport {

    public static void showOutPlayer(List<OutPlayerRespDto> outPlayerList) {
        StringBuilder sb = new StringBuilder();
        sb.append("---------------------------------------------------\n");
        sb.append(String.format("%s%s%s%s%s\n",
                formatter("ID", 6),
                formatter("name", 10),
                formatter("position", 10),
                formatter("reason", 8),
                formatter("outDate", 12)));
        sb.append("---------------------------------------------------\n");

        String dateStr = "";
        for (OutPlayerRespDto list : outPlayerList) {
            if (list.getOutDate() != null) {
                dateStr = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        .withZone(ZoneId.systemDefault())
                        .format(list.getOutDate().toInstant());
            } else {
                dateStr = "";
            }
            sb.append(String.format("%6d%s%s%s%s\n", list.getId(),
                    formatter(list.getName(), 10),
                    formatter(list.getPosition(), 10),
                    formatter((list.getReason() != null) ? list.getReason() : "", 8),
                    formatter(dateStr, 12)));
        }
        sb.append("---------------------------------------------------\n");
        System.out.println(sb);
    }
}
