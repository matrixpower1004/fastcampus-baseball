package team.snowball.baseball.service;

import team.snowball.baseball.dao.OutPlayerDao;
import team.snowball.baseball.dao.PlayerDao;
import team.snowball.baseball.dao.SnowballDBManager;
import team.snowball.baseball.dto.OutPlayerRespDto;
import team.snowball.baseball.handler.DatabaseException;
import team.snowball.baseball.handler.InternalServerErrorException;
import team.snowball.baseball.model.player.OutPlayer;
import team.snowball.baseball.model.player.OutPlayerRepository;
import team.snowball.baseball.model.player.PlayerRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static team.snowball.baseball.code.ConsoleMessage.MSG_SUCCESS_TO_REGISTER;
import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_FAILED_TO_REGISTER;

/**
 * author         : Jason Lee
 * date           : 2023-06-28
 * description    :
 */
public class OutPlayerService {

    public static OutPlayerService outPlayerService;

    private static final OutPlayerRepository OUT_PLAYER_REPOSITORY = OutPlayerDao.getInstance();
    private static final PlayerRepository PLAYER_REPOSITORY = PlayerDao.getInstance();
    private static final Connection CONNECTION = SnowballDBManager.getConnection();

    public OutPlayerService() {
    }

    public static OutPlayerService getInstance() {
        if (outPlayerService == null) {
            outPlayerService = new OutPlayerService();
        }
        return outPlayerService;
    }

    public String save(OutPlayer outPlayer) {
        if (outPlayer == null) {
            throw new InternalServerErrorException();
        }
        /**
         *  퇴출 선수 등록은 2단계로 이루어진다.
         *  1. out_player 테이블에 퇴출 선수 데이터를 삽입한다.
         *  2. 해당 선수의 id를 통해 player 테이블의 team_id를 null로 update
         */
        try {
            CONNECTION.setAutoCommit(false);

            // 퇴출 선수 등록
            int outPlayerResult = OUT_PLAYER_REPOSITORY.save(outPlayer);
            if (outPlayerResult != 1) {
                CONNECTION.rollback();
                throw new DatabaseException(ERR_MSG_FAILED_TO_REGISTER.getErrorMessage());
            }

            // Player id를 통해 해당 선수의 team_id를 null로 update
            int playerResult = PLAYER_REPOSITORY.updateRetired(outPlayer.getPlayerId());
            if (playerResult != 1) {
                CONNECTION.rollback();
                throw new DatabaseException(ERR_MSG_FAILED_TO_REGISTER.getErrorMessage());
            }

            // 모든 쿼리가 성공적으로 수행되었다면 commit 하고 종료
            CONNECTION.commit();
            return MSG_SUCCESS_TO_REGISTER.getMessage();

        } catch (SQLException e) {
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                throw new DatabaseException(ERR_MSG_FAILED_TO_REGISTER.getErrorMessage());
            }
        }
        throw new DatabaseException(ERR_MSG_FAILED_TO_REGISTER.getErrorMessage());
    }

    public List<OutPlayerRespDto> findAll() {
        return OUT_PLAYER_REPOSITORY.findAll();
    }

    public void findById(Long id) {
        // 요구 사항에 없는 기능
    }

    public void update(OutPlayer outPlayer) {
        // 요구 사항에 없는 기능
    }

    public void delete(Long id) {
        // 요구 사항에 없는 기능
        // 퇴출 테이블에서 삭제되면 선수 테이블에서도 삭제해야 하나?
    }
}
