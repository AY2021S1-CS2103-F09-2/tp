package seedu.pivot.logic.commands.victimcommands;

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
import seedu.pivot.model.investigationcase.Name;
import seedu.pivot.model.investigationcase.caseperson.Address;
import seedu.pivot.model.investigationcase.caseperson.Email;
import seedu.pivot.model.investigationcase.caseperson.Phone;
import seedu.pivot.model.investigationcase.caseperson.Sex;
import seedu.pivot.model.investigationcase.caseperson.Victim;
import seedu.pivot.testutil.CaseBuilder;

public class DeleteVictimCommandTest {
    private static final Name DEFAULT_NAME = new Name("Test Name");
    private static final Sex DEFAULT_SEX = Sex.createSex("m");
    private static final Phone DEFAULT_PHONE = new Phone("91234567");
    private static final Address DEFAULT_ADDRESS = new Address("Blk 123");
    private static final Email DEFAULT_EMAIL = new Email("abc@gmail.com");
    private static final Victim DEFAULT_VICTIM = new Victim(DEFAULT_NAME, DEFAULT_SEX,
            DEFAULT_PHONE, DEFAULT_EMAIL, DEFAULT_ADDRESS);
    private static final Index DEFAULT_CASE_INDEX = Index.fromZeroBased(0);
    private static final Index DEFAULT_VICTIM_INDEX = Index.fromZeroBased(0);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteVictimCommand(null, null));
        assertThrows(NullPointerException.class, () -> new DeleteVictimCommand(null, DEFAULT_VICTIM_INDEX));
        assertThrows(NullPointerException.class, () -> new DeleteVictimCommand(DEFAULT_CASE_INDEX, null));
    }

    @Test
    public void equals() {
        Index alternateCaseIndex = Index.fromZeroBased(1000);
        Index alternateVictimIndex = Index.fromZeroBased(1000);
        DeleteCommand command = new DeleteVictimCommand(DEFAULT_CASE_INDEX, DEFAULT_VICTIM_INDEX);

        // same object -> returns true
        assertTrue(command.equals(command));

        // same values -> returns true
        assertTrue(command.equals(new DeleteVictimCommand(DEFAULT_CASE_INDEX, DEFAULT_VICTIM_INDEX)));

        // different types -> returns false
        assertFalse(command.equals(1));

        // null -> returns false
        assertFalse(command.equals(null));

        // different person -> returns false
        assertFalse(command.equals(new DeleteVictimCommand(alternateCaseIndex, alternateVictimIndex)));
    }

    @Test
    public void execute_victimDeletedByModel_success() throws CommandException {
        StateManager.setState(DEFAULT_CASE_INDEX);
        Case testCase = new CaseBuilder()
                .addVictims(DEFAULT_VICTIM).build();
        List<Case> caseList = new ArrayList<>();
        caseList.add(testCase);

        ModelStub modelStub = new ModelStubWithCaseList(caseList);
        DeleteCommand command = new DeleteVictimCommand(DEFAULT_CASE_INDEX, DEFAULT_VICTIM_INDEX);
        assertEquals(String.format(DeleteVictimCommand.MESSAGE_DELETE_VICTIM_SUCCESS, DEFAULT_VICTIM),
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
        DeleteCommand command = new DeleteVictimCommand(DEFAULT_CASE_INDEX, invalidIndex);
        assertThrows(CommandException.class,
                UserMessages.MESSAGE_INVALID_VICTIM_DISPLAYED_INDEX, () -> command.execute(modelStub));
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
