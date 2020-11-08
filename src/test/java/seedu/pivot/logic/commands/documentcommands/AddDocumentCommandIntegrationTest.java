package seedu.pivot.logic.commands.documentcommands;

import static seedu.pivot.logic.commands.testutil.CommandTestUtil.assertCommandFailure;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.assertCommandSuccess;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.showCaseAtIndex;
import static seedu.pivot.testutil.TypicalCases.getTypicalPivot;
import static seedu.pivot.testutil.TypicalIndexes.FIRST_INDEX;

import org.junit.jupiter.api.Test;

import seedu.pivot.commons.core.UserMessages;
import seedu.pivot.logic.commands.AddCommand;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.Model;
import seedu.pivot.model.ModelManager;
import seedu.pivot.model.Pivot;
import seedu.pivot.model.UserPrefs;
import seedu.pivot.model.investigationcase.Case;
import seedu.pivot.model.investigationcase.Document;
import seedu.pivot.model.investigationcase.Name;
import seedu.pivot.model.investigationcase.Reference;
import seedu.pivot.testutil.CaseBuilder;


public class AddDocumentCommandIntegrationTest {
    private static final Name DEFAULT_NAME = new Name("Test Name");
    private static final Reference DEFAULT_REFERENCE = new Reference("test1.txt");
    private static final Document DEFAULT_DOCUMENT = new Document(DEFAULT_NAME, DEFAULT_REFERENCE);
    private Model model = new ModelManager(getTypicalPivot(), new UserPrefs());

    @Test
    public void execute_validIndexAndValidDocumentUnfilteredList_success() {
        StateManager.setState(FIRST_INDEX);
        Case caseToUpdate = model.getFilteredCaseList().get(FIRST_INDEX.getZeroBased());
        // CASE: ALICE, WITHOUT DOCUMENT
        Case expectedCase = new CaseBuilder(caseToUpdate).withDocument(DEFAULT_NAME.getAlphaNum(),
                DEFAULT_REFERENCE.getFileName()).build();

        AddDocumentCommand command = new AddDocumentCommand(FIRST_INDEX, DEFAULT_DOCUMENT);

        String expectedMessage = String.format(AddDocumentCommand.MESSAGE_ADD_DOCUMENT_SUCCESS, DEFAULT_DOCUMENT);
        ModelManager expectedModel = new ModelManager(new Pivot(model.getPivot()), new UserPrefs());
        expectedModel.setCase(caseToUpdate, expectedCase);
        expectedModel.commitPivot(expectedMessage, command);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        StateManager.resetState();
    }

    @Test
    public void execute_validIndexAndDuplicateDocumentUnfilteredList_throwsCommandException() {
        StateManager.setState(FIRST_INDEX);
        Case caseToUpdate = model.getFilteredCaseList().get(FIRST_INDEX.getZeroBased());
        // CASE: ALICE, WITHOUT DESCRIPTIONS
        Document document = caseToUpdate.getDocuments().get(0);

        AddCommand command = new AddDocumentCommand(FIRST_INDEX, document);

        assertCommandFailure(command, model, UserMessages.MESSAGE_DUPLICATE_DOCUMENT);
        StateManager.resetState();
    }

    @Test
    public void execute_validIndexAndValidDocumentFilteredList_success() {
        showCaseAtIndex(model, FIRST_INDEX);
        StateManager.setState(FIRST_INDEX);
        Case caseToUpdate = model.getFilteredCaseList().get(FIRST_INDEX.getZeroBased());
        // CASE: ALICE, WITHOUT DOCUMENT
        Case expectedCase = new CaseBuilder(caseToUpdate).withDocument(DEFAULT_NAME.getAlphaNum(),
                DEFAULT_REFERENCE.getFileName()).build();

        AddDocumentCommand command = new AddDocumentCommand(FIRST_INDEX, DEFAULT_DOCUMENT);

        String expectedMessage = String.format(AddDocumentCommand.MESSAGE_ADD_DOCUMENT_SUCCESS, DEFAULT_DOCUMENT);
        ModelManager expectedModel = new ModelManager(new Pivot(model.getPivot()), new UserPrefs());
        showCaseAtIndex(expectedModel, FIRST_INDEX);
        expectedModel.setCase(caseToUpdate, expectedCase);
        expectedModel.commitPivot(expectedMessage, command);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        StateManager.resetState();
    }

    @Test
    public void execute_validIndexAndDuplicateDocumentFilteredList_throwsCommandException() {
        showCaseAtIndex(model, FIRST_INDEX);
        StateManager.setState(FIRST_INDEX);
        Case caseToUpdate = model.getFilteredCaseList().get(FIRST_INDEX.getZeroBased());
        // CASE: ALICE, WITHOUT DESCRIPTIONS
        Document document = caseToUpdate.getDocuments().get(0);

        AddCommand command = new AddDocumentCommand(FIRST_INDEX, document);

        assertCommandFailure(command, model, UserMessages.MESSAGE_DUPLICATE_DOCUMENT);
        StateManager.resetState();
    }
}
