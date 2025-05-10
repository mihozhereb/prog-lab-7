package ru.mihozhereb.command;

import ru.mihozhereb.collection.CollectionManager;
import ru.mihozhereb.collection.model.MusicBand;
import ru.mihozhereb.control.Request;
import ru.mihozhereb.control.Response;
import ru.mihozhereb.control.UserManager;

import java.sql.SQLException;
import java.util.SortedSet;

public class RemoveGreaterCommand implements Command {
    @Override
    public Response execute(Request r) {
        int userId = UserManager.getInstance().getUserId(r.login(), r.password());

        for (MusicBand i : CollectionManager.getInstance().getCollection()) {
            if (r.element().compareTo(i) < 0) {
                try {
                    CollectionManager.getInstance().remove(userId, i.getId());
                } catch (SQLException e) {
                    return new Response("Error. " + e.getMessage(), null);
                }
            }
        }

        return new Response("Done.", null);
    }

    @Override
    public String getHelp() {
        return "remove_greater {element} | remove all items from the collection that exceed the specified value";
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.ENTER;
    }
}
