package ru.mihozhereb.command;

import ru.mihozhereb.collection.CollectionManager;
import ru.mihozhereb.collection.model.MusicBand;
import ru.mihozhereb.control.Request;
import ru.mihozhereb.control.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrintFieldAscendingNumberOfParticipantsCommand implements Command {
    @Override
    public Response execute(Request r) {
        List<String> result = CollectionManager.getInstance().getCollection().stream().mapToLong(
                MusicBand::getNumberOfParticipants
        ).sorted().mapToObj(String::valueOf).toList();

        return new Response("Done. " + String.join(" ", result), null);
    }

    @Override
    public String getHelp() {
        return "print_field_ascending_number_of_participants | print the values of the numberOfParticipants field of " +
                "all elements in ascending order";
    }
}
