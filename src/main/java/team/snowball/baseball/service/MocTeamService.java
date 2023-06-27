package team.snowball.baseball.service;

/**
 * author         : Jason Lee
 * date           : 2023-06-28
 * description    :
 */
public class MocTeamService implements CommandService {

    public static MocTeamService mocTeamService;

    private MocTeamService() {
    }

    public static MocTeamService getInstance() {
        if (mocTeamService == null) {
            mocTeamService = new MocTeamService();
        }
        return mocTeamService;
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
