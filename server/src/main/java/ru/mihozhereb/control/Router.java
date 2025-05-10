package ru.mihozhereb.control;

import ru.mihozhereb.collection.CollectionManager;
import ru.mihozhereb.collection.model.MusicBand;
import ru.mihozhereb.command.*;

import java.time.LocalDateTime;

/**
 * Router class
 */
public class Router {
    /**
     * Define the command and start execution
     *
     * @param r {@code Request} object
     * @return {@code Response} object
     */
    public static Response route(Request r) {
        if (!r.command().equals("get_command_type") && !r.command().equals("register")
                && UserManager.getInstance().getUserId(r.login(), r.password()) == -1) {
            return new Response("Not authorized.", null);
        }

        if (r.command().equals("help")) {
            return new Response(helpCommand(), null);
        }

        Command command = CommandsMap.getCommand(r.command());

        if (command == null) {
            return new Response("Command not found.", null);
        }
        if (r.element() != null) {
            r.element().setOwnerId(UserManager.getInstance().getUserId(r.login(), r.password()));
        }

        return command.execute(r);
    }

    /**
     * Collects help information about all commands in the router
     *
     * @return full help text for user
     */
    private static String helpCommand() {
        StringBuilder helpText = new StringBuilder();

        helpText.append("HELP | COMMANDS:").append(System.lineSeparator());

        for (Command i : CommandsMap.getValues()) {
            if (!i.getHelp().isEmpty()) {
                helpText.append(i.getHelp()).append(System.lineSeparator());
            }
        }

        helpText.append("exit | terminate the client program without saving the file")
                .append(System.lineSeparator())
                .append("execute_script file_name | read and execute a script from a file")
                .append(System.lineSeparator());

        return helpText.toString();
    }
}
