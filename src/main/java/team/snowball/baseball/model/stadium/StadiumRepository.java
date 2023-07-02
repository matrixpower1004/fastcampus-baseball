package team.snowball.baseball.model.stadium;


import java.util.List;

/**
 * author         : Yongwon Kim
 * date           : 2023-06-28
 * description    :
 */
public interface StadiumRepository {

    int save(Stadium stadium);

    List<Stadium> findAll();

    Stadium findById(Long id);

    int delete(Long id);

    int update(Stadium stadium);

}
