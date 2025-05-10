package ru.mihozhereb.control;

import ru.mihozhereb.collection.DbManager;

import java.sql.SQLException;

/**
 * UserManager singleton class
 */
public class UserManager {
    private UserManager() {}

    private static UserManager instance;
    private DbManager db;

    /**
     * Return instance of {@code UserManager}
     *
     * @return UserManager instance
     */
    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void load(DbManager dbm) {
        db = dbm;
    }

    public int getUserId(String login, String password) {
        try {
            return db.selectUser(login, password);
        } catch (SQLException e) {
            System.out.println(e);
            return -1;
        }
    }

    public void register(String login, String password) throws SQLException {
        db.insertUser(login, password);
    }
}
