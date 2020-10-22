package seedu.pivot.logic.commands.casecommands;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.pivot.logic.commands.AddCommand;
import seedu.pivot.logic.commands.CommandResult;
import seedu.pivot.logic.commands.exceptions.CommandException;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.Model;
import seedu.pivot.model.investigationcase.Case;

/**
 * Adds a case to PIVOT.
 */
public class AddCaseCommand extends AddCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + TYPE_CASE
            + ": Adds a case to PIVOT.\n"
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + "[" + PREFIX_STATUS + "STATUS]\n"
            + "Example: " + COMMAND_WORD + " "
            + TYPE_CASE + " "
            + PREFIX_TITLE + "Bishan Theft "
            + PREFIX_STATUS + "closed";

    public static final String MESSAGE_ADD_CASE_SUCCESS = "New case added: %1$s";
    public static final String MESSAGE_DUPLICATE_CASE = "This case already exists in PIVOT";

    private final Case investigationCase;

    /**
     * Creates an AddCaseCommand to add the specified {@code Case}.
     *
     * @param investigationCase investigation Case to be added to PIVOT.
     */
    public AddCaseCommand(Case investigationCase) {
        requireNonNull(investigationCase);
        this.investigationCase = investigationCase;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        assert(StateManager.atMainPage()) : "Program should be at main page";

        if (model.hasCase(investigationCase)) {
            throw new CommandException(MESSAGE_DUPLICATE_CASE);
        }

        model.addCase(investigationCase);
        return new CommandResult(String.format(MESSAGE_ADD_CASE_SUCCESS, investigationCase));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCaseCommand // instanceof handles nulls
                && investigationCase.equals(((AddCaseCommand) other).investigationCase));
    }
}
