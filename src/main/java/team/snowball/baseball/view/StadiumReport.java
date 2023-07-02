package team.snowball.baseball.view;

import team.snowball.baseball.model.stadium.Stadium;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StadiumReport {

    public static void showStadiumList(List<Stadium> stadiumList) {
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------------------------------\n");
        sb.append(String.format("%s%s%s\n",
                Report.convert("id", 6),
                Report.convert("name", 20),
                Report.convert("createdAt", 12)));
        sb.append("-------------------------------------------\n");
        for (Stadium list : stadiumList) {
            String dateStr = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    .withZone(ZoneId.systemDefault())
                    .format(list.getCreatedAt().toInstant());
            sb.append(String.format("%6d%s%s\n",
                    list.getId(),
                    Report.convert(list.getName(), 20),
                    Report.convert(dateStr, 12)));
        }
        sb.append("-------------------------------------------\n");
        System.out.println(sb);
    }

}
