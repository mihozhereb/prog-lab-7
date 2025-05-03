package ru.mihozhereb.control;

import ru.mihozhereb.collection.model.MusicBand;
import ru.mihozhereb.command.CommandType;
import ru.mihozhereb.command.ExecuteScriptCommand;
import ru.mihozhereb.io.ConsoleWorker;

import java.io.IOException;
import java.util.Optional;

/**
 * Handler class
 */
public class Handler {
    /**
     * The function processes the string, highlighting the command name, command arguments.
     * If necessary, forms a new object from user input.
     * Creates a {@code Request} object and sends a command for execution, then receives a {@code Response} object
     * from {@code Router} and returns the result of execution in a string form understandable for the user.
     *
     * @param row user command
     * @return result of execution in a string form understandable for the user
     * @see Request
     * @see Response
     * @see UDPClient
     */
    private final UDPClient client;

    public Handler(UDPClient client) {
        this.client = client;
    }

    private CommandType getCommandType(String command) throws IOException {
        String commandType;

        commandType = client.sendRequest(
                new Request(
                        "get_command_type", command, null,
                        UserData.getUserLogin(), UserData.getUserPassword()
                )
        ).response();

        return CommandType.valueOf(commandType);
    }

    public String handle(String row, ConsoleWorker cw) throws IOException {
        String[] args = row.split(" ", 2);

        if (args[0].equals("exit")) {
            System.exit(0);
        }

        Optional<MusicBand> element = Optional.empty();

        if (getCommandType(args[0]) == CommandType.ENTER) {
            InputHelper inputHelper = new InputHelper(cw);
            try {
                element = Optional.ofNullable(inputHelper.input());
            } catch (InputCancelledException e) {
                return e.getLocalizedMessage() + System.lineSeparator();
            }
        }

        Request req = new Request(
                args[0], args.length == 2 ? args[1] : null, element.orElse(null),
                UserData.getUserLogin(), UserData.getUserPassword()
        );

        Response resp;
        if (args[0].equals("execute_script")) {
            resp = new ExecuteScriptCommand().execute(req);
        } else {
            resp = client.sendRequest(req);
        }

        StringBuilder responseText = new StringBuilder(resp.response()).append(System.lineSeparator());

        if (resp.elements() != null) {
            for (MusicBand m : resp.elements()) {
                responseText.append(m.toString()).append(",").append(System.lineSeparator());
            }
        }

        return responseText.toString();
    }

    public UDPClient getClient() {
        return client;
    }
}
