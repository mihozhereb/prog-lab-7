package ru.mihozhereb.command;

import ru.mihozhereb.collection.CollectionManager;
import ru.mihozhereb.control.Request;
import ru.mihozhereb.control.Response;

public class AddCommand implements Command {
    @Override
    public Response execute(Request r) {
        CollectionManager.getInstance().getCollection().add(r.element());

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
