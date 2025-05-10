package ru.mihozhereb.command;

import ru.mihozhereb.collection.CollectionManager;
import ru.mihozhereb.control.Request;
import ru.mihozhereb.control.Response;
import ru.mihozhereb.control.UserManager;

import java.sql.SQLException;

public class ClearCommand implements Command {
    @Override
    public Response execute(Request r) {
        try {
            CollectionManager.getInstance().remove(UserManager.getInstance().getUserId(r.login(), r.password()));
        } catch (SQLException e) {
            return new Response("Error. " + e.getMessage(), null);
        }

        return new Response("Done.", null);
    }

    @Override
    public String getHelp() {
        return "clear | clear collection";
    }
}
