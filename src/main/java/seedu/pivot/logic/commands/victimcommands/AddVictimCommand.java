package seedu.pivot.logic.commands.victimcommands;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.pivot.model.Model.PREDICATE_SHOW_ALL_CASES;

import java.util.List;

import seedu.pivot.commons.core.index.Index;
import seedu.pivot.logic.commands.AddCommand;
import seedu.pivot.logic.commands.CommandResult;
import seedu.pivot.logic.commands.exceptions.CommandException;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.Model;
import seedu.pivot.model.investigationcase.Case;
import seedu.pivot.model.investigationcase.Victim;

public class AddVictimCommand extends AddCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + TYPE_VICTIM
            + ": Adds a victim to current case in PIVOT. "
            + "Parameters: "
            + PREFIX_NAME + "NAME \n"
            + "Example: " + COMMAND_WORD + " " + TYPE_VICTIM + " "
            + PREFIX_NAME + "John Doe ";

    public static final String MESSAGE_ADD_VICTIM_SUCCESS = "New victim added: %1$s";
    public static final String MESSAGE_DUPLICATE_VICTIM = "This victim already exists in the case";

    private final Index index;
    private final Victim victim;

    /**
     * Creates an AddVictimCommand to add the specified {@code Case}
     *
     * @param victim The victim to be added.
     */
    public AddVictimCommand(Index index, Victim victim) {
        requireNonNull(index);
        requireNonNull(victim);
        this.index = index;
        this.victim = victim;

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Case> lastShownList = model.getFilteredCaseList();

        //check for valid index
        assert(StateManager.atCasePage()) : "Program should be at case page";
        assert(index.getZeroBased() < lastShownList.size()) : "index should be valid";

        Case stateCase = lastShownList.get(index.getZeroBased());
        List<Victim> updatedVictims = stateCase.getVictims();

        if (updatedVictims.contains(victim)) {
            throw new CommandException(MESSAGE_DUPLICATE_VICTIM);
        }

        updatedVictims.add(victim);

        Case updatedCase = new Case(stateCase.getTitle(), stateCase.getDescription(),
                stateCase.getStatus(), stateCase.getDocuments(), stateCase.getSuspects(),
                updatedVictims, stateCase.getWitnesses(), stateCase.getTags());

        model.setCase(stateCase, updatedCase);
        model.updateFilteredCaseList(PREDICATE_SHOW_ALL_CASES);

        return new CommandResult(String.format(MESSAGE_ADD_VICTIM_SUCCESS, victim));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddVictimCommand // instanceof handles nulls
                && victim.equals(((AddVictimCommand) other).victim)
                && index.equals(((AddVictimCommand) other).index));
    }
}
