package team.snowball.baseball.view;

import team.snowball.baseball.dto.OutPlayerRespDto;
import team.snowball.baseball.model.player.Player;

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
        sb.append("---------------------------------------------------\n");
        sb.append(String.format("%s%s%s%s%s\n",
                convert("ID", 6),
                convert("name", 10),
                convert("position", 10),
                convert("reason", 8),
                convert("outDate", 12)));
        sb.append("---------------------------------------------------\n");
        for (OutPlayerRespDto list : outPlayerList) {
            String dateStr = "";
            if (list.getOutDate() != null) {
                dateStr = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        .withZone(ZoneId.systemDefault())
                        .format(list.getOutDate().toInstant());
            }
            sb.append(String.format("%6d%s%s%s%s\n", list.getId(),
                    convert(list.getName(), 10),
                    convert(list.getPosition(), 10),
                    convert((list.getReason() != null) ? list.getReason() : "", 8),
                    convert(dateStr, 12)));
        }
        sb.append("---------------------------------------------------\n");
        System.out.println(sb);
    }

    public static void showPlayerByTeam(List<Player> playerList) {
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------------------------------\n");
        sb.append(String.format("%s%s%s%s\n",
                convert("ID", 6),
                convert("name", 10),
                convert("position", 10),
                convert("등록일자", 12 )));
        sb.append("-------------------------------------------\n");
        for (Player list : playerList) {
            String dateStr = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    .withZone(ZoneId.systemDefault())
                    .format(list.getCreatedAt().toInstant());
            sb.append(String.format("%6d%s%s%s\n",
                    list.getId(),
                    convert(list.getName(), 10),
                    convert(list.getPosition(), 10),
                    convert(dateStr, 12)));
        }
        sb.append("-------------------------------------------\n");
        System.out.println(sb);
    }

    // 전각문자(한글)의 개수만큼 문자열의 길이을 빼주는 메서드
    private static String convert(String word, int size) {
        String formatter = String.format("%%%ds", size - getKorCnt(word));
        return String.format(formatter, word);
    }

    // Unicode(한글)의 개수를 구하는 메서드
    private static int getKorCnt(String it) {
        int cnt = 0;
        for (int i = 0; i < it.length(); i++) {
            if (Character.getType(it.charAt(i)) == Character.OTHER_LETTER) {
                cnt++;
            }
        }
        return cnt;
    }

}
