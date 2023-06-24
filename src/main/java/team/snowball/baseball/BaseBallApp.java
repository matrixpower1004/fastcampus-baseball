package team.snowball.baseball;

import team.snowball.baseball.dao.ConnectionDAO;

/**
 * author         : Jason Lee
 * date           : 2023-06-25
 * description    :
 */
public class BaseBallApp {

    private static BaseBallApp baseBallApp;

    public BaseBallApp() {
        baseBallApp = this;
    }

    public static BaseBallApp getInstance() {
        if (baseBallApp == null) {
            baseBallApp = new BaseBallApp();
        }
        return baseBallApp;
    }

    public BaseBallApp test() {
        String result = ConnectionDAO.connectionTest();
        System.out.println(result);

        return this;
    }

}
