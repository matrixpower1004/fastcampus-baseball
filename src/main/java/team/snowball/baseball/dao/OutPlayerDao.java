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
    private static final Connection CONNECTION = SnowballDBManager.getConnection();

    private OutPlayerDao() {
    }

    public static OutPlayerDao getInstance() {
        if (outPlayerDao == null) {
            outPlayerDao = new OutPlayerDao();
        }
        return outPlayerDao;
    }

    @Override
    public int save(OutPlayer outPlayer) {
        String sql = "INSERT INTO out_player(player_id, reason, created_at) VALUES (?, ?, now())";

        try (PreparedStatement pstmt = CONNECTION.prepareStatement(sql)) {
            pstmt.setLong(1, outPlayer.getPlayerId());
            pstmt.setString(2, outPlayer.getReason());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(ERR_MSG_FAILED_TO_UPDATE.getErrorMessage());
        }
    }

    @Override
    public List<OutPlayerRespDto> findAll() {
        String sql = "SELECT p.id, p.name, p.position, op.reason, op.created_at "
                + "FROM player p "
                + "LEFT OUTER JOIN out_player op "
                + "ON p.id = op.player_id";

        List<OutPlayerRespDto> outPlayerList = new ArrayList<>();

        try (PreparedStatement pstmt = CONNECTION.prepareStatement(sql)) {
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    OutPlayerRespDto outPlayerRespDto = getOutPlayerDto(resultSet);
                    outPlayerList.add(outPlayerRespDto);
                }
                return outPlayerList;

            } catch (SQLException e) {
                throw new DatabaseException(ERR_MSG_FAILED_TO_FIND.getErrorMessage());
            }
        } catch (Exception e) {
            throw new DatabaseException(ERR_MSG_FAILED_TO_FIND.getErrorMessage());
        }
    } // end of method findAll

    private OutPlayerRespDto getOutPlayerDto(ResultSet resultSet) {
        try {
            return OutPlayerRespDto.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .position(resultSet.getString("position"))
                    .reason(resultSet.getString("reason"))
                    .outDate(resultSet.getTimestamp("created_at"))
                    .build();
        } catch (SQLException e) {
            throw new DatabaseException(ERR_MSG_FAILED_TO_FIND.getErrorMessage());
        }
    }

} // end of class OutPlayerDao
