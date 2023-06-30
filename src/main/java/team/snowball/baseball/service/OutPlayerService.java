package team.snowball.baseball.service;

import team.snowball.baseball.dao.OutPlayerDao;
import team.snowball.baseball.dto.OutPlayerRespDto;
import team.snowball.baseball.handler.InternalServerErrorException;
import team.snowball.baseball.model.player.OutPlayer;
import team.snowball.baseball.model.player.OutPlayerRepository;
import team.snowball.baseball.view.Report;

import java.util.List;

import static team.snowball.baseball.code.ConsoleMessage.*;
import static team.snowball.baseball.code.ErrorMessage.*;

/**
 * author         : Jason Lee
 * date           : 2023-06-28
 * description    :
 */
public class OutPlayerService {

    public static OutPlayerService outPlayerService;

    private static final OutPlayerRepository REPOSITORY = OutPlayerDao.getInstance();

    public OutPlayerService() {
    }

    public static OutPlayerService getInstance() {
        if (outPlayerService == null) {
            outPlayerService = new OutPlayerService();
        }
        return outPlayerService;
    }

    public void create(OutPlayer outPlayer) {
        if (outPlayer == null) {
            throw new InternalServerErrorException();
        }
        System.out.println(REPOSITORY.insert(outPlayer) == 1 ?
                MSG_SUCCESS_TO_REGISTER.getMessage() : ERR_MSG_FAILED_TO_REGISTER.getErrorMessage());
    }

    public void read() {
        List<OutPlayerRespDto> outPlayerRespDto = REPOSITORY.findAll();
        Report.showOutPlayer(outPlayerRespDto);
    }

    public void update(OutPlayer outPlayer) {

    }

    public void delete(Long id) {

    }
}
