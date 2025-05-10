package ru.mihozhereb.command;

import ru.mihozhereb.collection.CollectionManager;
import ru.mihozhereb.control.Request;
import ru.mihozhereb.control.Response;

import java.sql.SQLException;

public class AddCommand implements Command {
    @Override
    public Response execute(Request r) {
        try {
            CollectionManager.getInstance().add(r.element());
        } catch (SQLException e) {
            return new Response("Error. " + e.getMessage(), null);
        }

        return new Response("Done.", null);
    }

    @Override
    public String getHelp() {
        return "add {element} | add element in collection";
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.ENTER;
    }
}
