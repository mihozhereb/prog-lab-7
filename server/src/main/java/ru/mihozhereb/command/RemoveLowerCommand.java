package ru.mihozhereb.command;

import ru.mihozhereb.collection.CollectionManager;
import ru.mihozhereb.control.Request;
import ru.mihozhereb.control.Response;

public class RemoveLowerCommand implements Command {
    @Override
    public Response execute(Request r) {
        CollectionManager.getInstance().getCollection().removeIf(i -> r.element().compareTo(i) > 0);

        return new Response("Done.", null);
    }

    @Override
    public String getHelp() {
        return "remove_lower {element} | remove from the collection all items smaller than the specified value";
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.ENTER;
    }
}
