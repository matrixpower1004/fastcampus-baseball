package team.snowball.baseball.dao;

import team.snowball.baseball.handler.DatabaseException;
import team.snowball.baseball.handler.InvalidIdException;
import team.snowball.baseball.model.team.Team;
import team.snowball.baseball.model.team.TeamRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static team.snowball.baseball.code.ErrorMessage.*;

/**
 * author         : Yongwon Kim
 * date           : 2023-06-28
 * description    :
 */
public class TeamDao implements TeamRepository {

    private static final Connection connection = SnowballDBManager.getConnection();
    ;

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
    public int save(Team team) {
        int result = 0;
        try {
            connection.setAutoCommit(false);

            // stadium_id 중복 검사
            int idDuplicateCount = idDupeStadiumId(team.getStadiumId());
            if (idDuplicateCount > 0) {
                throw new InvalidIdException(ERR_MSG_DUPLICATE_ID.getErrorMessage());
            }

            String query = "INSERT INTO team(stadium_id, name, created_at) VALUES (?, ?, now())";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setLong(1, team.getStadiumId());
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
    public List<Team> findAll() {
        List<Team> teamList = new ArrayList<>();
        String sql = "SELECT * FROM team";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    Team team = getTeam(resultSet);
                    teamList.add(team);
                }
                return teamList;
            } catch (SQLException e) {
                throw new DatabaseException(ERR_MSG_FAILED_TO_FIND.getErrorMessage());
            }
        } catch (SQLException e) {
            throw new DatabaseException(ERR_MSG_FAILED_TO_FIND.getErrorMessage());
        }
    }

    private Team getTeam(ResultSet resultSet) {
        try {
            return Team.builder()
                    .id(resultSet.getLong("id"))
                    .stadiumId(resultSet.getLong("stadium_id"))
                    .name(resultSet.getString("name"))
                    .createdAt(resultSet.getTimestamp("created_at"))
                    .build();
        } catch (SQLException e) {
            throw new DatabaseException(ERR_MSG_FAILED_TO_FIND.getErrorMessage());
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
                    connection.commit();
                    return result;
                }

                connection.rollback();
                throw new DatabaseException(ERR_MSG_FAILED_TO_DELETE.getErrorMessage());
            }

        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DatabaseException(ERR_MSG_FAILED_TO_DELETE.getErrorMessage());
            }
            throw new DatabaseException(ERR_MSG_FAILED_TO_DELETE.getErrorMessage());
        }
    }

    // 팀 업데이트
    @Override
    public int update(Team team) {
        int result = 0;
        try {
            connection.setAutoCommit(false);

            String query = "UPDATE team SET name=? WHERE id=?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, team.getName());
                pstmt.setLong(2, team.getId());

                result = pstmt.executeUpdate();

                if (result == 1) {
                    connection.commit();
                    return result;
                }

                connection.rollback();
                throw new DatabaseException(ERR_MSG_FAILED_TO_UPDATE.getErrorMessage());
            }

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DatabaseException(ERR_MSG_FAILED_TO_UPDATE.getErrorMessage());
            }
        }
        return result;
    }

    public int idDupeStadiumId(Long stadiumId) {
        String query = "SELECT COUNT(*) FROM team WHERE stadium_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, stadiumId);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DatabaseException();
        }
    }

}
