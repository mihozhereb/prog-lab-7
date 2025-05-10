package ru.mihozhereb.command;

import ru.mihozhereb.control.Request;
import ru.mihozhereb.control.Response;
import ru.mihozhereb.control.UserManager;

import java.sql.SQLException;

public class RegisterCommand implements Command{
    @Override
    public Response execute(Request r) {
        try {
            UserManager.getInstance().register(r.login(), r.password());
            return new Response("Done.", null);
        } catch (SQLException e) {
            return new Response("Failed to register user with this login", null);
        }
    }

    @Override
    public String getHelp() {
        return "";
    }
}
