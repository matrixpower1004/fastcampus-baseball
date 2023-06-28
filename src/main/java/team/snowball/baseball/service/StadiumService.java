package team.snowball.baseball.service;

import team.snowball.baseball.dao.StadiumDao;
import team.snowball.baseball.model.stadium.Stadium;

import java.util.List;

/**
 * author         : Yongwon Kim
 * date           : 2023-06-28
 * description    :
 */
public class StadiumService implements CommandService {

    private static final StadiumDao stadiumDao = new StadiumDao();

    public static StadiumService stadiumService;

    private StadiumService() {
    }

    public static StadiumService getInstance() {
        if (stadiumService == null) {
            stadiumService = new StadiumService();
        }
        return stadiumService;
    }

    @Override
    public void create(Stadium stadium) {
        stadiumDao.insert(stadium);
    }

    @Override
    public void read() {
        List<Stadium> stadiums = stadiumDao.findAllStadiums();
        for (Stadium stadium : stadiums) {
            System.out.println(stadium);
        }
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