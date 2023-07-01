package team.snowball.baseball.dao;

import team.snowball.baseball.handler.DatabaseException;
import team.snowball.baseball.model.player.Player;
import team.snowball.baseball.model.player.PlayerRepository;
import team.snowball.baseball.model.team.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static team.snowball.baseball.code.ErrorMessage.*;

/**
 * author         : Jason Lee
 * date           : 2023-06-27
 * description    :
 */
public class PlayerDao implements PlayerRepository {

    private static PlayerDao playerDao;
    private static final Connection CONNECTION = SnowballDBManager.getConnection();

    private PlayerDao() {
    }

    public static PlayerDao getInstance() {
        if (playerDao == null) {
            playerDao = new PlayerDao();
        }
        return playerDao;
    }

    @Override
    public int insert(Player player) {
        PreparedStatement pstmt = null;
        int result = 0;
        try {
            CONNECTION.setAutoCommit(false);

            String sql = "INSERT INTO player(team_id, name, position, created_at) VALUES (?, ?, ?, now())";
            pstmt = CONNECTION.prepareStatement(sql);
            pstmt.setLong(1, player.getTeamId());
            pstmt.setString(2, player.getName());
            pstmt.setString(3, player.getPosition());

            result = pstmt.executeUpdate();

            if (result == 1) {
                CONNECTION.commit();
                return result;
            }

            CONNECTION.rollback();
            return result;

        } catch (Exception e) {
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                System.out.println(e.getMessage());
                throw new DatabaseException();
            }
        }

        return result;
    }

    @Override
    public List<Player> findByTeamId(Long id) {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        List<Player> playerList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM player WHERE team_id = ?";
            pstmt = CONNECTION.prepareStatement(sql);
            pstmt.setLong(1, id);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                Player player = Player.builder()
                        .id(resultSet.getLong("id"))
                        .teamId(resultSet.getLong("team_id"))
                        .name(resultSet.getString("name"))
                        .position(resultSet.getString("position"))
                        .createdAt(resultSet.getTimestamp("created_at"))
                        .build();
                playerList.add(player);
            }

            return playerList;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new DatabaseException();
        }
    }

    @Override
    public int delete(Long id) {
        PreparedStatement pstmt = null;
        int result = 0;
        try {
            CONNECTION.setAutoCommit(false);

            String sql = "delete from player where id=?";
            pstmt = CONNECTION.prepareStatement(sql);
            pstmt.setLong(1, id);

            result = pstmt.executeUpdate();

            if (result == 1) {
                CONNECTION.commit();
                return result;
            }

            CONNECTION.rollback();
            return result;

        } catch (Exception e) {
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                System.out.println(e.getMessage());
                throw new DatabaseException(ERR_MSG_FAILED_TO_DELETE.getErrorMessage());
            }
        }
        return result;
    }

    @Override
    public void findLineByPosition() {

        // 2번에 걸쳐서 작업해 보자. 첫 작업에서 team_id와 team_name을 매핑시킨다.
        // 1. db에서 team_name을 가져온다.
        List<String> teamNameist = findTeamName();
        System.out.println(teamNameist);

        // 2차 작업
        String sql = "CALL POSITION_PIVOT()";
        try (PreparedStatement pstmt = CONNECTION.prepareStatement(sql)) {
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    System.out.print(resultSet.getString("position") + "\t");
                    for (String teamName : teamNameist) {
                        System.out.print(resultSet.getString(teamName) + "\t");
                    }
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new DatabaseException(ERR_MSG_FAILED_TO_DELETE.getErrorMessage());
        }

    }

    private List<String> findTeamName() {
        String sql = "select name from team order by id";
        List<String> teamList = new ArrayList<>();
        try (PreparedStatement pstmt = CONNECTION.prepareStatement(sql)) {
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    String teamName = resultSet.getString("name");
                    teamList.add(teamName);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new DatabaseException(ERR_MSG_FAILED_TO_FIND_TEAM_NAME.getErrorMessage());
        }
        return teamList;
    }

}// end of class PlayerDao
