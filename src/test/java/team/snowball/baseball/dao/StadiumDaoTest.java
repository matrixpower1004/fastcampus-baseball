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
class StadiumDaoTest {
    private StadiumRepository stadiumRepository = StadiumDao.getInstance();

    @Test
    void inset_test() {
        // Given
        Stadium stadium = Stadium.builder()
                .name("잠실야구장")
                .build();

        // When
        int result = stadiumRepository.save(stadium);

        // Then
        assertThat(result).isEqualTo(1);
    }

    @Test
    void findAllStadium_test() {
        // Given

        // When
        List<Stadium> stadiumList = stadiumRepository.findAll();

        // Then
        assertThat(stadiumList).isNotEmpty();
    }

    @Test
    void delete_test() {
        // Given
        Long id = 28L;

        // When
        int result = stadiumRepository.delete(id);

        // Then
        assertThat(result).isEqualTo(1);
    }

    @Test
    void update_test() {
        // Given
        Stadium stadium = Stadium.builder()
                .id(2L)
                .name("잠야구장")
                .build();

        // When
        int result = stadiumRepository.update(stadium);

        // Then
        assertThat(result).isEqualTo(1);
    }

    @Test
    void duplicate_test() {
        // Given
        Stadium stadium = Stadium.builder()
                .name("잠실야구")
                .build();

        // When
        int result = StadiumDao.getInstance().nameDuplicate(stadium);

        // Then
        assertThat(result).isEqualTo(0);
    }

    @Test
    void test() {
        // Given
        Long id = 1L;

        // When
        Stadium stadium = stadiumRepository.findById(id);

        // Then
        assertThat(stadium.getId()).isEqualTo(id);
    }

}