package ru.mihozhereb.command;

import ru.mihozhereb.collection.CollectionManager;
import ru.mihozhereb.collection.model.MusicBand;
import ru.mihozhereb.control.Request;
import ru.mihozhereb.control.Response;

import java.util.Objects;

public class UpdateCommand implements Command {
    @Override
    public Response execute(Request r) {
        CollectionManager c = CollectionManager.getInstance();

        int id;
        try {
            id = Integer.parseInt(r.argument());
        } catch (NumberFormatException e) {
            return new Response("Error. Invalid id.", null);
        }

        c.getCollection().removeIf(n -> n.getId().equals(id));
        r.element().setIdManually(id);
        c.getCollection().add(r.element());
        return new Response("Done.", null);
    }

    @Override
    public String getHelp() {
        return "update id {element} | update element where id = id";
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.ENTER;
    }
}
