package team.snowball.baseball.view;

import team.snowball.baseball.dto.PositionRespDto;
import team.snowball.baseball.model.player.Player;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static team.snowball.baseball.view.Report.*;

/**
 * author         : Jason Lee
 * date           : 2023-07-02
 * description    :
 */
public class PlayerReport {

    public static void showPlayerByTeam(List<Player> playerList) {
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------------------------------\n");
        sb.append(String.format("%s%s%s%s\n",
                formatter("id", 6),
                formatter("name", 10),
                formatter("position", 10),
                formatter("createdAt", 12)));
        sb.append("-------------------------------------------\n");
        for (Player list : playerList) {
            String dateStr = dateFormatter.apply(list.getCreatedAt());
            sb.append(String.format("%6d%s%s%s\n",
                    list.getId(),
                    formatter(list.getName(), 10),
                    formatter(list.getPosition(), 10),
                    formatter(dateStr, 12)));
        }
        sb.append("-------------------------------------------\n");
        System.out.println(sb);
    }

    public static void showPlayersByPosition(PositionRespDto positionRespDto) {
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------------------------------\n");
        sb.append(String.format("%s", formatter("Position", 10)));
        for (String teamName : positionRespDto.getTeamNames()) {
            sb.append(String.format("%s", formatter(teamName, 10)));
        }
        sb.append("\n");
        sb.append("-------------------------------------------\n");
        for (String position : positionRespDto.getPlayersByPosition().keySet()) {
            sb.append(String.format("%s", formatter(position, 10)));
            List<String> playerNames = positionRespDto.getPlayersByPosition().get(position);
            for (String name : playerNames) {
                if (name.isEmpty()) {
                    sb.append(String.format("%s", formatter("", 10)));
                    // continue 를 빼먹어서 열이 안 맞는 것 때문에 얼마나 헤맸던지 ㅠㅠ...
                    continue;
                }
                sb.append(String.format("%s", formatter(name, 10)));
            }
            sb.append("\n");
        }
        sb.append("-------------------------------------------\n");
        System.out.println(sb);
    }

    public static void showPlayerAll(List<Player> playerList) {
        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------------------------------------------\n");
        sb.append(String.format("%s%s%s%s%s\n",
                formatter("id", 6),
                formatter("team_id", 10),
                formatter("name", 12),
                formatter("position", 14),
                formatter("createdAt", 16)));
        sb.append("--------------------------------------------------------------\n");
        for (Player list : playerList) {
            String dateStr = dateFormatter.apply(list.getCreatedAt());
            sb.append(String.format("%6d%10d%s%s%s\n",
                    list.getId(),
                    list.getTeamId(),
                    formatter(list.getName(), 12),
                    formatter(list.getPosition(), 14),
                    formatter(dateStr, 16)));
        }
        sb.append("-------------------------------------------------------------\n");
        System.out.println(sb);
    }

    public static void showPlayerOne(Player player) {
        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------------------------------------------\n");
        sb.append(String.format("%s%s%s%s%s\n",
                formatter("id", 6),
                formatter("team_id", 10),
                formatter("name", 12),
                formatter("position", 14),
                formatter("createdAt", 16)));
        sb.append("--------------------------------------------------------------\n");
        String dateStr = dateFormatter.apply(player.getCreatedAt());
        sb.append(String.format("%6d%10d%s%s%s\n",
                player.getId(),
                player.getTeamId(),
                formatter(player.getName(), 12),
                formatter(player.getPosition(), 14),
                formatter(dateStr, 16)));
        sb.append("-------------------------------------------------------------\n");
        System.out.println(sb);
    }
}
