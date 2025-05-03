package ru.mihozhereb.command;

import ru.mihozhereb.collection.CollectionManager;
import ru.mihozhereb.collection.model.MusicBand;
import ru.mihozhereb.control.Request;
import ru.mihozhereb.control.Response;

import java.util.SortedSet;

public class RemoveGreaterCommand implements Command {
    @Override
    public Response execute(Request r) {
        CollectionManager.getInstance().getCollection().removeIf(i -> r.element().compareTo(i) < 0);

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
