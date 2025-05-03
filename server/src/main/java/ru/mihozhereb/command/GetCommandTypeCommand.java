package ru.mihozhereb.command;

import ru.mihozhereb.control.CommandsMap;
import ru.mihozhereb.control.Request;
import ru.mihozhereb.control.Response;

import java.util.Optional;

public class GetCommandTypeCommand implements Command{

    @Override
    public Response execute(Request r) {
        Optional<CommandType> commandType = Optional.ofNullable(CommandsMap.getCommandType(r.argument()));
        return new Response(commandType.orElse(CommandType.PRIMITIVE).toString(), null);
    }

    @Override
    public String getHelp() {
        return "";
    }
}
