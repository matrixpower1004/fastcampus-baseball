package team.snowball.baseball.service;

import team.snowball.baseball.dao.OutPlayerDao;
import team.snowball.baseball.dto.OutPlayerRespDto;
import team.snowball.baseball.handler.InternalServerErrorException;
import team.snowball.baseball.model.player.OutPlayer;
import team.snowball.baseball.model.player.OutPlayerRepository;

import java.util.List;

import static team.snowball.baseball.code.ConsoleMessage.MSG_SUCCESS_TO_REGISTER;
import static team.snowball.baseball.code.ErrorMessage.ERR_MSG_FAILED_TO_REGISTER;
import static team.snowball.baseball.view.OutPlayerReport.showOutPlayer;

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

    public void save(OutPlayer outPlayer) {
        if (outPlayer == null) {
            throw new InternalServerErrorException();
        }
        System.out.println(REPOSITORY.insert(outPlayer) == 1 ?
                MSG_SUCCESS_TO_REGISTER.getMessage() : ERR_MSG_FAILED_TO_REGISTER.getErrorMessage());
    }

    public void find() {
        List<OutPlayerRespDto> outPlayerRespDto = REPOSITORY.findAll();
        showOutPlayer(outPlayerRespDto);
    }

    public void find(Long id) {
        // 요구 사항에 없는 기능
    }

    public void update(OutPlayer outPlayer) {
        // 요구 사항에 없는 기능
    }

    public void delete(Long id) {
        // 요구 사항에 없는 기능
    }
}
