package seedu.pivot.logic.commands.suspectcommands;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.model.Model.PREDICATE_SHOW_ALL_CASES;

import java.util.List;

import seedu.pivot.commons.core.Messages;
import seedu.pivot.commons.core.index.Index;
import seedu.pivot.logic.commands.CommandResult;
import seedu.pivot.logic.commands.DeleteCommand;
import seedu.pivot.logic.commands.exceptions.CommandException;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.Model;
import seedu.pivot.model.investigationcase.Case;
import seedu.pivot.model.investigationcase.Suspect;

public class DeleteSuspectCommand extends DeleteCommand {

    public static final String MESSAGE_DELETE_SUSPECT_SUCCESS = "Deleted Suspect: %1$s";

    private final Index caseIndex;
    private final Index suspectIndex;

    /**
     * Creates a DeleteSuspectCommand to delete the suspect at specified index, in the case at specified index.
     * @param caseIndex The index of the case to delete the suspect.
     * @param suspectIndex The index of the suspect to be deleted.
     */
    public DeleteSuspectCommand(Index caseIndex, Index suspectIndex) {
        this.caseIndex = caseIndex;
        this.suspectIndex = suspectIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Case> lastShownList = model.getFilteredCaseList();

        assert(StateManager.atCasePage()) : "Program should be at case page";
        assert(caseIndex.getZeroBased() < lastShownList.size()) : "index should be valid";

        Case openCase = lastShownList.get(caseIndex.getZeroBased());
        List<Suspect> updatedSuspects = openCase.getSuspects();

        if (suspectIndex.getZeroBased() >= updatedSuspects.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUSPECTS_DISPLAYED_INDEX);
        }

        Suspect suspectToDelete = updatedSuspects.get(suspectIndex.getZeroBased());
        updatedSuspects.remove(suspectIndex.getZeroBased());
        Case updatedCase = new Case(openCase.getTitle(), openCase.getDescription(), openCase.getStatus(),
                openCase.getDocuments(), updatedSuspects, openCase.getVictims(), openCase.getWitnesses(),
                openCase.getTags());

        model.setCase(openCase, updatedCase);
        model.updateFilteredCaseList(PREDICATE_SHOW_ALL_CASES);
        return new CommandResult(String.format(MESSAGE_DELETE_SUSPECT_SUCCESS, suspectToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteSuspectCommand // instanceof handles nulls
                && caseIndex.equals(((DeleteSuspectCommand) other).caseIndex)
                && suspectIndex.equals(((DeleteSuspectCommand) other).suspectIndex)); // state check
    }
}
