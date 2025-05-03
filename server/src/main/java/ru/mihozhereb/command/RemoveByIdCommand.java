package ru.mihozhereb.command;

import ru.mihozhereb.collection.CollectionManager;
import ru.mihozhereb.control.Request;
import ru.mihozhereb.control.Response;

public class RemoveByIdCommand implements Command {
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
        return new Response("Done.", null);
    }

    @Override
    public String getHelp() {
        return "remove_by_id id | remove element where id = id";
    }
}
