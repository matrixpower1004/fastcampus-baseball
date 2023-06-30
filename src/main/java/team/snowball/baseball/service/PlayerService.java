package team.snowball.baseball.service;

import team.snowball.baseball.dao.PlayerDao;
import team.snowball.baseball.handler.InternalServerErrorException;
import team.snowball.baseball.model.player.Player;
import team.snowball.baseball.model.player.PlayerRepository;

import java.util.List;

import static team.snowball.baseball.code.ConsoleMessage.*;
import static team.snowball.baseball.code.ErrorMessage.*;
import static team.snowball.baseball.view.Report.showPlayerByTeam;

/**
 * author         : Jason Lee
 * date           : 2023-06-28
 * description    :
 */
public class PlayerService {

    private static PlayerService playerService;
    private static final PlayerRepository REPOSITORY = PlayerDao.getInstance();
    private PlayerService() {
    }

    public static PlayerService getInstance() {
        if (playerService == null) {
            playerService = new PlayerService();
        }
        return playerService;
    }
    public void create(Player player) {
        if (player == null) {
            throw new InternalServerErrorException();
        }
        System.out.println(REPOSITORY.insert(player) == 1 ?
                MSG_SUCCESS_TO_REGISTER.getMessage() : ERR_MSG_FAILED_TO_REGISTER.getErrorMessage());
    }

    public void read(int id) {
        List<Player> playerList = REPOSITORY.findByTeamId(id);
        showPlayerByTeam(playerList);
    }

    public void update(Player player) {
        System.out.println("선수 수정");
    }

    public void delete(Long id) {
        if (REPOSITORY.delete(id) != 1) {
            System.out.println(ERR_MSG_FAILED_TO_DELETE.getErrorMessage());
        }
        System.out.println(MSG_SUCCESS_TO_DELETE.getMessage());
    }

    public void positionByReport() {
        REPOSITORY.findLineByPosition();
    }
}
