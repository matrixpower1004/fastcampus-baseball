package team.snowball.baseball.service;

import team.snowball.baseball.dao.PlayerDao;
import team.snowball.baseball.dao.SnowballDBManager;
import team.snowball.baseball.dto.PositionRespDto;
import team.snowball.baseball.handler.InternalServerErrorException;
import team.snowball.baseball.model.player.Player;
import team.snowball.baseball.model.player.PlayerRepository;

import java.sql.Connection;
import java.util.List;

import static team.snowball.baseball.code.ConsoleMessage.MSG_SUCCESS_TO_REGISTER;
import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_FAILED_TO_REGISTER;
import static team.snowball.baseball.view.PlayerReport.showPlayerByTeam;
import static team.snowball.baseball.view.Report.showResult;

/**
 * author         : Jason Lee
 * date           : 2023-06-28
 * description    :
 */
public class PlayerService {

    private static PlayerService playerService;
    private static final PlayerRepository REPOSITORY = PlayerDao.getInstance();

    private static final Connection CONNECTION = SnowballDBManager.getConnection();

    private PlayerService() {
    }

    public static PlayerService getInstance() {
        if (playerService == null) {
            playerService = new PlayerService();
        }
        return playerService;
    }
    public void save(Player player) {
        if (player == null) {
            throw new InternalServerErrorException();
        }
        System.out.println(REPOSITORY.insert(player) == 1 ?
                MSG_SUCCESS_TO_REGISTER.getMessage() : ERR_MSG_FAILED_TO_REGISTER.getErrorMessage());
    }

    public void find(Long teamId) {
        List<Player> playerList = REPOSITORY.findByTeamId(teamId);
        showPlayerByTeam(playerList);
    }

    public void find() {
        List<Player> playerList = REPOSITORY.findAll();
        System.out.println(playerList);
        //todo: view 구현
    }

    public void update(Player player) {
        System.out.println("선수 수정");
    }

    public void delete(Long id) {
        int result = REPOSITORY.delete(id);
        showResult.accept(result);
    }

    public PositionRespDto findPositionBy() {
        return REPOSITORY.findLineByPosition();
    }
}
