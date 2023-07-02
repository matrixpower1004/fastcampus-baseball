package team.snowball.baseball.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team.snowball.baseball.code.ParamList;
import team.snowball.baseball.dto.QueryDto;
import team.snowball.baseball.handler.InvalidInputException;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * author         : Jason Lee
 * date           : 2023-06-26
 * description    :
 */
@Getter
@AllArgsConstructor
public enum Command {
    STADIUM_LIST("야구장목록", (queryDto) -> {
        if (queryDto.getParams().containsKey(ParamList.ID.getKeyName())) {
            StadiumController.getInstance().findById(queryDto);
            return;
        }
        StadiumController.getInstance().findAll();
    }),
    STADIUM_SAVE("야구장등록", (queryDto) -> {
        StadiumController.getInstance().save(queryDto);
    }),
    TEAM_LIST("팀목록", (queryDto) -> {
        if (queryDto.getParams().containsKey(ParamList.ID.getKeyName())) {
            TeamController.getInstance().findById(queryDto);
            return;
        }
        TeamController.getInstance().findAll();
    }),
    TEAM_SAVE("팀등록", (queryDto -> {
        TeamController.getInstance().save(queryDto);
    })),
    PLAYER_LIST("선수목록", (queryDto) -> {
        if (queryDto.getParams().containsKey(ParamList.TEAM_ID.getKeyName())) {
            PlayerController.getInstance().findByTeamId(queryDto);
            return;
        }
        if (queryDto.getParams().containsKey(ParamList.ID.getKeyName())) {
            PlayerController.getInstance().findById(queryDto);
            return;
        }
        PlayerController.getInstance().findAll();
    }),
    PLAYER_SAVE("선수등록", (queryDto) -> {
        PlayerController.getInstance().save(queryDto);
    }),
    OUT_PLAYER_LIST("퇴출목록", (queryDto) -> {
        if (queryDto.getParams().containsKey(ParamList.ID.getKeyName())) {
            OutPlayerController.getInstance().findById(queryDto);
            return;
        }
        OutPlayerController.getInstance().findAll();
    }),
    OUT_PLAYER_SAVE("퇴출등록", (queryDto) -> {
        OutPlayerController.getInstance().save(queryDto);
    }),
    POSITION_BY("포지션별목록", (queryDto) -> {
        PlayerController.getInstance().findByPosition();
    });

    private final String command;
    private final Consumer<QueryDto> getController;

    public static void execute(QueryDto queryDto) {
        queryDto.getCommand().getController.accept(queryDto);
    }

    public static Command findByCommandName(String request) {
        return Arrays.stream(Command.values())
                .filter(d -> d.command.equals(request))
                .findFirst()
                .orElseThrow(InvalidInputException::new);
    }
}
