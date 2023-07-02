package team.snowball.baseball.view;

import team.snowball.baseball.model.team.Team;

import java.util.List;

import static team.snowball.baseball.view.Report.dateFormatter;

public class TeamReport {

    public static void showTeamList(List<Team> teamList) {
        StringBuilder sb = new StringBuilder();
        sb.append("----------------------------------------------------------\n");
        sb.append(String.format("%s%s%ss%s\n",
                Report.formatter("id", 6),
                Report.formatter("StadiumId", 12),
                Report.formatter("name", 16),
                Report.formatter("createdAt", 18)));
        sb.append("----------------------------------------------------------\n");
        for (Team list : teamList) {
            String dateStr = dateFormatter.apply(list.getCreatedAt());
            sb.append(String.format("%6d%12d%s%s\n",
                    list.getId(),
                    list.getStadiumId(),
                    Report.formatter(list.getName(), 16),
                    Report.formatter(dateStr, 18)));
        }
        sb.append("----------------------------------------------------------\n");
        System.out.println(sb);
    }
}
