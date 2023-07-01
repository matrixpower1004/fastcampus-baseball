package team.snowball.baseball.service;

import team.snowball.baseball.dao.StadiumDao;
import team.snowball.baseball.handler.InternalServerErrorException;
import team.snowball.baseball.model.stadium.Stadium;

import java.util.List;
import java.util.function.Consumer;

import static team.snowball.baseball.code.ConsoleMessage.MSG_SUCCESS_TO_REGISTER;
import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_OVERLAP_NAME;
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
        if (stadium == null) {
            throw new InternalServerErrorException();
        }
        showResult.accept(stadiumDao.insert(stadium));
    }

    Consumer<Integer> showResult = (result) -> {
        System.out.println(result == 1 ?
                MSG_SUCCESS_TO_REGISTER.getMessage() : ERR_MSG_OVERLAP_NAME.getErrorMessage());
    };

    public void read() {
        List<Stadium> stadiums = stadiumDao.findAllStadiums();
        showStadiumList(stadiums);
    }

    public void read(Long stadiumId) {
        // 요구사항에 없는 기능.
    }

    public void update(Stadium stadium) {
        stadiumDao.update(stadium);
    }

    public void delete(Long id) {
        stadiumDao.delete(id);
    }

}
