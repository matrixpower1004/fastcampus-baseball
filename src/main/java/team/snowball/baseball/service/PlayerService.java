package team.snowball.baseball.service;

import team.snowball.baseball.dao.PlayerDao;
import team.snowball.baseball.dto.PositionRespDto;
import team.snowball.baseball.handler.InternalServerErrorException;
import team.snowball.baseball.model.player.Player;
import team.snowball.baseball.model.player.PlayerRepository;

import java.util.List;

import static team.snowball.baseball.view.Report.showIsDuplicate;

/**
 * author         : Jason Lee
 * date           : 2023-06-28
 * description    :
 */
public class PlayerService {

    private static final PlayerRepository PLAYER_REPOSITORY = PlayerDao.getInstance();

    private static PlayerService playerService;


    private PlayerService() {
    }

    public static PlayerService getInstance() {
        if (playerService == null) {
            playerService = new PlayerService();
        }
        return playerService;
    }

    public int save(Player player) {
        if (player == null) {
            throw new InternalServerErrorException();
        }
        return PLAYER_REPOSITORY.insert(player);
    }

    public List<Player> findByTeamId(Long teamId) {
        return PLAYER_REPOSITORY.findByTeamId(teamId);
    }

    public Player findById(Long id) {
        // 요구사항에 없는 기능
        return PLAYER_REPOSITORY.findById(id);
    }

    public List<Player> findAll() {
        return PLAYER_REPOSITORY.findAll();

    }

    public void update(Player player) {
        // 요구사항에 없는 기능
    }

    public void delete(Long id) {
        int result = PLAYER_REPOSITORY.delete(id);
        showIsDuplicate.accept(result);
    }

    public PositionRespDto findPositionBy() {
        return PLAYER_REPOSITORY.findLineByPosition();
    }

} // end of class
