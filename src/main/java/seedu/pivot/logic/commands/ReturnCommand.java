package seedu.pivot.logic.commands;

import java.util.logging.Logger;

import seedu.pivot.commons.core.LogsCenter;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.Model;

/**
 * Returns the program to the Main Page.
 */
public class ReturnCommand extends Command {

    public static final String COMMAND_WORD = "return";

    public static final String MESSAGE_RETURN_SUCCESS = "You are now at the Main Page! Type 'open case [INDEX]' "
            + "to open a case!";

    private static final Logger logger = LogsCenter.getLogger(ReturnCommand.class);

    @Override
    public CommandResult execute(Model model) {
        logger.info("Returning back to main page...");

        StateManager.resetState();

        return new CommandResult(MESSAGE_RETURN_SUCCESS);
    }
}
