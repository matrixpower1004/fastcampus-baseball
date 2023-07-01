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

    private static final Connection CONNECTION = SnowballDBManager.getConnection();;

    private static StadiumDao stadiumDao;
    private StadiumDao() {
    }

    public static StadiumDao getInstance() {
        if (stadiumDao == null) {
            stadiumDao = new StadiumDao();
        }
        return stadiumDao;
    }
    // 야구장 등록
    @Override
    public int insert(Stadium stadium) {
        int result = 0;
        try {
            CONNECTION.setAutoCommit(false);

            //야구장 이름 중복 체크
            String checkQuery = "SELECT COUNT(*) FROM stadium WHERE name = ?";
            try (PreparedStatement pstmt = CONNECTION.prepareStatement(checkQuery)) {
                pstmt.setString(1, stadium.getName());
                try (ResultSet resultSet = pstmt.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        if (count > 0) {
                            return 0;
                        }
                    }
                }
            }

            String Query = "INSERT INTO stadium(name, created_at) VALUES (?, now())";
            try (PreparedStatement pstmt = CONNECTION.prepareStatement(Query)) {
                pstmt.setString(1, stadium.getName());
                result = pstmt.executeUpdate();

                if (result == 1) {
                    CONNECTION.commit();
                    return result;
                }

                CONNECTION.rollback();
                return result;
            }
        } catch (Exception e) {
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                System.out.println(e.getMessage());
                throw new DatabaseException(ERR_MSG_FAILED_TO_DELETE.getErrorMessage());
            }
            System.out.println(e.getMessage());
            throw new DatabaseException();
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
            pstmt = CONNECTION.prepareStatement(query);
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

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new DatabaseException();
        }
    }

    // 삭제
    @Override
    public int delete(Long id) {
        PreparedStatement pstmt = null;
        int result = 0;
        try {
            CONNECTION.setAutoCommit(false);

            String query = "DELETE FROM stadium WHERE id=?";
            pstmt = CONNECTION.prepareStatement(query);
            pstmt.setLong(1, id);

            result = pstmt.executeUpdate();

            if (result == 1) {
                System.out.println(MSG_SUCCESS_TO_DELETE.getMessage());
                CONNECTION.commit();
                return result;
            }

            CONNECTION.rollback();
            System.out.println(ERR_MSG_FAILED_TO_DELETE.getErrorMessage());
            return result;

        } catch (Exception e) {
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                System.out.println(e.getMessage());
                throw new DatabaseException(ERR_MSG_FAILED_TO_DELETE.getErrorMessage());
            }
            System.out.println(e.getMessage());
            throw new DatabaseException();
        }
    }


    // 업데이트
    @Override
    public int update(Stadium stadium) {
        PreparedStatement pstmt = null;
        int result = 0;
        try {
            CONNECTION.setAutoCommit(false);

            String query = "UPDATE stadium SET name=? WHERE id=?";
            pstmt = CONNECTION.prepareStatement(query);
            pstmt.setString(1, stadium.getName());
            pstmt.setLong(2, stadium.getId());

            result = pstmt.executeUpdate();

            if (result == 1) {
                CONNECTION.commit();
                System.out.println(MSG_SUCCESS_TO_UPDATE.getMessage());
            } else {
                CONNECTION.rollback();
                System.out.println(ERR_MSG_FAILED_TO_UPDATE.getErrorMessage());
            }

        } catch (SQLException e) {
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                throw new DatabaseException(ex.getMessage());
            }
            throw new DatabaseException(e.getMessage());

        }
        return result;
    }

}
