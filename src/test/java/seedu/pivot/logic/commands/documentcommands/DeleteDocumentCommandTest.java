package seedu.pivot.logic.commands.documentcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pivot.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.pivot.commons.core.UserMessages;
import seedu.pivot.commons.core.index.Index;
import seedu.pivot.logic.commands.DeleteCommand;
import seedu.pivot.logic.commands.Undoable;
import seedu.pivot.logic.commands.exceptions.CommandException;
import seedu.pivot.logic.commands.testutil.ModelStub;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.investigationcase.Case;
import seedu.pivot.model.investigationcase.Document;
import seedu.pivot.model.investigationcase.Name;
import seedu.pivot.model.investigationcase.Reference;
import seedu.pivot.testutil.CaseBuilder;



public class DeleteDocumentCommandTest {
    private static final Name DEFAULT_NAME = new Name("Test Name");
    private static final Reference DEFAULT_REFERENCE = new Reference("test1.txt");
    private static final Document DEFAULT_DOCUMENT = new Document(DEFAULT_NAME, DEFAULT_REFERENCE);
    private static final Index DEFAULT_CASE_INDEX = Index.fromZeroBased(0);
    private static final Index DEFAULT_DOC_INDEX = Index.fromZeroBased(0);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteDocumentCommand(null, null));
        assertThrows(NullPointerException.class, () -> new DeleteDocumentCommand(null, DEFAULT_DOC_INDEX));
        assertThrows(NullPointerException.class, () -> new DeleteDocumentCommand(DEFAULT_CASE_INDEX, null));
    }

    @Test
    public void equals() {
        Index alternateCaseIndex = Index.fromZeroBased(1000);
        Index alternateDocIndex = Index.fromZeroBased(1000);
        DeleteCommand command = new DeleteDocumentCommand(DEFAULT_CASE_INDEX, DEFAULT_DOC_INDEX);

        // same object -> returns true
        assertTrue(command.equals(command));

        // same values -> returns true
        assertTrue(command.equals(new DeleteDocumentCommand(DEFAULT_CASE_INDEX, DEFAULT_DOC_INDEX)));

        // different types -> returns false
        assertFalse(command.equals(1));

        // null -> returns false
        assertFalse(command.equals(null));

        // different person -> returns false
        assertFalse(command.equals(new DeleteDocumentCommand(alternateCaseIndex, alternateDocIndex)));
    }

    @Test
    public void execute_documentDeletedByModel_success() throws CommandException {
        StateManager.setState(DEFAULT_CASE_INDEX);
        Case testCase = new CaseBuilder()
                .withDocument(DEFAULT_NAME.getAlphaNum(), DEFAULT_REFERENCE.getFileName()).build();
        List<Case> caseList = new ArrayList<>();
        caseList.add(testCase);

        ModelStub modelStub = new ModelStubWithCaseList(caseList);
        DeleteCommand command = new DeleteDocumentCommand(DEFAULT_CASE_INDEX, DEFAULT_DOC_INDEX);
        assertEquals(String.format(DeleteDocumentCommand.MESSAGE_DELETE_DOCUMENT_SUCCESS, DEFAULT_DOCUMENT),
                command.execute(modelStub).getFeedbackToUser());
        StateManager.resetState();
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        StateManager.setState(DEFAULT_CASE_INDEX);
        Case testCase = new CaseBuilder().build();
        List<Case> caseList = new ArrayList<>();
        caseList.add(testCase);

        Index invalidIndex = Index.fromZeroBased(1000);
        ModelStub modelStub = new ModelStubWithCaseList(caseList);
        DeleteCommand command = new DeleteDocumentCommand(DEFAULT_CASE_INDEX, invalidIndex);
        assertThrows(CommandException.class,
                UserMessages.MESSAGE_INVALID_DOCUMENT_DISPLAYED_INDEX, () -> command.execute(modelStub));
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
