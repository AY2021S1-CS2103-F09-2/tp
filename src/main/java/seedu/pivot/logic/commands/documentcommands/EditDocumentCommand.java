package seedu.pivot.logic.commands.documentcommands;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_CASE_PAGE;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_VALID_INDEX;
import static seedu.pivot.commons.core.UserMessages.MESSAGE_DUPLICATE_DOCUMENT;
import static seedu.pivot.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_REFERENCE;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.pivot.commons.core.LogsCenter;
import seedu.pivot.commons.core.UserMessages;
import seedu.pivot.commons.core.index.Index;
import seedu.pivot.logic.commands.CommandResult;
import seedu.pivot.logic.commands.EditCommand;
import seedu.pivot.logic.commands.Page;
import seedu.pivot.logic.commands.Undoable;
import seedu.pivot.logic.commands.exceptions.CommandException;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.Model;
import seedu.pivot.model.investigationcase.Case;
import seedu.pivot.model.investigationcase.Document;
import seedu.pivot.model.investigationcase.Name;
import seedu.pivot.model.investigationcase.Reference;

/**
 * Represents an Edit command for editing a Document in a Case in PIVOT.
 */
public class EditDocumentCommand extends EditCommand implements Undoable {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + TYPE_DOC
            + ": Edits the document of the opened case at the specified index.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_REFERENCE + "REFERENCE]\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_DOC + " " + "1" + " "
            + PREFIX_NAME + "new name "
            + PREFIX_REFERENCE + "edited.txt";


    public static final String MESSAGE_EDIT_DOCUMENT_SUCCESS = "Edited document: %1$s";

    private static final Page pageType = Page.CASE;
    private static final Logger logger = LogsCenter.getLogger(EditDocumentCommand.class);

    private final Index index;
    private final Index documentIndex;
    private final Optional<Name> name;
    private final Optional<Reference> reference;


    /**
     * Creates an EditDocumentCommand to edit a {@code Document} at index @code docIndex} with the
     * name and reference if present, from the case at index {@code caseIndex}.
     *
     * @param caseIndex index of a case in the list.
     * @param docIndex document index in the document list.
     * @param name an {@code Optional<Name>} to update the document.
     * @param reference an {@code Optional<Reference>} to update the document.
     */
    public EditDocumentCommand(Index caseIndex, Index docIndex, Optional<Name> name, Optional<Reference> reference) {
        requireAllNonNull(caseIndex, docIndex, name, reference);
        this.index = caseIndex;
        this.documentIndex = docIndex;
        this.name = name;
        this.reference = reference;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Editing document of current case...");
        requireNonNull(model);

        List<Case> lastShownList = model.getFilteredCaseList();

        //check for valid index
        assert(StateManager.atCasePage()) : ASSERT_CASE_PAGE;
        assert(index.getZeroBased() < lastShownList.size()) : ASSERT_VALID_INDEX;

        //get current case in state
        Case stateCase = lastShownList.get(index.getZeroBased());
        List<Document> documents = stateCase.getDocuments();

        //document index validation in model
        if (documentIndex.getZeroBased() >= documents.size()) {
            logger.info("Invalid index: " + documentIndex.getOneBased());
            throw new CommandException(UserMessages.MESSAGE_INVALID_DOCUMENT_DISPLAYED_INDEX);
        }

        //get document
        Document documentToEdit = documents.get(documentIndex.getZeroBased());

        //edit document in case
        Document editedDocument =
                new Document(name.orElse(documentToEdit.getName()), reference.orElse(documentToEdit.getReference()));

        //check for duplicate
        if (documents.contains(editedDocument)) {
            logger.warning("Failed to edit document: Edited to a document that exists in PIVOT");
            throw new CommandException(MESSAGE_DUPLICATE_DOCUMENT);
        }

        documents.set(documentIndex.getZeroBased(), editedDocument);

        Case updatedCase = new Case(stateCase.getTitle(), stateCase.getDescription(),
                stateCase.getStatus(), documents, stateCase.getSuspects(),
                stateCase.getVictims(), stateCase.getWitnesses(), stateCase.getTags(), stateCase.getArchiveStatus());

        //update model
        model.setCase(stateCase, updatedCase);
        model.commitPivot(String.format(MESSAGE_EDIT_DOCUMENT_SUCCESS, editedDocument), this);

        return new CommandResult(String.format(MESSAGE_EDIT_DOCUMENT_SUCCESS, editedDocument));
    }

    @Override
    public Page getPage() {
        return pageType;
    }
}
