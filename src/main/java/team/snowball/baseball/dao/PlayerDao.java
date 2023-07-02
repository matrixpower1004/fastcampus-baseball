package team.snowball.baseball.dao;

import team.snowball.baseball.dto.PositionRespDto;
import team.snowball.baseball.handler.DatabaseException;
import team.snowball.baseball.model.player.Player;
import team.snowball.baseball.model.player.PlayerRepository;

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
        int result = 0;
        String sql = "delete from player where id=?";
        try (PreparedStatement pstmt = CONNECTION.prepareStatement(sql)){
            CONNECTION.setAutoCommit(false);

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
    public PositionRespDto findLineByPosition() {

        // 2번에 걸쳐서 작업해 보자.
        // 1. 먼저 DB에서 현재 등록되어 있는 팀의 이름을 가져온다.
        List<String> teamNameList = findTeamName();
        if (teamNameList.size() == 0 || teamNameList == null) {
            throw new DatabaseException(ERR_MSG_FAILED_TO_FIND.getErrorMessage());
        }

        // 2차 작업 - DB의 프로시저를 호출
        String sql = "CALL POSITION_PIVOT()";

        LinkedHashMap<String, List<String>> playerByPositionMap = new LinkedHashMap<>();

        try (PreparedStatement pstmt = CONNECTION.prepareStatement(sql)) {
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    String position = resultSet.getString("position");
                    List<String> playerNames = new ArrayList<>();

                    for (String teamName : teamNameList) {
                        String playerName = resultSet.getString(teamName);
                        playerNames.add(playerName);
                    }

                    playerByPositionMap.put(position, playerNames);
                }
                return PositionRespDto.builder()
                        .teamNames(teamNameList)
                        .playersByPosition(playerByPositionMap)
                        .build();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new DatabaseException(ERR_MSG_FAILED_TO_FIND.getErrorMessage());
        }
    }

    @Override
    public List<Player> findAll() {
        String sql = "select * from player order by id";
        List<Player> playerList = new ArrayList<>();
        try (PreparedStatement pstmt = CONNECTION.prepareStatement(sql)) {
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    Player player = makePlayer(resultSet);
                    playerList.add(player);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DatabaseException(ERR_MSG_FAILED_TO_FIND.getErrorMessage());
        }
        return playerList;
    }

    @Override
    public Player findById(Long id) {
        String sql = "select * from player where id = ?;";
        try (PreparedStatement pstmt = CONNECTION.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                return makePlayer(resultSet);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DatabaseException(ERR_MSG_FAILED_TO_FIND_BY_ID.getErrorMessage());
        }
    }

    private Player makePlayer(ResultSet resultSet) {
        try {
            return Player.builder()
                    .id(resultSet.getLong("id"))
                    .teamId(resultSet.getLong("team_id"))
                    .name(resultSet.getString("name"))
                    .position(resultSet.getString("position"))
                    .createdAt(resultSet.getTimestamp("created_at"))
                    .build();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DatabaseException();
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DatabaseException(ERR_MSG_FAILED_TO_FIND_TEAM_NAME.getErrorMessage());
        }
        return teamList;
    }

}// end of class PlayerDao
