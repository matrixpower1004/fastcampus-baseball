package team.snowball.baseball.dao;

import team.snowball.baseball.handler.DatabaseException;
import team.snowball.baseball.model.team.Team;
import team.snowball.baseball.model.team.TeamRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static team.snowball.baseball.code.ConsoleMessage.MSG_SUCCESS_TO_DELETE;
import static team.snowball.baseball.code.ConsoleMessage.MSG_SUCCESS_TO_REGISTER;
import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_FAILED_TO_DELETE;
import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_FAILED_TO_REGISTER;

/**
 * author         : Yongwon Kim
 * date           : 2023-06-28
 * description    :
 */
public class TeamDao implements TeamRepository {

    private Connection connection;

    public TeamDao() {
        connection = SnowballDBManager.getConnection();
    }

    // 팀 등록
    @Override
    public int insert(Team team) {
        PreparedStatement pstmt = null;
        int result = 0;
        try {
            connection.setAutoCommit(false);

            String query = "INSERT INTO team(stadium_id, name, created_at) VALUES (?, ?, now())";
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, team.getStadiumId());
            pstmt.setString(2, team.getName());

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

    // 팀 전체 조회
    @Override
    public List<Team> findAllTeams() {
        List<Team> teamList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM team";
            pstmt = connection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                Team team = Team.builder()
                        .id(resultSet.getLong("id"))
                        .stadiumId(resultSet.getInt("stadium_id"))
                        .name(resultSet.getString("name"))
                        .createdAt(resultSet.getTimestamp("created_at"))
                        .build();
                teamList.add(team);
            }

            return teamList;

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            SnowballDBManager.disconnect(connection, pstmt, resultSet);
        }

    }

    // 팀 삭제
    @Override
    public int delete(int id) {
        PreparedStatement pstmt = null;
        int result = 0;
        try {
            connection.setAutoCommit(false);

            String query = "DELETE FROM team WHERE id=?";
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);

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


    // TODO: 업데이트 메세지 논의 후 추가 예정
    // 팀 업데이트
    @Override
    public int update(Team team) {
        PreparedStatement pstmt = null;
        int result = 0;
        try {
            connection.setAutoCommit(false);

            String query = "UPDATE team SET name=? WHERE id=?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, team.getName());
            pstmt.setLong(2, team.getId());

            result = pstmt.executeUpdate();

            if (result == 1) {
                connection.commit();
                System.out.println("업데이트 성공");
            } else {
                connection.rollback();
                System.out.println("업데이트 실패");
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
