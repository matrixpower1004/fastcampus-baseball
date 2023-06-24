package team.snowball.baseball.dao;

import team.snowball.baseball.handler.DatabaseException;

import java.sql.*;

/**
 * author         : Jason Lee
 * date           : 2023-06-25
 * description    :
 */
public class SnowballDBManager {
    public static Connection connection(String url, String id, String pw) {
        try {
            return DriverManager.getConnection(url, id, pw);
        } catch (SQLException e) {
            throw new DatabaseException();
        }
    }

    public static void disconnect(Connection connection, PreparedStatement pstmt, ResultSet rs) {
        try { rs.close(); } catch (Exception e) { }
        try { pstmt.close(); } catch (Exception e) { }
        try { connection.close(); } catch (Exception e) { }
    }
}
