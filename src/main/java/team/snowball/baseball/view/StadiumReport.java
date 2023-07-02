package team.snowball.baseball.view;

import team.snowball.baseball.model.stadium.Stadium;

import java.util.List;

import static team.snowball.baseball.view.Report.dateFormatter;

public class StadiumReport {

    public static void showStadiumList(List<Stadium> stadiumList) {
        StringBuilder sb = new StringBuilder();
        sb.append("-----------------------------------------------------\n");
        sb.append(String.format("%s%s%s\n",
                Report.formatter("id", 6),
                Report.formatter("name", 24),
                Report.formatter("createdAt", 18)));
        sb.append("-----------------------------------------------------\n");
        for (Stadium list : stadiumList) {
            String dateStr = dateFormatter.apply(list.getCreatedAt());
            sb.append(String.format("%6d%s%s\n",
                    list.getId(),
                    Report.formatter(list.getName(), 24),
                    Report.formatter(dateStr, 18)));
        }
        sb.append("-----------------------------------------------------\n");
        System.out.println(sb);
    }

    public static void showStadium(Stadium stadium) {
        StringBuilder sb = new StringBuilder();
        sb.append("-----------------------------------------------------\n");
        sb.append(String.format("%s%s%s\n",
                Report.formatter("id", 6),
                Report.formatter("name", 24),
                Report.formatter("createdAt", 18)));
        sb.append("-----------------------------------------------------\n");
        String dateStr = dateFormatter.apply(stadium.getCreatedAt());
        sb.append(String.format("%6d%s%s\n",
                stadium.getId(),
                Report.formatter(stadium.getName(), 24),
                Report.formatter(dateStr, 18)));
        sb.append("-----------------------------------------------------\n");
        System.out.println(sb);
    }
}
