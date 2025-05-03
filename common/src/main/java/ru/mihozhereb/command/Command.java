package ru.mihozhereb.command;

import ru.mihozhereb.control.Request;
import ru.mihozhereb.control.Response;

public interface Command {
    /**
     * Executes the command with arguments from the {@code Request} and returns the result in the {@code Response}
     *
     * @param r Request object
     * @return Response object
     * @see Request
     * @see Response
     */
    Response execute(Request r);

    /**
     * Returns a text description of the command and the format of the request
     * <p>
     * Format "command | description"
     *
     * @return String command description
     */
    String getHelp();

    /**
     * Return a type of command (PRIMITIVE or ENTER)
     *
     * @return Command type
     */
    default CommandType getCommandType() {
        return CommandType.PRIMITIVE;
    }
}
