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
import seedu.pivot.logic.commands.AddCommand;
import seedu.pivot.logic.commands.Undoable;
import seedu.pivot.logic.commands.exceptions.CommandException;
import seedu.pivot.logic.commands.testutil.ModelStub;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.investigationcase.Case;
import seedu.pivot.model.investigationcase.Document;
import seedu.pivot.model.investigationcase.Name;
import seedu.pivot.model.investigationcase.Reference;
import seedu.pivot.testutil.CaseBuilder;

public class AddDocumentCommandTest {
    private static final Name DEFAULT_NAME = new Name("Test Name");
    private static final Reference DEFAULT_REFERENCE = new Reference("test1.txt");
    private static final Document DEFAULT_DOCUMENT = new Document(DEFAULT_NAME, DEFAULT_REFERENCE);
    private static final Index DEFAULT_INDEX = Index.fromZeroBased(0);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddDocumentCommand(null, null));
        assertThrows(NullPointerException.class, () -> new AddDocumentCommand(null, DEFAULT_DOCUMENT));
        assertThrows(NullPointerException.class, () -> new AddDocumentCommand(DEFAULT_INDEX, null));
    }

    @Test
    public void equals() {
        Document alternateDoc = new Document(new Name("Random Name"), new Reference("Random Ref"));
        Index alternateIndex = Index.fromZeroBased(1000);

        // same object -> returns true
        AddCommand testCommand = new AddDocumentCommand(DEFAULT_INDEX, DEFAULT_DOCUMENT);
        assertTrue(testCommand.equals(testCommand));

        // same values -> returns true
        assertTrue(testCommand.equals(new AddDocumentCommand(DEFAULT_INDEX, DEFAULT_DOCUMENT)));

        // different types -> returns false
        assertFalse(testCommand.equals(1));

        // null -> returns false
        assertFalse(testCommand.equals(null));

        // different person -> returns false
        assertFalse(testCommand.equals(new AddDocumentCommand(DEFAULT_INDEX, alternateDoc)));
        assertFalse(testCommand.equals(new AddDocumentCommand(alternateIndex, DEFAULT_DOCUMENT)));
        assertFalse(testCommand.equals(new AddDocumentCommand(alternateIndex, alternateDoc)));
    }

    @Test
    public void execute_validDocument_success() throws CommandException {
        StateManager.setState(DEFAULT_INDEX);
        Case testCase = new CaseBuilder().build();
        List<Case> caseList = new ArrayList<>();
        caseList.add(testCase);

        ModelStub modelStub = new ModelStubWithCaseList(caseList);
        AddCommand command = new AddDocumentCommand(DEFAULT_INDEX, DEFAULT_DOCUMENT);
        assertEquals(String.format(AddDocumentCommand.MESSAGE_ADD_DOCUMENT_SUCCESS, DEFAULT_DOCUMENT),
                command.execute(modelStub).getFeedbackToUser());

        StateManager.resetState();
    }

    @Test
    public void execute_sameDocument_throwsCommandException() {
        StateManager.setState(DEFAULT_INDEX);
        Case testCase = new CaseBuilder().withDocument(DEFAULT_NAME.getAlphaNum(),
                DEFAULT_REFERENCE.getFileName()).build();
        List<Case> caseList = new ArrayList<>();
        caseList.add(testCase);

        ModelStub modelStub = new ModelStubWithCaseList(caseList);
        AddCommand command = new AddDocumentCommand(DEFAULT_INDEX, DEFAULT_DOCUMENT);
        assertThrows(CommandException.class,
                UserMessages.MESSAGE_DUPLICATE_DOCUMENT, () -> command.execute(modelStub));
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
