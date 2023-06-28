package team.snowball.baseball.model.stadium;


import java.util.List;

/**
 * author         : Yongwon Kim
 * date           : 2023-06-28
 * description    :
 */
public interface StadiumRepository {

    int insert(Stadium stadium);

    List<Stadium> findAllStadiums();

    int delete(int id);

    int update(Stadium stadium);

}