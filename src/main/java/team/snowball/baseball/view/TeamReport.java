package team.snowball.baseball.view;

import team.snowball.baseball.model.team.Team;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TeamReport {

    public static void showTeamList(List<Team> teamList) {
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------------------------------\n");
        sb.append(String.format("%s%s%ss%s\n",
                Report.convert("id", 6),
                Report.convert("StadiumId", 13),
                Report.convert("name", 15),
                Report.convert("createdAt", 12)));
        sb.append("-------------------------------------------\n");
        for (Team list : teamList) {
            String dateStr = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    .withZone(ZoneId.systemDefault())
                    .format(list.getCreatedAt().toInstant());
            sb.append(String.format("%6d%13d%s%s\n",
                    list.getId(),
                    list.getStadiumId(),
                    Report.convert(list.getName(), 15),
                    Report.convert(dateStr, 12)));
        }
        sb.append("-------------------------------------------\n");
        System.out.println(sb);
    }


}
