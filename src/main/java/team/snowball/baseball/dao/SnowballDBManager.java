package team.snowball.baseball.dao;

import team.snowball.baseball.handler.DatabaseException;

import java.sql.*;

/**
 * author         : Jason Lee
 * date           : 2023-06-25
 * description    :
 */
public class SnowballDBManager {
    public static final Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/baseball?serverTimezone=Asia/Seoul";
        String id = "matrixpower";
        String pwd = "forCe@9348#";
        try {
            return DriverManager.getConnection(url, id, pwd);
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
