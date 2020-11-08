package seedu.pivot.logic.commands.witnesscommands;

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
import seedu.pivot.model.investigationcase.caseperson.Witness;
import seedu.pivot.testutil.CaseBuilder;
import seedu.pivot.testutil.CasePersonBuilder;

public class AddWitnessCommandTest {

    private static final Witness TEST_WITNESS = new CasePersonBuilder().buildWitness();

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddWitnessCommand(null, null));
        assertThrows(NullPointerException.class, () -> new AddWitnessCommand(null, TEST_WITNESS));
        assertThrows(NullPointerException.class, () -> new AddWitnessCommand(FIRST_INDEX, null));
    }

    @Test
    public void equals() {
        Witness alternateWitness = new Witness(new Name("Alice"), Sex.F,
                new Phone("92345678"), new Email("alice@hello.com"), new Address("Blk 345"));
        Index alternateIndex = Index.fromZeroBased(1000);

        // same object -> returns true
        AddCommand testCommand = new AddWitnessCommand(FIRST_INDEX, TEST_WITNESS);
        assertTrue(testCommand.equals(testCommand));

        // same values -> returns true
        assertTrue(testCommand.equals(new AddWitnessCommand(FIRST_INDEX, TEST_WITNESS)));

        // different types -> returns false
        assertFalse(testCommand.equals(1));

        // null -> returns false
        assertFalse(testCommand.equals(null));

        // different person -> returns false
        assertFalse(testCommand.equals(new AddWitnessCommand(FIRST_INDEX, alternateWitness)));
        assertFalse(testCommand.equals(new AddWitnessCommand(alternateIndex, TEST_WITNESS)));
        assertFalse(testCommand.equals(new AddWitnessCommand(alternateIndex, alternateWitness)));
    }

    @Test
    public void execute_validWitness_success() throws CommandException {
        StateManager.setState(FIRST_INDEX);
        Case testCase = new CaseBuilder().build();
        List<Case> caseList = new ArrayList<>();
        caseList.add(testCase);

        ModelStub modelStub = new ModelStubWithCaseList(caseList);
        AddCommand command = new AddWitnessCommand(FIRST_INDEX, TEST_WITNESS);
        assertEquals(String.format(AddWitnessCommand.MESSAGE_ADD_WITNESS_SUCCESS, TEST_WITNESS),
                command.execute(modelStub).getFeedbackToUser());

        StateManager.resetState();
    }

    @Test
    public void execute_sameWitness_throwsCommandException() {
        StateManager.setState(FIRST_INDEX);
        Case testCase = new CaseBuilder().addWitnesses(TEST_WITNESS).build();
        List<Case> caseList = new ArrayList<>();
        caseList.add(testCase);

        ModelStub modelStub = new ModelStubWithCaseList(caseList);
        AddCommand command = new AddWitnessCommand(FIRST_INDEX, TEST_WITNESS);
        assertThrows(CommandException.class,
                UserMessages.MESSAGE_DUPLICATE_WITNESS, () -> command.execute(modelStub));
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
