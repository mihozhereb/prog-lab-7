package ru.mihozhereb.command;

import ru.mihozhereb.collection.CollectionManager;
import ru.mihozhereb.collection.model.MusicBand;
import ru.mihozhereb.control.Request;
import ru.mihozhereb.control.Response;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ShowCommand implements Command {
    @Override
    public Response execute(Request r) {
        List<MusicBand> result =  CollectionManager.getInstance().getCollection().stream().sorted(
                Comparator.comparing(MusicBand::getCoordinates)
        ).toList();
        return new Response("Done.", result);
    }

    @Override
    public String getHelp() {
        return "show | show all elements in collection";
    }
}
