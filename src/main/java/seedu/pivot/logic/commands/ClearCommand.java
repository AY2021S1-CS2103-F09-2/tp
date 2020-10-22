package seedu.pivot.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.pivot.model.Model;
import seedu.pivot.model.Pivot;

/**
 * Clears PIVOT.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "PIVOT has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setPivot(new Pivot());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
