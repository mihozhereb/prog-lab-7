package ru.mihozhereb.command;

import ru.mihozhereb.collection.CollectionManager;
import ru.mihozhereb.collection.model.MusicBand;
import ru.mihozhereb.control.Request;
import ru.mihozhereb.control.Response;
import java.util.Comparator;

import java.util.SortedSet;

public class AddIfMaxCommand implements Command {
    @Override
    public Response execute(Request r) {
        SortedSet<MusicBand> collection = CollectionManager.getInstance().getCollection();

        if (collection.last().compareTo(r.element()) < 0) {
            collection.add(r.element());
        }

        return new Response("Done.", null);
    }

    @Override
    public String getHelp() {
        return "add_if_max {element} | add a new element to the collection if its value exceeds the value of the " +
                "largest element of this collection";
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.ENTER;
    }
}
