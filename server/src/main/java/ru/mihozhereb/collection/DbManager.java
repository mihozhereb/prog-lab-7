package ru.mihozhereb.collection;

import ru.mihozhereb.collection.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DbManager {
    private final Connection connection;

    public DbManager(String jdbcUrl, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(jdbcUrl, username, password);
    }

    private MusicBand parseResultSetToMusicBand(ResultSet rs) throws SQLException {
        MusicBand mb = new MusicBand();
        mb.setCoordinates(new Coordinates());
        mb.setFrontMan(new Person());

        mb.setId(rs.getInt("id"));
        mb.setName(rs.getString("name"));
        mb.getCoordinates().setX(rs.getDouble("x"));
        mb.getCoordinates().setY(rs.getFloat("y"));
        mb.setCreationDate(rs.getObject("creationDate", LocalDateTime.class));
        mb.setNumberOfParticipants(rs.getLong("numberOfParticipants"));
        rs.getString("genre");
        if (!rs.wasNull()) {
            mb.setGenre(MusicGenre.valueOf(rs.getString("genre")));
        }
        mb.getFrontMan().setName(rs.getString("personName"));
        mb.getFrontMan().setBirthday(rs.getObject("personBirthday", LocalDate.class));
        rs.getDouble("personHeight");
        if (!rs.wasNull()) {
            mb.getFrontMan().setHeight(rs.getDouble("personHeight"));
        }
        mb.getFrontMan().setWeight(rs.getInt("personWeight"));
        rs.getString("personHairColor");
        if (!rs.wasNull()) {
            mb.getFrontMan().setHairColor(Color.valueOf(rs.getString("personHairColor")));
        }
        mb.setOwnerId(rs.getInt("ownerId"));

        return mb;
    }

    public List<MusicBand> selectMusicBands() throws SQLException {
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM musicBands;");

        List<MusicBand> result = new ArrayList<MusicBand>();

        while (resultSet.next()) {
            result.add(parseResultSetToMusicBand(resultSet));
        }

        return result;
    }

    private void prepareStatement(PreparedStatement stmt, MusicBand mb) throws SQLException {
        stmt.setString(1, mb.getName());
        stmt.setDouble(2, mb.getCoordinates().getX());
        stmt.setFloat(3, mb.getCoordinates().getY());
        stmt.setLong(4, mb.getNumberOfParticipants());
        if (mb.getGenre() == null) {
            stmt.setNull(5, Types.VARCHAR);
        } else {
            stmt.setString(5, mb.getGenre().toString()); // assuming MusicGenre is an enum type
        }
        stmt.setString(6, mb.getFrontMan().getName());
        stmt.setObject(7, mb.getFrontMan().getBirthday());
        if (mb.getFrontMan().getHeight() == null) {
            stmt.setNull(8, Types.DECIMAL);
        } else {
            stmt.setDouble(8, mb.getFrontMan().getHeight());
        }
        stmt.setInt(9, mb.getFrontMan().getWeight());
        if (mb.getFrontMan().getHairColor() == null) {
            stmt.setNull(10, Types.VARCHAR);
        } else {
            stmt.setString(10, mb.getFrontMan().getHairColor().toString());
        }
        stmt.setInt(11, mb.getOwnerId());
    }

    public void insertMusicBand(MusicBand mb) throws SQLException {
        String sql = "INSERT INTO MusicBands (Name, X, Y, NumberOfParticipants, Genre, PersonName, PersonBirthday, " +
                "PersonHeight, PersonWeight, PersonHairColor, OwnerId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement statement = connection.prepareStatement(sql);

        prepareStatement(statement, mb);

        statement.executeUpdate();
    }

    public void removeMusicBand(int ownerId, int MBId) throws SQLException {
        String sql = "DELETE FROM MusicBands WHERE OwnerId = ? AND Id = ?;";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, ownerId);
        statement.setInt(2, MBId);

        statement.executeUpdate();
    }

    public void removeMusicBand(int ownerId) throws SQLException {
        String sql = "DELETE FROM MusicBands WHERE OwnerId = ?;";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, ownerId);

        statement.executeUpdate();
    }

    public void updateMusicBand(MusicBand newMB) throws SQLException {
        String sql = "UPDATE MusicBands SET Name = ?, X = ?, Y = ?, NumberOfParticipants = ?, Genre = ?, " +
                "PersonName = ?, PersonBirthday = ?, PersonHeight = ?, PersonWeight = ?, PersonHairColor = ?, " +
                "OwnerId = ? WHERE Id = ?;";

        PreparedStatement statement = connection.prepareStatement(sql);

        prepareStatement(statement, newMB);

        statement.setInt(12, newMB.getId());

        statement.executeUpdate();
    }

    public void insertUser(String login, String password) throws SQLException {
        String sql = "INSERT INTO Users (Login, Password) VALUES (?, ?);";

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, login);
        statement.setString(2, password);

        statement.executeUpdate();
    }

    public int selectUser(String login, String password) throws SQLException {
        String sql = "SELECT userid FROM users WHERE login = ? AND password = ?;";

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, login);
        statement.setString(2, password);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("userid");
        }

        return -1;
    }
}
