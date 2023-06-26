package team.snowball.baseball.view;

import team.snowball.baseball.handler.InputEndException;
import team.snowball.baseball.handler.InvalidInputException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static team.snowball.baseball.code.ConsoleMessage.MSG_GUIDE_END;

/**
 * author         : Jason Lee
 * date           : 2023-06-26
 * description    :
 */
public class InputQuery {

    private static InputQuery inputQuery;

    private InputQuery() {
    }

    public static InputQuery getInstance() {
        if (inputQuery == null) {
            inputQuery = new InputQuery();
        }
        return inputQuery;
    }

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public String nextLIne() {
        System.out.println(MSG_GUIDE_END.getMessage());
        String query = "";
        try {
            query = br.readLine();
            if (query.toUpperCase().equals("END")) {
                throw new InputEndException();
            }
        } catch (IOException | NullPointerException e) {
            throw new InvalidInputException();
        }
        return query;
    }

}