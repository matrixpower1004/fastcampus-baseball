package team.snowball.baseball.service;

/**
 * author         : Jason Lee
 * date           : 2023-06-28
 * description    :
 */
public class OutPlayerService implements CommandService {

    public static OutPlayerService outPlayerService;

    public OutPlayerService() {
    }

    public static OutPlayerService getInstance() {
        if (outPlayerService == null) {
            outPlayerService = new OutPlayerService();
        }
        return outPlayerService;
    }

    @Override
    public void create() {

    }

    @Override
    public void read() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
