package seedu.pivot.logic.commands.suspectcommands;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pivot.testutil.Assert.assertThrows;
import static seedu.pivot.testutil.TypicalIndexes.FIRST_INDEX;

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
import seedu.pivot.model.investigationcase.Name;
import seedu.pivot.model.investigationcase.caseperson.Address;
import seedu.pivot.model.investigationcase.caseperson.Email;
import seedu.pivot.model.investigationcase.caseperson.Phone;
import seedu.pivot.model.investigationcase.caseperson.Sex;
import seedu.pivot.model.investigationcase.caseperson.Suspect;
import seedu.pivot.testutil.CaseBuilder;
import seedu.pivot.testutil.CasePersonBuilder;


public class AddSuspectCommandTest {

    private static final Suspect TEST_SUSPECT = new CasePersonBuilder().buildSuspect();

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddSuspectCommand(null, null));
        assertThrows(NullPointerException.class, () -> new AddSuspectCommand(null, TEST_SUSPECT));
        assertThrows(NullPointerException.class, () -> new AddSuspectCommand(FIRST_INDEX, null));
    }

    @Test
    public void equals() {
        Suspect alternateSuspect = new Suspect(new Name("Alice"), Sex.F,
                new Phone("92345678"), new Email("alice@hello.com"), new Address("Blk 345"));
        Index alternateIndex = Index.fromZeroBased(1000);

        // same object -> returns true
        AddCommand testCommand = new AddSuspectCommand(FIRST_INDEX, TEST_SUSPECT);
        assertTrue(testCommand.equals(testCommand));

        // same values -> returns true
        assertTrue(testCommand.equals(new AddSuspectCommand(FIRST_INDEX, TEST_SUSPECT)));

        // different types -> returns false
        assertFalse(testCommand.equals(1));

        // null -> returns false
        assertFalse(testCommand.equals(null));

        // different person -> returns false
        assertFalse(testCommand.equals(new AddSuspectCommand(FIRST_INDEX, alternateSuspect)));
        assertFalse(testCommand.equals(new AddSuspectCommand(alternateIndex, TEST_SUSPECT)));
        assertFalse(testCommand.equals(new AddSuspectCommand(alternateIndex, alternateSuspect)));
    }

    @Test
    public void execute_validSuspect_success() throws CommandException {
        StateManager.setState(FIRST_INDEX);
        Case testCase = new CaseBuilder().build();
        List<Case> caseList = new ArrayList<>();
        caseList.add(testCase);

        ModelStub modelStub = new ModelStubWithCaseList(caseList);
        AddCommand command = new AddSuspectCommand(FIRST_INDEX, TEST_SUSPECT);
        assertEquals(String.format(AddSuspectCommand.MESSAGE_ADD_SUSPECT_SUCCESS, TEST_SUSPECT),
                command.execute(modelStub).getFeedbackToUser());

        StateManager.resetState();
    }

    @Test
    public void execute_sameSuspect_throwsCommandException() {
        StateManager.setState(FIRST_INDEX);
        Case testCase = new CaseBuilder().addSuspects(TEST_SUSPECT).build();
        List<Case> caseList = new ArrayList<>();
        caseList.add(testCase);

        ModelStub modelStub = new ModelStubWithCaseList(caseList);
        AddCommand command = new AddSuspectCommand(FIRST_INDEX, TEST_SUSPECT);
        assertThrows(CommandException.class,
                UserMessages.MESSAGE_DUPLICATE_SUSPECT, () -> command.execute(modelStub));
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
