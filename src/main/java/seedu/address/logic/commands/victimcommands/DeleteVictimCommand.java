package seedu.address.logic.commands.victimcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CASES;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.investigationcase.Case;
import seedu.address.model.investigationcase.Victim;

/**
 * Deletes a case identified using it's displayed index from PIVOT.
 */
public class DeleteVictimCommand extends DeleteCommand {

    public static final String MESSAGE_DELETE_VICTIM_SUCCESS = "Deleted victim: %1$s";

    private final Index caseIndex;
    private final Index victimIndex;

    /**
     * Creates a DeleteVictimCommand to delete the victim at specified index, in the case at specified index.
     * @param caseIndex The index of the case to delete the victim.
     * @param victimIndex The index of the victim to be deleted.
     */
    public DeleteVictimCommand(Index caseIndex, Index victimIndex) {
        this.caseIndex = caseIndex;
        this.victimIndex = victimIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Case> lastShownList = model.getFilteredCaseList();

        // invalid case index
        if (caseIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CASE_DISPLAYED_INDEX);
        }

        Case caseToEdit = lastShownList.get(caseIndex.getZeroBased());
        List<Victim> updatedVictims = caseToEdit.getVictims();

        // invalid victim index
        if (victimIndex.getZeroBased() >= updatedVictims.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_VICTIM_DISPLAYED_INDEX);
        }

        Victim victimToDelete = updatedVictims.get(victimIndex.getZeroBased());
        updatedVictims.remove(victimToDelete);

        Case editedCase = new Case(caseToEdit.getTitle(), caseToEdit.getDescription(),
                caseToEdit.getStatus(), caseToEdit.getDocuments(), caseToEdit.getSuspects(),
                updatedVictims, caseToEdit.getWitnesses(), caseToEdit.getTags());

        model.setCase(caseToEdit, editedCase);
        model.updateFilteredCaseList(PREDICATE_SHOW_ALL_CASES);

        return new CommandResult(String.format(MESSAGE_DELETE_VICTIM_SUCCESS, victimToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteVictimCommand // instanceof handles nulls
                && caseIndex.equals(((DeleteVictimCommand) other).caseIndex)
                && victimIndex.equals(((DeleteVictimCommand) other).victimIndex)); // state check
    }
}
