package seedu.address.logic.commands;

/**
 * Adds a case to PIVOT.
 */
public abstract class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    // Todo: Refine the description of message usage
    public static final String MESSAGE_USAGE_MAIN_PAGE = COMMAND_WORD
            + ": Adds an item of a specified type to PIVOT.\n"
            + "Parameters (for 'case' TYPE): TYPE t:TITLE\n"
            + "Example: " + COMMAND_WORD + " case t: Ang Mo Kio Murders";

    // Todo: Refine the description of message usage
    public static final String MESSAGE_USAGE_CASE_PAGE = COMMAND_WORD
            + ": Adds an item of a specified type to opened case in PIVOT.\n"
            + "Format: '" + COMMAND_WORD + " TYPE PARAMETERS'\n\n"
            + "TYPE 'suspect','victim','witness'\n"
            + "Parameters: n:NAME\n"
            + "Example: " + COMMAND_WORD + " suspect n:John\n\n"
            + "TYPE 'doc'\n"
            + "Parameters: n:NAME r:REFERENCE\n"
            + "Example: " + COMMAND_WORD + " doc n:Evidence r:text1.txt\n\n"
            + "TYPE 'desc'\n"
            + "Parameters: d:DESC \n"
            + "Example: " + COMMAND_WORD + " desc d:7 caught for rioting";
}
