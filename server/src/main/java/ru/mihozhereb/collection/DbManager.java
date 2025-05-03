package ru.mihozhereb.collection;

import ru.mihozhereb.collection.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DbManager {
    private final Connection connection;

    public DbManager(String jdbcUrl, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(jdbcUrl, username, password);
    }

    private MusicBand ParseResultSetToMusicBand(ResultSet rs) throws SQLException {
        MusicBand mb = new MusicBand();
        mb.setCoordinates(new Coordinates());
        mb.setFrontMan(new Person());

        mb.setId(rs.getInt("id"));
        mb.setName(rs.getString("name"));
        mb.getCoordinates().setX(rs.getDouble("x"));
        mb.getCoordinates().setY(rs.getFloat("y"));
        mb.setCreationDate(rs.getObject("creationDate", LocalDateTime.class));
        mb.setNumberOfParticipants(rs.getInt("numberOfParticipants"));
        mb.setGenre(rs.getObject("genre", MusicGenre.class));
        mb.getFrontMan().setName(rs.getString("personName"));
        mb.getFrontMan().setBirthday(rs.getObject("personBirthday", LocalDate.class));
        mb.getFrontMan().setHeight(rs.getDouble("personHeight"));
        mb.getFrontMan().setWeight(rs.getInt("personWeight"));
        mb.getFrontMan().setHairColor(rs.getObject("personHairColor", Color.class));

        return mb;
    }

    public void SelectBands() throws SQLException {
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM battle");

        while (resultSet.next())
        {
            String columnValue = resultSet.getString("battleid");
            LocalDateTime asd = resultSet.getObject("starttime", LocalDateTime.class);
            System.out.println("Column Value: " + columnValue + asd);
        }
    }
}
