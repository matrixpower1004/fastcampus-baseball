package team.snowball.baseball.service;

import team.snowball.baseball.dao.StadiumDao;
import team.snowball.baseball.model.stadium.Stadium;

import java.util.List;

import static team.snowball.baseball.view.Report.showStadiumList;

/**
 * author         : Yongwon Kim
 * date           : 2023-06-28
 * description    :
 */
public class StadiumService {

    private static final StadiumDao stadiumDao = StadiumDao.getInstance();

    public static StadiumService stadiumService;

    private StadiumService() {
    }

    public static StadiumService getInstance() {
        if (stadiumService == null) {
            stadiumService = new StadiumService();
        }
        return stadiumService;
    }

    public void create(Stadium stadium) {
        stadiumDao.insert(stadium);
    }

    public void read() {
        List<Stadium> stadiums = stadiumDao.findAllStadiums();
        showStadiumList(stadiums);
    }

    public void update(Stadium stadium) {
        stadiumDao.update(stadium);
    }

    public void delete(Long id) {
        stadiumDao.delete(id);
    }

}
