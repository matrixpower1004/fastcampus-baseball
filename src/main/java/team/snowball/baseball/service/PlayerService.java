package team.snowball.baseball.service;

/**
 * author         : Jason Lee
 * date           : 2023-06-28
 * description    :
 */
public class PlayerService implements CommandService {

    private static PlayerService playerService;
    private PlayerService() {
    }

    public static PlayerService getInstance() {
        if (playerService == null) {
            playerService = new PlayerService();
        }
        return playerService;
    }

    @Override
    public void create() {
        System.out.println("등록");
    }

    @Override
    public void read() {
        System.out.println("조회");
    }

    @Override
    public void update() {
        System.out.println("수정");
    }

    @Override
    public void delete() {
        System.out.println("삭제");
    }
}
