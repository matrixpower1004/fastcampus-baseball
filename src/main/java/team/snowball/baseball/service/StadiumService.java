package team.snowball.baseball.service;

import team.snowball.baseball.dao.StadiumDao;
import team.snowball.baseball.model.Stadium;
import java.util.List;

/**
 * author         : Yongwon Kim
 * date           : 2023-06-27
 * description    :
 */

public class StadiumService {
    private StadiumDao stadiumDao;

    public StadiumService(StadiumDao stadiumDao) {
        this.stadiumDao = stadiumDao;
    }

    // 야구장 생성 로직 구현
    public void createStadium(String name) {
        Stadium stadium = new Stadium(null, name, null);
        stadiumDao.createStadium(stadium);
    }

    // 야구장 전체 목록 로직 구현
    public List<Stadium> getAllStadiums() {
        return stadiumDao.getAllStadiums();
    }

}