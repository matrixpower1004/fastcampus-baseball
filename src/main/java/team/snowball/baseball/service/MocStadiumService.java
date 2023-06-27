package team.snowball.baseball.service;

/**
 * author         : Jason Lee
 * date           : 2023-06-28
 * description    :
 */
public class MocStadiumService implements CommandService {

    public static MocStadiumService mocStadiumService;

    private MocStadiumService() {
    }

    public static MocStadiumService getInstance() {
        if (mocStadiumService == null) {
            mocStadiumService = new MocStadiumService();
        }
        return mocStadiumService;
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
