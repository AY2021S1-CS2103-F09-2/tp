package seedu.pivot.logic.commands.witnesscommands;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.model.Model.PREDICATE_SHOW_ALL_CASES;

import java.util.List;

import seedu.pivot.commons.core.Messages;
import seedu.pivot.commons.core.index.Index;
import seedu.pivot.logic.commands.CommandResult;
import seedu.pivot.logic.commands.DeleteCommand;
import seedu.pivot.logic.commands.exceptions.CommandException;
import seedu.pivot.model.Model;
import seedu.pivot.model.investigationcase.Case;
import seedu.pivot.model.investigationcase.Witness;

/**
 * Deletes a case identified using it's displayed index from PIVOT.
 */
public class DeleteWitnessCommand extends DeleteCommand {

    public static final String MESSAGE_DELETE_WITNESS_SUCCESS = "Deleted witness: %1$s";

    private final Index caseIndex;
    private final Index witnessIndex;

    /**
     * Creates a DeleteWitnessCommand to delete the witness at specified index, in the case at specified index.
     * @param caseIndex The index of the case to delete the witness.
     * @param witnessIndex The index of the witness to be deleted.
     */
    public DeleteWitnessCommand(Index caseIndex, Index witnessIndex) {
        this.caseIndex = caseIndex;
        this.witnessIndex = witnessIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Case> lastShownList = model.getFilteredCaseList();

        // invalid case index
        if (caseIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CASE_DISPLAYED_INDEX);
        }

        Case stateCase = lastShownList.get(caseIndex.getZeroBased());
        List<Witness> updatedWitnesses = stateCase.getWitnesses();

        // invalid witness index
        if (witnessIndex.getZeroBased() >= updatedWitnesses.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_WITNESS_DISPLAYED_INDEX);
        }

        Witness witnessToDelete = updatedWitnesses.get(witnessIndex.getZeroBased());
        updatedWitnesses.remove(witnessToDelete);

        Case editedCase = new Case(stateCase.getTitle(), stateCase.getDescription(),
                stateCase.getStatus(), stateCase.getDocuments(), stateCase.getSuspects(),
                stateCase.getVictims(), updatedWitnesses, stateCase.getTags());

        model.setCase(stateCase, editedCase);
        model.updateFilteredCaseList(PREDICATE_SHOW_ALL_CASES);

        return new CommandResult(String.format(MESSAGE_DELETE_WITNESS_SUCCESS, witnessToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteWitnessCommand // instanceof handles nulls
                && caseIndex.equals(((DeleteWitnessCommand) other).caseIndex)
                && witnessIndex.equals(((DeleteWitnessCommand) other).witnessIndex)); // state check
    }
}
