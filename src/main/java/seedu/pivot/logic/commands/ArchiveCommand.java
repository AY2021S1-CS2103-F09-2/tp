package seedu.pivot.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_MAIN_PAGE;

import java.util.List;
import java.util.logging.Logger;

import seedu.pivot.commons.core.LogsCenter;
import seedu.pivot.commons.core.UserMessages;
import seedu.pivot.commons.core.index.Index;
import seedu.pivot.logic.commands.exceptions.CommandException;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.Model;
import seedu.pivot.model.investigationcase.ArchiveStatus;
import seedu.pivot.model.investigationcase.Case;

/**
 * Archives a case identified using it's displayed index from PIVOT.
 */
public class ArchiveCommand extends Command {
    public static final String COMMAND_WORD = "archive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Archives the case identified by the index number"
            + " used in the displayed list.\n"
            + "Format: '" + COMMAND_WORD + " case PARAMETERS'\n\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " case 1";

    private static final String MESSAGE_ARCHIVE_CASE_SUCCESS = "Case archived: %1$s";
    private static final String MESSAGE_ARCHIVE_CASE_ALREADY_ARCHIVED = "This case has already been archived! "
            + "Type 'list case' to see all unarchived cases.";
    private static final Logger logger = LogsCenter.getLogger(ArchiveCommand.class);

    private Index targetIndex;

    public ArchiveCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Archiving case ...");

        assert(StateManager.atMainPage()) : ASSERT_MAIN_PAGE;

        requireNonNull(model);
        List<Case> lastShownList = model.getFilteredCaseList();

        // check case provided is valid index
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(UserMessages.MESSAGE_INVALID_CASE_DISPLAYED_INDEX);
        }

        // if case is already archived, throw error
        Case caseToArchive = lastShownList.get(targetIndex.getZeroBased());
        if (caseToArchive.getArchiveStatus().equals(ArchiveStatus.ARCHIVED)) {
            throw new CommandException(MESSAGE_ARCHIVE_CASE_ALREADY_ARCHIVED);
        }

        Case updatedCase = new Case(caseToArchive.getTitle(), caseToArchive.getDescription(), caseToArchive.getStatus(),
                caseToArchive.getDocuments(), caseToArchive.getSuspects(), caseToArchive.getVictims(),
                caseToArchive.getWitnesses(), caseToArchive.getTags(), ArchiveStatus.ARCHIVED);

        model.setCase(caseToArchive, updatedCase);
        model.updateFilteredCaseList(Model.PREDICATE_SHOW_ALL_CASES);
        return new CommandResult(String.format(MESSAGE_ARCHIVE_CASE_SUCCESS, updatedCase));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ArchiveCommand // instanceof handles nulls
                && targetIndex.equals(((ArchiveCommand) other).targetIndex)); // state check
    }
}
