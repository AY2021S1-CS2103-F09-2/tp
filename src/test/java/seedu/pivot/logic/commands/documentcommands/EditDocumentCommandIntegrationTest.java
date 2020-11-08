package seedu.pivot.logic.commands.documentcommands;

import static seedu.pivot.logic.commands.documentcommands.EditDocumentCommand.MESSAGE_EDIT_DOCUMENT_SUCCESS;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.assertCommandFailure;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.assertCommandSuccess;
import static seedu.pivot.testutil.TypicalCases.JANICE;
import static seedu.pivot.testutil.TypicalCases.PETER;
import static seedu.pivot.testutil.TypicalCases.TOM;
import static seedu.pivot.testutil.TypicalCases.getTypicalPivot;
import static seedu.pivot.testutil.TypicalIndexes.FIRST_INDEX;
import static seedu.pivot.testutil.TypicalIndexes.SECOND_INDEX;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.pivot.commons.core.UserMessages;
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

public class EditDocumentCommandIntegrationTest {

    private static final Name DEFAULT_NAME = new Name("EditDocumentCommandIntegrationTest");
    private static final Reference DEFAULT_REFERENCE = new Reference("EditDocumentCommandIntegrationTest.txt");
    private static final Name FIRST_TYPICAL_CASE_NAME = new Name("name");
    private static final Reference FIRST_TYPICAL_CASE_REFERENCE = new Reference("validButShouldNotExist.txt");
    private Model model;

    @Test
    public void execute_allFieldsSpecified_success() {
        //set up
        StateManager.setState(FIRST_INDEX);
        model = new ModelManager(getTypicalPivot(), new UserPrefs());

        EditDocumentCommand command = new EditDocumentCommand(FIRST_INDEX, FIRST_INDEX,
                Optional.of(DEFAULT_NAME), Optional.of(DEFAULT_REFERENCE));

        //build expected first case
        Case expectedCase = new CaseBuilder().withTitle("Alice Pauline Assault")
                .withDocument(DEFAULT_NAME.getAlphaNum(), DEFAULT_REFERENCE.getFileName())
                .withStatus("COLD")
                .addVictims(TOM)
                .addWitnesses(JANICE)
                .addSuspects(PETER)
                .withTags("friends")
                .build();

        //expected document
        Document editedDocument = new Document(DEFAULT_NAME, DEFAULT_REFERENCE);
        String expectedMessage = String.format(MESSAGE_EDIT_DOCUMENT_SUCCESS, editedDocument);

        //get expected model
        Case caseToEdit = model.getFilteredCaseList().get(FIRST_INDEX.getZeroBased());
        Model expectedModel = new ModelManager(new Pivot(model.getPivot()), new UserPrefs());
        expectedModel.setCase(caseToEdit, expectedCase);
        expectedModel.commitPivot(expectedMessage, command);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        StateManager.resetState();
    }


    @Test
    public void execute_duplicateDocument_throwsCommandException() {
        //set up
        StateManager.setState(FIRST_INDEX);
        model = new ModelManager(getTypicalPivot(), new UserPrefs());

        //add document to first case for testing
        Case caseToUpdate = model.getFilteredCaseList().get(FIRST_INDEX.getZeroBased());
        Case addDocumentToCase = new CaseBuilder(caseToUpdate).withDocument(DEFAULT_NAME.getAlphaNum(),
                DEFAULT_REFERENCE.getFileName()).build();
        model.setCase(caseToUpdate, addDocumentToCase);


        //initialise command
        //edits second document to become the first
        EditDocumentCommand command = new EditDocumentCommand(FIRST_INDEX, SECOND_INDEX,
                Optional.of(FIRST_TYPICAL_CASE_NAME), Optional.of(FIRST_TYPICAL_CASE_REFERENCE));

        assertCommandFailure(command, model, UserMessages.MESSAGE_DUPLICATE_DOCUMENT);

        StateManager.resetState();
    }

    @Test
    public void execute_documentIndexOutOfBounds_throwsCommandException() {
        //set up
        StateManager.setState(FIRST_INDEX);
        model = new ModelManager(getTypicalPivot(), new UserPrefs());

        //initialise command
        EditDocumentCommand command = new EditDocumentCommand(FIRST_INDEX, SECOND_INDEX,
                Optional.of(DEFAULT_NAME), Optional.of(DEFAULT_REFERENCE));

        assertCommandFailure(command, model, UserMessages.MESSAGE_INVALID_DOCUMENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_editNameOnly_success() {
        //set up
        StateManager.setState(FIRST_INDEX);
        model = new ModelManager(getTypicalPivot(), new UserPrefs());
        Model expectedModel = new ModelManager(new Pivot(model.getPivot()), new UserPrefs());

        //add document to first case for testing
        Case caseToUpdate = model.getFilteredCaseList().get(FIRST_INDEX.getZeroBased());
        Case addDocumentToCase = new CaseBuilder(caseToUpdate).withDocument(DEFAULT_NAME.getAlphaNum(),
                DEFAULT_REFERENCE.getFileName()).build();
        model.setCase(caseToUpdate, addDocumentToCase);

        //initialise command
        //edits second document name to become the first document name
        EditDocumentCommand command = new EditDocumentCommand(FIRST_INDEX, SECOND_INDEX,
                Optional.of(FIRST_TYPICAL_CASE_NAME), Optional.empty());

        //expected document updated
        Document expectedDocument = new Document(FIRST_TYPICAL_CASE_NAME, DEFAULT_REFERENCE);
        String expectedMessage = String.format(MESSAGE_EDIT_DOCUMENT_SUCCESS, expectedDocument);

        //build expected model
        Case expectedCaseToUpdate = expectedModel.getFilteredCaseList().get(FIRST_INDEX.getZeroBased());
        Case expectedEditDocumentToCase = new CaseBuilder(caseToUpdate)
                .withDocument(FIRST_TYPICAL_CASE_NAME.getAlphaNum(),
                DEFAULT_REFERENCE.getFileName()).build();
        expectedModel.setCase(expectedCaseToUpdate, expectedEditDocumentToCase);
        expectedModel.commitPivot(expectedMessage, command);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        StateManager.resetState();

    }

    @Test
    public void execute_editReferenceOnly_success() {
        //set up
        StateManager.setState(FIRST_INDEX);
        model = new ModelManager(getTypicalPivot(), new UserPrefs());
        Model expectedModel = new ModelManager(new Pivot(model.getPivot()), new UserPrefs());

        //add document to first case for testing
        Case caseToUpdate = model.getFilteredCaseList().get(FIRST_INDEX.getZeroBased());
        Case addDocumentToCase = new CaseBuilder(caseToUpdate).withDocument(DEFAULT_NAME.getAlphaNum(),
                DEFAULT_REFERENCE.getFileName()).build();
        model.setCase(caseToUpdate, addDocumentToCase);

        //initialise command
        //edits second document reference to become the first document reference
        EditDocumentCommand command = new EditDocumentCommand(FIRST_INDEX, SECOND_INDEX,
                Optional.empty(), Optional.of(FIRST_TYPICAL_CASE_REFERENCE));

        //expected document updated
        Document expectedDocument = new Document(DEFAULT_NAME, FIRST_TYPICAL_CASE_REFERENCE);
        String expectedMessage = String.format(MESSAGE_EDIT_DOCUMENT_SUCCESS, expectedDocument);

        //build expected model
        Case expectedCaseToUpdate = expectedModel.getFilteredCaseList().get(FIRST_INDEX.getZeroBased());
        Case expectedEditDocumentToCase = new CaseBuilder(caseToUpdate)
                .withDocument(DEFAULT_NAME.getAlphaNum(),
                        FIRST_TYPICAL_CASE_REFERENCE.getFileName()).build();
        expectedModel.setCase(expectedCaseToUpdate, expectedEditDocumentToCase);
        expectedModel.commitPivot(expectedMessage, command);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        StateManager.resetState();

    }

}
