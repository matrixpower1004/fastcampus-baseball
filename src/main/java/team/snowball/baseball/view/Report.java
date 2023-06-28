package team.snowball.baseball.view;

import team.snowball.baseball.dto.OutPlayerRespDto;
import team.snowball.baseball.model.player.Player;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * author         : Jason Lee
 * date           : 2023-06-29
 * description    :
 */
public class Report {

    public static void showOutPlayer(List<OutPlayerRespDto> outPlayerList) {
        StringBuilder sb = new StringBuilder();
        System.out.println("===================================================================================");
        sb.append(String.format("|%-5s|%-7s|%-9s|%-10s|%-11s|\n", "ID", "name", "position", "reason", "outDate"));
        sb.append("===================================================================================\n");
        String dateStr = "";
        for (OutPlayerRespDto list : outPlayerList) {
            if (list.getOutDate() != null) {
//                dateStr = new SimpleDateFormat("yyyy-MM-dd").format(list.getOutDate());
                dateStr = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        .withZone(ZoneId.systemDefault())
                        .format(list.getOutDate().toInstant());
            }
            sb.append(String.format("|%5d|%-7s|%-9s|%-10s|%-11s|\n",
                    list.getId(), list.getName(), list.getPosition(),
                    (list.getReason() != null) ? list.getReason() : "" , dateStr));
            dateStr = "";
        }
        sb.append("===================================================================================\n");
        System.out.println(sb);
    }

    public static void showPlayerByTeam(List<Player> playerList) {

    }

}
