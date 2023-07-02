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
import static team.snowball.baseball.code.ConsoleMessage.MSG_SUCCESS_TO_UPDATE;
import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_FAILED_TO_DELETE;
import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_FAILED_TO_UPDATE;

/**
 * author         : Yongwon Kim
 * date           : 2023-06-28
 * description    :
 */
public class TeamDao implements TeamRepository {

    private static final Connection connection = SnowballDBManager.getConnection();;

    private static TeamDao teamDao;
    private TeamDao() {
    }

    public static TeamDao getInstance() {
        if (teamDao == null) {
            teamDao = new TeamDao();
        }
        return teamDao;
    }

    // 팀 등록
    @Override
    public int insert(Team team) {
        int result = 0;
        try {
            connection.setAutoCommit(false);

            int nameDuplicateCount = nameDuplicate(team);
            if (nameDuplicateCount > 0) {
                return -1;
            }

            int idDuplicateCount = idDuplicate(team);
            if (idDuplicateCount > 0) {
                return -2;
            }

            String query = "INSERT INTO team(stadium_id, name, created_at) VALUES (?, ?, now())";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setInt(1, team.getStadiumId());
                pstmt.setString(2, team.getName());
                result = pstmt.executeUpdate();

                if (result == 1) {
                    connection.commit();
                    return result;
                }

                connection.rollback();
                return result;
            }

        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println(e.getMessage());
                throw new DatabaseException(ERR_MSG_FAILED_TO_DELETE.getErrorMessage());
            }
            System.out.println(e.getMessage());
            throw new DatabaseException();
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

        } catch (Exception e) {
            throw new DatabaseException();
        }
    }

    // 팀 삭제
    @Override
    public int delete(Long id) {

        int result = 0;
        try {
            connection.setAutoCommit(false);

            String query = "DELETE FROM team WHERE id=?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
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
            }

        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println(e.getMessage());
                throw new DatabaseException(ERR_MSG_FAILED_TO_DELETE.getErrorMessage());
            }
            System.out.println(e.getMessage());
            throw new DatabaseException();
        }
    }

    // 팀 업데이트
    @Override
    public int update(Team team) {
        int result = 0;
        try {
            connection.setAutoCommit(false);

            int nameDuplicateCount = nameDuplicate(team);
            if (nameDuplicateCount > 0) {
                return -1;
            }

            int idDuplicateCount = idDuplicate(team);
            if (idDuplicateCount > 0) {
                return -2;
            }

            String query = "UPDATE team SET name=? WHERE id=?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, team.getName());
                pstmt.setLong(2, team.getId());

                result = pstmt.executeUpdate();

                if (result == 1) {
                    connection.commit();
                    System.out.println(MSG_SUCCESS_TO_UPDATE.getMessage());
                } else {
                    connection.rollback();
                    System.out.println(ERR_MSG_FAILED_TO_UPDATE.getErrorMessage());
                }
            }

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DatabaseException(ex.getMessage());
            }
            throw new DatabaseException(e.getMessage());
        }
        return result;
    }

    public int nameDuplicate(Team team) {
        String query = "SELECT COUNT(*) FROM team WHERE name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, team.getName());
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DatabaseException();
        }
        return -1;
    }

    public int idDuplicate(Team team) {
        String query = "SELECT COUNT(*) FROM team WHERE stadium_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, team.getStadiumId());
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DatabaseException();
        }
        return -2;
    }


}
