package seedu.pivot.logic.commands.documentcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.pivot.logic.commands.documentcommands.EditDocumentCommand.MESSAGE_EDIT_DOCUMENT_SUCCESS;
import static seedu.pivot.testutil.Assert.assertThrows;
import static seedu.pivot.testutil.TypicalCases.getTypicalCases;
import static seedu.pivot.testutil.TypicalIndexes.FIRST_INDEX;
import static seedu.pivot.testutil.TypicalIndexes.SECOND_INDEX;
import static seedu.pivot.testutil.TypicalIndexes.THIRD_INDEX;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.pivot.commons.core.UserMessages;
import seedu.pivot.logic.commands.Undoable;
import seedu.pivot.logic.commands.exceptions.CommandException;
import seedu.pivot.logic.commands.testutil.ModelStub;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.investigationcase.Case;
import seedu.pivot.model.investigationcase.Document;
import seedu.pivot.model.investigationcase.Name;
import seedu.pivot.model.investigationcase.Reference;
import seedu.pivot.testutil.CaseBuilder;

public class EditDocumentCommandTest {

    private static final Name DEFAULT_FIRST_NAME = new Name("Test Name");
    private static final Reference DEFAULT_FIRST_REFERENCE = new Reference("editdocumentcommand.txt");
    private static final Name DEFAULT_SECOND_NAME = new Name("another doc");
    private static final Reference DEFAULT_SECOND_REFERENCE = new Reference("anotherReference.pdf");
    private static final Case CASE_WITH_DOCUMENT = new CaseBuilder()
            .withDocument("Test Name", "editdocumentcommand.txt")
            .withDocument("another doc", "anotherReference.pdf")
            .build();


    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditDocumentCommand(null, FIRST_INDEX,
                Optional.of(DEFAULT_FIRST_NAME), Optional.of(DEFAULT_FIRST_REFERENCE)));
        assertThrows(NullPointerException.class, () -> new EditDocumentCommand(FIRST_INDEX, null,
                Optional.of(DEFAULT_FIRST_NAME), Optional.of(DEFAULT_FIRST_REFERENCE)));
        assertThrows(NullPointerException.class, () -> new EditDocumentCommand(FIRST_INDEX, FIRST_INDEX,
                null, Optional.of(DEFAULT_FIRST_REFERENCE)));
        assertThrows(NullPointerException.class, () -> new EditDocumentCommand(FIRST_INDEX, FIRST_INDEX,
                Optional.of(DEFAULT_FIRST_NAME), null));
    }


    @Test
    public void execute_allFieldsSpecified_success() throws CommandException {

        //set up case page
        StateManager.setState(FIRST_INDEX);

        //set up model with case list
        ModelStub modelStub = new ModelStubWithCaseList(getTypicalCases());

        //initialise valid edit command
        EditDocumentCommand command = new EditDocumentCommand(FIRST_INDEX, FIRST_INDEX,
                Optional.of(DEFAULT_FIRST_NAME), Optional.of(DEFAULT_FIRST_REFERENCE));

        //edit document success
        Document editedDocument = new Document(DEFAULT_FIRST_NAME, DEFAULT_FIRST_REFERENCE);

        assertEquals(String.format(MESSAGE_EDIT_DOCUMENT_SUCCESS, editedDocument),
                command.execute(modelStub).getFeedbackToUser());

        StateManager.resetState();
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        //initialise valid edit command
        EditDocumentCommand command = new EditDocumentCommand(FIRST_INDEX, FIRST_INDEX,
                Optional.of(DEFAULT_FIRST_NAME), Optional.of(DEFAULT_FIRST_REFERENCE));

        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void execute_documentIndexOutOfBounds_throwsCommandException() {
        //set up
        StateManager.setState(FIRST_INDEX);
        List<Case> caseList = new ArrayList<>();
        caseList.add(CASE_WITH_DOCUMENT);
        ModelStub modelStub = new ModelStubWithCaseList(caseList);

        //initialise command
        EditDocumentCommand command = new EditDocumentCommand(FIRST_INDEX, THIRD_INDEX,
                Optional.of(DEFAULT_FIRST_NAME), Optional.of(DEFAULT_FIRST_REFERENCE));

        assertThrows(CommandException.class, UserMessages.MESSAGE_INVALID_DOCUMENT_DISPLAYED_INDEX, (
                ) -> command.execute(modelStub));

        StateManager.resetState();
    }

    @Test
    public void execute_duplicateDocument_throwsCommandException() {
        //set up
        StateManager.setState(FIRST_INDEX);
        List<Case> caseList = new ArrayList<>();
        caseList.add(CASE_WITH_DOCUMENT);
        ModelStub modelStub = new ModelStubWithCaseList(caseList);

        //initialise command
        //edits second document to become the first
        EditDocumentCommand command = new EditDocumentCommand(FIRST_INDEX, SECOND_INDEX,
                Optional.of(DEFAULT_FIRST_NAME), Optional.of(DEFAULT_FIRST_REFERENCE));

        assertThrows(CommandException.class,
                UserMessages.MESSAGE_DUPLICATE_DOCUMENT, () -> command.execute(modelStub));

        StateManager.resetState();

    }

    @Test
    public void execute_editNameOnly_success() throws CommandException {
        //set up
        StateManager.setState(FIRST_INDEX);
        List<Case> caseList = new ArrayList<>();
        caseList.add(CASE_WITH_DOCUMENT);
        ModelStub modelStub = new ModelStubWithCaseList(caseList);

        //initialise command
        //edits second document name to become the first document name
        EditDocumentCommand command = new EditDocumentCommand(FIRST_INDEX, SECOND_INDEX,
                Optional.of(DEFAULT_FIRST_NAME), Optional.empty());

        //edit document success
        Document editedDocument = new Document(DEFAULT_FIRST_NAME, DEFAULT_SECOND_REFERENCE);

        assertEquals(String.format(MESSAGE_EDIT_DOCUMENT_SUCCESS, editedDocument),
                command.execute(modelStub).getFeedbackToUser());

        StateManager.resetState();
    }

    @Test
    public void execute_editReferenceOnly_success() throws CommandException {
        //set up
        StateManager.setState(FIRST_INDEX);
        List<Case> caseList = new ArrayList<>();
        caseList.add(CASE_WITH_DOCUMENT);
        ModelStub modelStub = new ModelStubWithCaseList(caseList);

        //initialise command
        //edits second document reference to become the first document reference
        EditDocumentCommand command = new EditDocumentCommand(FIRST_INDEX, SECOND_INDEX,
                 Optional.empty(), Optional.of(DEFAULT_FIRST_REFERENCE));

        //edit document success
        Document editedDocument = new Document(DEFAULT_SECOND_NAME, DEFAULT_FIRST_REFERENCE);

        assertEquals(String.format(MESSAGE_EDIT_DOCUMENT_SUCCESS, editedDocument),
                command.execute(modelStub).getFeedbackToUser());

        StateManager.resetState();
    }

    /**
     * A Model stub that holds a caseList.
     */
    private class ModelStubWithCaseList extends ModelStub {
        private final List<Case> caseList;

        private ModelStubWithCaseList(List<Case> caseList) {
            this.caseList = caseList;
        }

        @Override
        public ObservableList<Case> getFilteredCaseList() {
            return FXCollections.observableList(caseList);
        }

        @Override
        public void setCase(Case target, Case editedCase) {
            this.caseList.set(caseList.indexOf(target), editedCase);
        }

        @Override
        public void updateFilteredCaseList(Predicate<Case> predicate) {
            return;
        }

        @Override
        public void commitPivot(String commandMessage, Undoable command) {}
    }

}
