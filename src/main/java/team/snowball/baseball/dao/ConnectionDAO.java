package team.snowball.baseball.dao;

import team.snowball.baseball.handler.DatabaseException;

import java.sql.Connection;

/**
 * author         : Jason Lee
 * date           : 2023-06-25
 * description    :
 */
public class ConnectionDAO {
    /**
     *  MySQL DB 연결 방법 설명
     *  1. 주소는 local PC 에서 하신다면 변경할 필요 없습니다.
     *  2. id, password 는 각자의 MySQL 설정에 맞게 수정해주세요.
     */
    public static String connectionTest() {

        Connection connection = null;

        try {
            String url = "jdbc:mysql://localhost:3306/baseball?serverTimezone=Asia/Seoul";
            connection = SnowballDBManager.connection(url, "matrixpower", "forCe@9348#");
            return "DB 연결 성공!";
        } catch (Exception e) {
            throw new DatabaseException();
        } finally { // 실패하면 무조건 연결 끊어지게 구현
            SnowballDBManager.disconnect(connection, null, null);
        }

    }
}
