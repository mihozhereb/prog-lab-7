package ru.mihozhereb.command;

import ru.mihozhereb.collection.CollectionManager;
import ru.mihozhereb.control.Request;
import ru.mihozhereb.control.Response;
import ru.mihozhereb.control.UserManager;

import java.sql.SQLException;

public class RemoveByIdCommand implements Command {
    @Override
    public Response execute(Request r) {
        int id;
        try {
            id = Integer.parseInt(r.argument());
        } catch (NumberFormatException e) {
            return new Response("Error. Invalid id.", null);
        }

        int userId = UserManager.getInstance().getUserId(r.login(), r.password());

        try {
            CollectionManager.getInstance().remove(userId, id);
        } catch (SQLException e) {
            return new Response("Error. " + e.getMessage(), null);
        }
        return new Response("Done.", null);
    }

    @Override
    public String getHelp() {
        return "remove_by_id id | remove element where id = id";
    }
}
