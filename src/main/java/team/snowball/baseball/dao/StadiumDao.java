package team.snowball.baseball.dao;

import team.snowball.baseball.handler.DatabaseException;
import team.snowball.baseball.model.stadium.Stadium;
import team.snowball.baseball.model.stadium.StadiumRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static team.snowball.baseball.code.ConsoleMessage.*;
import static team.snowball.baseball.code.ErrorMessage.*;

/**
 * author         : Yongwon Kim
 * date           : 2023-06-28
 * description    :
 */
public class StadiumDao implements StadiumRepository {

    private Connection connection;

    public StadiumDao() {
        connection = SnowballDBManager.getConnection();
    }

    // 야구장 등록
    @Override
    public int insert(Stadium stadium) {
        PreparedStatement pstmt = null;
        int result = 0;
        try {
            connection.setAutoCommit(false);

            String query = "INSERT INTO stadium(name, created_at) VALUES (?, now())";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, stadium.getName());

            result = pstmt.executeUpdate();

            if (result == 1) {
                connection.commit();
                System.out.println(MSG_SUCCESS_TO_REGISTER.getMessage());
                return result;
            }

            connection.rollback();
            System.out.println(ERR_MSG_FAILED_TO_REGISTER.getErrorMessage());
            return result;

        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println(e.getMessage());
                throw new DatabaseException(ERR_MSG_FAILED_TO_DELETE.getErrorMessage());
            }
            System.out.println(e.getMessage());
            throw new DatabaseException();
        } finally {
            SnowballDBManager.disconnect(connection, pstmt, null);
        }

    }

    // 야구장 전체 조회
    @Override
    public List<Stadium> findAllStadiums() {
        List<Stadium> stadiumList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM stadium";
            pstmt = connection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                Stadium stadium = Stadium.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .createdAt(resultSet.getTimestamp("created_at"))
                        .build();
                stadiumList.add(stadium);
            }

            return stadiumList;

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            SnowballDBManager.disconnect(connection, pstmt, resultSet);
        }

    }

    // 삭제
    @Override
    public int delete(Long id) {
        PreparedStatement pstmt = null;
        int result = 0;
        try {
            connection.setAutoCommit(false);

            String query = "DELETE FROM stadium WHERE id=?";
            pstmt = connection.prepareStatement(query);
            pstmt.setLong(1, id);

            result = pstmt.executeUpdate();

            if (result == 1) {
                System.out.println(MSG_SUCCESS_TO_DELETE.getMessage());
                connection.commit();
                return result;
            }

            connection.rollback();
            System.out.println(ERR_MSG_FAILED_TO_DELETE.getErrorMessage());
            return result;

        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println(e.getMessage());
                throw new DatabaseException(ERR_MSG_FAILED_TO_DELETE.getErrorMessage());
            }
            System.out.println(e.getMessage());
            throw new DatabaseException();
        } finally {
            SnowballDBManager.disconnect(connection, pstmt, null);
        }
    }


    // 업데이트
    @Override
    public int update(Stadium stadium) {
        PreparedStatement pstmt = null;
        int result = 0;
        try {
            connection.setAutoCommit(false);

            String query = "UPDATE stadium SET name=? WHERE id=?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, stadium.getName());
            pstmt.setLong(2, stadium.getId());

            result = pstmt.executeUpdate();

            if (result == 1) {
                connection.commit();
                System.out.println(MSG_SUCCESS_TO_UPDATE.getMessage());
            } else {
                connection.rollback();
                System.out.println(ERR_MSG_FAILED_TO_UPDATE.getErrorMessage());
            }

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DatabaseException(ex.getMessage());
            }
            throw new DatabaseException(e.getMessage());

        } finally {
            SnowballDBManager.disconnect(connection, pstmt, null);
        }
        return result;

    }

}

