package team.snowball.baseball.dao;

import lombok.Getter;
import team.snowball.baseball.model.Stadium;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class StadiumDao {

    private Connection connection;

    public StadiumDao(Connection connection) {
        this.connection = connection;
    }

    // 야구장 등록
    public void createStadium(Stadium stadium) throws SQLException {
        String query = "INSERT INTO stadium(name, created_at) VALUES (?, now())";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, stadium.getName());
            statement.executeUpdate();
        }
    }

    // 전체 야구장 조회
    public List<Stadium> getAllStadiums() throws SQLException {
        List<Stadium> stadiums = new ArrayList<>();
        String query = "SELECT * FROM stadium";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Timestamp createdA = resultSet.getTimestamp("created_at");
                Stadium stadium = new Stadium(id, name, createdA);
                stadiums.add(stadium);
            }
        }
        return stadiums;
    }


}
