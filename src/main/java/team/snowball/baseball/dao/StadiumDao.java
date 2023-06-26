package team.snowball.baseball.dao;

import lombok.Getter;
import team.snowball.baseball.handler.DatabaseException;
import team.snowball.baseball.model.Stadium;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * author         : Yongwon Kim
 * date           : 2023-06-26
 * description    :
 */

@Getter
public class StadiumDao {

    private Connection connection;

    public StadiumDao(Connection connection) {
        this.connection = connection;
    }

    // 야구장 등록
    public void createStadium(Stadium stadium) {
        String query = "INSERT INTO stadium(name, created_at) VALUES (?, now())";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, stadium.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            SnowballDBManager.disconnect(connection, null, null);
        }
    }

    // 전체 야구장 조회
    public List<Stadium> getAllStadiums() {
        List<Stadium> stadiums = new ArrayList<>();
        String query = "SELECT * FROM stadium";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Timestamp createdA = resultSet.getTimestamp("created_at");
                Stadium stadium = new Stadium(id, name, createdA);
                stadiums.add(stadium);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            SnowballDBManager.disconnect(connection, null, null);
        }
        return stadiums;
    }

}

