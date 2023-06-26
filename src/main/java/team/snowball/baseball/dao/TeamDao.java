package team.snowball.baseball.dao;

import team.snowball.baseball.handler.DatabaseException;
import team.snowball.baseball.model.Stadium;
import team.snowball.baseball.model.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * author         : Yongwon Kim
 * date           : 2023-06-26
 * description    :
 */

public class TeamDao {

    private Connection connection;

    public TeamDao(Connection connection) {
        this.connection = connection;
    }

    // 팀 등록
    public void createTeam(Team team) {
        String query = "INSERT INTO team(stadium_id, name, created_at) VALUES (?, ?, now())";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, team.getStadiumId());
            statement.setString(2, team.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new DatabaseException(e.getMessage());
                }
            }
            SnowballDBManager.disconnect(connection, null, null);
        }
    }


    // 전체 팀 조회
    public List<Team> getAllTeams() {
        List<Team> teams = new ArrayList<>();
        String query = "SELECT * FROM team";
        ResultSet resultSet = null;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                int stadiumId = resultSet.getInt("stadium_id");
                String name = resultSet.getString("name");
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                Team team = new Team(id, stadiumId, name, createdAt);
                teams.add(team);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new DatabaseException(e.getMessage());
                }
            }
            SnowballDBManager.disconnect(connection, null, null);
        }
        return teams;
    }
}
