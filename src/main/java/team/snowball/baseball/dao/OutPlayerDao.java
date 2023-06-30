package team.snowball.baseball.dao;

import team.snowball.baseball.dto.OutPlayerRespDto;
import team.snowball.baseball.handler.DatabaseException;
import team.snowball.baseball.model.player.OutPlayer;
import team.snowball.baseball.model.player.OutPlayerRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static team.snowball.baseball.code.ErrorMessage.*;

/**
 * author         : Jason Lee
 * date           : 2023-06-27
 * description    :
 */
public class OutPlayerDao implements OutPlayerRepository {

    private static OutPlayerDao outPlayerDao;
    private static final Connection connection = SnowballDBManager.getConnection();
    private OutPlayerDao() {
    }

    public static OutPlayerDao getInstance() {
        if (outPlayerDao == null) {
            outPlayerDao = new OutPlayerDao();
        }
        return outPlayerDao;
    }

    @Override
    public int insert(OutPlayer outPlayer) {
        int result = 0;
        PreparedStatement pstmt = null;

        /**
         *  퇴출 선수 등록은 2단계로 이루어진다.
         *  1. out_player 테이블에 퇴출 선수 데이터를 삽입한다.
         *  2. 해당 선수의 id를 통해 player 테이블의 team_id를 null로 update
         */
        try {
            // 트랜잭션 시작
            connection.setAutoCommit(false);

            // 1. 퇴출 선수 등륵을 진행
            String sql = "INSERT INTO out_player(player_id, reason, created_at) VALUES (?, ?, now())";
            pstmt = connection.prepareStatement(sql);

            // player table update에도 사용해야 하니 변수로 선언하자
            final Long playerId = outPlayer.getPlayerId();

            pstmt.setLong(1, playerId);
            pstmt.setString(2, outPlayer.getReason());

            // insert 결과가 1건이 아니라면 실패.
            if (pstmt.executeUpdate() != 1) {
                connection.rollback();
                return result;
            }

            // 2. player테이블의 해당 선수 teamId를 null로 update.
            sql = "update player set team_id=null where id=?";

            pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, playerId);

            result = pstmt.executeUpdate();

            if (result == 1) {
                // 2단계 까지 성공했다면 commit.
                connection.commit();
                return result;
            }

            connection.rollback();
            return result;

        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println(e.getMessage());
                throw new DatabaseException(ERR_MSG_FAILED_TO_REGISTER.getErrorMessage());
            }
        }
        return result;
    }

    @Override
    public List<OutPlayerRespDto> findAll() {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String sql = "SELECT p.id, p.name, p.position, op.reason, op.created_at "
                    + "FROM player p "
                    + "LEFT OUTER JOIN out_player op "
                    + "ON p.id = op.player_id";
            pstmt = connection.prepareStatement(sql);
            resultSet = pstmt.executeQuery();

            List<OutPlayerRespDto> outPlayerList = new ArrayList<>();

            while (resultSet.next()) {
                OutPlayerRespDto outPlayerRespDto = OutPlayerRespDto.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .position(resultSet.getString("position"))
                        .reason(resultSet.getString("reason"))
                        .outDate(resultSet.getTimestamp("created_at"))
                        .build();
                outPlayerList.add(outPlayerRespDto);
            }

            if (outPlayerList == null) {
                throw new DatabaseException();
            }

            return outPlayerList;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new DatabaseException(ERR_MSG_FAILED_TO_FIND.getErrorMessage());
        }
    } // end of method findAll

} // end of class OutPlayerDao
