package team.snowball.baseball.view;

import team.snowball.baseball.handler.InternalServerErrorException;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import static team.snowball.baseball.code.ConsoleMessage.*;
import static team.snowball.baseball.code.ErrorMessage.*;

/**
 * author         : Jason Lee
 * date           : 2023-06-29
 * description    :
 */
public class Report {

    // 전각문자(한글)의 개수만큼 문자열의 길이을 빼주는 메서드
    public static String formatter(String word, int size) {
        String format = String.format("%%%ds", size - getUnicodeCount(word));
        return String.format(format, word);
    }

    // Unicode(한글)의 개수를 구하는 메서드
    private static int getUnicodeCount(String it) {
        int count = 0;
        try {
            for (int i = 0; i < it.length(); i++) {
                if (Character.getType(it.charAt(i)) == Character.OTHER_LETTER) {
                    count++;
                }
            }
            return count;
        } catch (IndexOutOfBoundsException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    public static Function<Timestamp, String> dateFormatter = (timestamp) -> {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd")
                .withZone(ZoneId.systemDefault())
                .format(timestamp.toInstant());
    };

    public static Consumer<Integer> showSaveResult = (result) -> {
        System.out.println(result == 1 ? MSG_SUCCESS_TO_REGISTER.getMessage() :
                ERR_MSG_FAILED_TO_REGISTER.getErrorMessage());
    };

    public static Consumer<Integer> showDeleteResult = (result) -> {
        System.out.println(result == 1 ? MSG_SUCCESS_TO_DELETE.getMessage() :
                ERR_MSG_FAILED_TO_DELETE.getErrorMessage());
    };

    public static Consumer<Integer> showUpdateResult = (result) -> {
        System.out.println(result == 1 ? MSG_SUCCESS_TO_UPDATE.getMessage() :
                ERR_MSG_FAILED_TO_UPDATE.getErrorMessage());
    };

    public static Consumer<Integer> showIsDuplicate = (result) -> {
        System.out.println(result == 1 ? MSG_SUCCESS_TO_REGISTER.getMessage() :
                ERR_MSG_DUPLICATE_ID.getErrorMessage());
    };

}
