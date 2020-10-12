package seedu.address.logic.commands;

/**
 * Lists all cases in the address book to the user.
 */
public abstract class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all items of a specified type.\n"
            + "Parameters: TYPE\n"
            + "Example: " + COMMAND_WORD + " case";

}
