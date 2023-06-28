package team.snowball.baseball.dao;

import org.junit.jupiter.api.Test;
import team.snowball.baseball.model.stadium.Stadium;
import team.snowball.baseball.model.stadium.StadiumRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * author         : Yongwon Kim
 * date           : 2023-06-28
 * description    :
 */
class StadiumDAOTest {
    private StadiumRepository stadiumRepository = new StadiumDao();

    @Test
    void inset_test() {
        // Given
        Stadium stadium = Stadium.builder()
                .name("잠실야구장")
                .build();

        // When
        int result = stadiumRepository.insert(stadium);

        // Then
        assertThat(result).isEqualTo(1);
    }

    @Test
    void findAllStadium_test() {
        // Given

        // When
        List<Stadium> stadiumList = stadiumRepository.findAllStadiums();

        // Then
        assertThat(stadiumList).isNotEmpty();
    }

    @Test
    void delete_test() {
        // Given
        int id = 28;

        // When
        int result = stadiumRepository.delete(id);

        // Then
        assertThat(result).isEqualTo(1);
    }

    @Test
    void update_test() {
        // Given
        Stadium stadium = Stadium.builder()
                .id(1L)
                .name("잠실야구장")
                .build();

        // When
        int result = stadiumRepository.update(stadium);

        // Then
        assertThat(result).isEqualTo(1);
    }

}