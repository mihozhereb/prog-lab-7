package ru.mihozhereb.control;

import ru.mihozhereb.command.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Storage of commands
 */
public class CommandsMap {
    private static final Map<String, Command> COMMANDS = new HashMap<>();

    static {
        COMMANDS.put("add", new AddCommand());
        COMMANDS.put("show", new ShowCommand());
        COMMANDS.put("info", new InfoCommand());
        COMMANDS.put("update", new UpdateCommand());
        COMMANDS.put("remove_by_id", new RemoveByIdCommand());
        COMMANDS.put("clear", new ClearCommand());
        COMMANDS.put("add_if_max", new AddIfMaxCommand());
        COMMANDS.put("remove_greater", new RemoveGreaterCommand());
        COMMANDS.put("remove_lower", new RemoveLowerCommand());
        COMMANDS.put("count_less_than_genre", new CountLessThanGenreCommand());
        COMMANDS.put("filter_contains_name", new FilterContainsNameCommand());
        COMMANDS.put("print_field_ascending_number_of_participants",
                new PrintFieldAscendingNumberOfParticipantsCommand());
        COMMANDS.put("get_command_type", new GetCommandTypeCommand());
        COMMANDS.put("register", new RegisterCommand());
    }

    /**
     * Get command by its name
     *
     * @param commandName Command name
     * @return Command
     */
    public static Command getCommand(String commandName) {
        return COMMANDS.get(commandName);
    }

    /**
     * Get command's type (ENTER or PRIMITIVE)
     *
     * @param commandName Command name
     * @return Commands's type
     * @see CommandType
     */
    public static CommandType getCommandType(String commandName) {
        return COMMANDS.get(commandName) == null ? null : COMMANDS.get(commandName).getCommandType();
    }

    /**
     * Get full command's storage
     * @return Command's collection
     */
    public static Collection<Command> getValues() {
        return COMMANDS.values();
    }
}
