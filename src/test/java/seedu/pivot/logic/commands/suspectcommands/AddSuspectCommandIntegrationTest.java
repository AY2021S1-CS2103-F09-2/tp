package seedu.pivot.logic.commands.suspectcommands;

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
import seedu.pivot.model.investigationcase.Name;
import seedu.pivot.model.investigationcase.caseperson.Address;
import seedu.pivot.model.investigationcase.caseperson.Email;
import seedu.pivot.model.investigationcase.caseperson.Phone;
import seedu.pivot.model.investigationcase.caseperson.Sex;
import seedu.pivot.model.investigationcase.caseperson.Suspect;
import seedu.pivot.testutil.CaseBuilder;

public class AddSuspectCommandIntegrationTest {
    private static final Name DEFAULT_NAME = new Name("Test Name");
    private static final Sex DEFAULT_SEX = Sex.createSex("m");
    private static final Phone DEFAULT_PHONE = new Phone("91234567");
    private static final Address DEFAULT_ADDRESS = new Address("Blk 123");
    private static final Email DEFAULT_EMAIL = new Email("abc@gmail.com");
    private static final Suspect DEFAULT_SUSPECT = new Suspect(DEFAULT_NAME, DEFAULT_SEX,
            DEFAULT_PHONE, DEFAULT_EMAIL, DEFAULT_ADDRESS);
    private Model model = new ModelManager(getTypicalPivot(), new UserPrefs());

    @Test
    public void execute_validIndexAndValidSuspectUnfilteredList_success() {
        StateManager.setState(FIRST_INDEX);
        Case caseToUpdate = model.getFilteredCaseList().get(FIRST_INDEX.getZeroBased());
        // CASE: ALICE, WITHOUT Suspect
        Case expectedCase = new CaseBuilder(caseToUpdate).addSuspects(DEFAULT_SUSPECT).build();

        AddSuspectCommand command = new AddSuspectCommand(FIRST_INDEX, DEFAULT_SUSPECT);

        String expectedMessage = String.format(AddSuspectCommand.MESSAGE_ADD_SUSPECT_SUCCESS, DEFAULT_SUSPECT);
        ModelManager expectedModel = new ModelManager(new Pivot(model.getPivot()), new UserPrefs());
        expectedModel.setCase(caseToUpdate, expectedCase);
        expectedModel.commitPivot(expectedMessage, command);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        StateManager.resetState();
    }

    @Test
    public void execute_validIndexAndDuplicateSuspectUnfilteredList_throwsCommandException() {
        StateManager.setState(FIRST_INDEX);
        Case caseToUpdate = model.getFilteredCaseList().get(FIRST_INDEX.getZeroBased());
        // CASE: ALICE, WITHOUT Suspect
        Suspect suspect = caseToUpdate.getSuspects().get(0);

        AddCommand command = new AddSuspectCommand(FIRST_INDEX, suspect);

        assertCommandFailure(command, model, UserMessages.MESSAGE_DUPLICATE_SUSPECT);
        StateManager.resetState();
    }

    @Test
    public void execute_validIndexAndValidSuspectFilteredList_success() {
        showCaseAtIndex(model, FIRST_INDEX);
        StateManager.setState(FIRST_INDEX);
        Case caseToUpdate = model.getFilteredCaseList().get(FIRST_INDEX.getZeroBased());
        // CASE: ALICE, WITHOUT Suspect
        Case expectedCase = new CaseBuilder(caseToUpdate).addSuspects(DEFAULT_SUSPECT).build();

        AddSuspectCommand command = new AddSuspectCommand(FIRST_INDEX, DEFAULT_SUSPECT);

        String expectedMessage = String.format(AddSuspectCommand.MESSAGE_ADD_SUSPECT_SUCCESS, DEFAULT_SUSPECT);
        ModelManager expectedModel = new ModelManager(new Pivot(model.getPivot()), new UserPrefs());
        showCaseAtIndex(expectedModel, FIRST_INDEX);
        expectedModel.setCase(caseToUpdate, expectedCase);
        expectedModel.commitPivot(expectedMessage, command);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        StateManager.resetState();
    }

    @Test
    public void execute_validIndexAndDuplicateSuspectFilteredList_throwsCommandException() {
        showCaseAtIndex(model, FIRST_INDEX);
        StateManager.setState(FIRST_INDEX);
        Case caseToUpdate = model.getFilteredCaseList().get(FIRST_INDEX.getZeroBased());
        // CASE: ALICE, WITHOUT Suspect
        Suspect suspect = caseToUpdate.getSuspects().get(0);

        AddCommand command = new AddSuspectCommand(FIRST_INDEX, suspect);

        assertCommandFailure(command, model, UserMessages.MESSAGE_DUPLICATE_SUSPECT);
        StateManager.resetState();
    }
}
