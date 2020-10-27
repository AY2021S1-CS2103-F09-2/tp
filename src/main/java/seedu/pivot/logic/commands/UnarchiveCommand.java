package seedu.pivot.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.pivot.commons.core.LogsCenter;
import seedu.pivot.commons.core.UserMessages;
import seedu.pivot.commons.core.index.Index;
import seedu.pivot.logic.commands.exceptions.CommandException;
import seedu.pivot.model.Model;
import seedu.pivot.model.investigationcase.ArchiveStatus;
import seedu.pivot.model.investigationcase.Case;

/**
 * Unarchives a case identified using it's displayed index from PIVOT.
 */
public class UnarchiveCommand extends Command {
    public static final String COMMAND_WORD = "unarchive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unarchives the case identified by the index number"
            + " used in the displayed list.\n"
            + "Format: '" + COMMAND_WORD + " case INDEX'\n\n"
            + "TYPE 'case'\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " case 1";

    private static final String MESSAGE_ARCHIVE_CASE_SUCCESS = "Case unarchived: %1$s";
    private static final String MESSAGE_ARCHIVE_CASE_NOT_ARCHIVED = "This case has not been archived! "
            + "Type 'list archive' to see all archived cases.";
    private static final Logger logger = LogsCenter.getLogger(UnarchiveCommand.class);

    private Index targetIndex;

    public UnarchiveCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Unarchiving case ...");

        requireNonNull(model);
        List<Case> lastShownList = model.getFilteredCaseList();

        // check case provided is valid index
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(UserMessages.MESSAGE_INVALID_CASE_DISPLAYED_INDEX);
        }

        Case caseToUnarchive = lastShownList.get(targetIndex.getZeroBased());

        // if case has not been archived, should not be able to unarchive it
        if (caseToUnarchive.getArchiveStatus().equals(ArchiveStatus.DEFAULT)) {
            throw new CommandException(MESSAGE_ARCHIVE_CASE_NOT_ARCHIVED);
        }

        Case updatedCase = new Case(caseToUnarchive.getTitle(), caseToUnarchive.getDescription(),
                caseToUnarchive.getStatus(), caseToUnarchive.getDocuments(), caseToUnarchive.getSuspects(),
                caseToUnarchive.getVictims(), caseToUnarchive.getWitnesses(), caseToUnarchive.getTags(),
                ArchiveStatus.DEFAULT);

        model.setCase(caseToUnarchive, updatedCase);
        model.updateFilteredCaseList(Model.PREDICATE_SHOW_ARCHIVED_CASES);
        return new CommandResult(String.format(MESSAGE_ARCHIVE_CASE_SUCCESS, updatedCase));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnarchiveCommand // instanceof handles nulls
                && targetIndex.equals(((UnarchiveCommand) other).targetIndex)); // state check
    }
}
