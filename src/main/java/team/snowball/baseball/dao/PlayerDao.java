package team.snowball.baseball.dao;

import team.snowball.baseball.code.Position;
import team.snowball.baseball.handler.DatabaseException;
import team.snowball.baseball.model.player.Player;
import team.snowball.baseball.model.player.PlayerRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_FAILED_TO_DELETE;
import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_FAILED_TO_FIND;

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
        //PreparedStatement pstmt = null;
        int result = 0;
        try {
            CONNECTION.setAutoCommit(false);

            //중목 선수이름 체크
            String checkQueryName = "SELECT COUNT(*) FROM player WHERE team_id = ? AND name = ?";
            try (PreparedStatement pstmt = CONNECTION.prepareStatement(checkQueryName)) {
                pstmt.setInt(1, player.getTeamId());
                pstmt.setString(2, player.getName());
                try (ResultSet resultSet = pstmt.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        if (count > 0) {
                            System.out.println(ERR_MSG_FAILED_TO_FIND.getErrorMessage()+ "이름");
                            return 0;
                        }
                    }
                }
            }

            //중목 포지션 체크
            String checkQueryPosition = "SELECT COUNT(*) FROM player WHERE team_id = ? AND position = ?";
            try (PreparedStatement pstmt = CONNECTION.prepareStatement(checkQueryPosition)) {
                pstmt.setInt(1, player.getTeamId());
                pstmt.setString(2, player.getPosition());
                try (ResultSet resultSet = pstmt.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        if (count > 0) {
                            System.out.println(ERR_MSG_FAILED_TO_FIND.getErrorMessage() + "포지션");
                            return 0;
                        }
                    }
                }
            }

            String sql = "INSERT INTO player(team_id, name, position, created_at) VALUES (?, ?, ?, now())";
            try (PreparedStatement pstmt = CONNECTION.prepareStatement(sql)) {
                pstmt.setInt(1, player.getTeamId());
                pstmt.setString(2, player.getName());
                pstmt.setString(3, player.getPosition());

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
                throw new DatabaseException();
            }
        }

        return result;
    }

    @Override
    public List<Player> findByTeamId(int id) {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        List<Player> playerList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM player WHERE team_id = ?";
            pstmt = CONNECTION.prepareStatement(sql);
            pstmt.setInt(1, id);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                Player player = Player.builder()
                        .id(resultSet.getLong("id"))
                        .teamId(resultSet.getInt("team_id"))
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

        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
//        List<PositionRespDto> positionByList = new ArrayList<>();
        Map<Integer, String> teamMap = new HashMap<>();

        try {
            // 2번에 걸쳐서 작업해 보자. 첫 작업에서 team_id와 team_name을 매핑시킨다.
            String sql1 = "select id, " +
                    "SUBSTRING_INDEX(SUBSTRING_INDEX(name, \" \", 2), \" \", -1)" +
                    "from team";
            pstmt = CONNECTION.prepareStatement(sql1);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString(2);
                teamMap.put(id, name);
            }

            // 여기까진 통과
            System.out.println(teamMap);

            // 2차 작업
            String sql = "select p.position, " +
                    "max(case when p.team_id = ? then p.name else '' end) as ? " +
                    "FROM player p " +
                    "LEFT JOIN team t ON p.team_id = t.id " +
                    "GROUP BY p.position " +
                    "ORDER BY p.position";

            pstmt = CONNECTION.prepareStatement(sql);

            int count = 1;
            for (Map.Entry<Integer, String> map : teamMap.entrySet()) {
                pstmt.setInt(1, map.getKey());
                System.out.println("put : " + map.getKey());
                String name = "team" + count;
                pstmt.setString(2, name);
                count++;
            }

            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getString("position"));
                System.out.println(resultSet.getString("team3"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new DatabaseException(ERR_MSG_FAILED_TO_DELETE.getErrorMessage());
        }

    } // end of class PlayerDao
}
