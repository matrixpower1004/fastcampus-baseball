package team.snowball.baseball.service;

import team.snowball.baseball.dao.StadiumDao;
import team.snowball.baseball.handler.InternalServerErrorException;
import team.snowball.baseball.model.stadium.Stadium;
import team.snowball.baseball.view.StadiumReport;

import java.util.List;

import static team.snowball.baseball.code.ConsoleMessage.MSG_SUCCESS_TO_DELETE;
import static team.snowball.baseball.code.ConsoleMessage.MSG_SUCCESS_TO_UPDATE;
import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_FAILED_TO_DELETE;
import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_FAILED_TO_UPDATE;
import static team.snowball.baseball.view.Report.showSaveResult;

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

    public void save(Stadium stadium) {
        if (stadium == null) {
            throw new InternalServerErrorException();
        }
        showSaveResult.accept(stadiumDao.save(stadium));
    }

    public List<Stadium> findAll() {
        return stadiumDao.findAll();
    }

    public Stadium findById(Long stadiumId) {
        return stadiumDao.findById(stadiumId);
    }

    public String update(Stadium stadium) {
        if (stadium == null) {
            throw new InternalServerErrorException();
        }
        return stadiumDao.update(stadium) == 1 ? MSG_SUCCESS_TO_UPDATE.getMessage() :
                ERR_MSG_FAILED_TO_UPDATE.getErrorMessage();
    }

    public String delete(Long id) {
        return stadiumDao.delete(id) == 1 ? MSG_SUCCESS_TO_DELETE.getMessage() :
                ERR_MSG_FAILED_TO_DELETE.getErrorMessage();

    }
}
