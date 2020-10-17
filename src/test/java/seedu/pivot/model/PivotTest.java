package seedu.pivot.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pivot.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.pivot.testutil.Assert.assertThrows;
import static seedu.pivot.testutil.TypicalCases.ALICE;
import static seedu.pivot.testutil.TypicalCases.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.pivot.model.investigationcase.Case;
import seedu.pivot.model.investigationcase.exceptions.DuplicateCaseException;
import seedu.pivot.testutil.CaseBuilder;

public class PivotTest {

    private final Pivot pivot = new Pivot();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), pivot.getCaseList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> pivot.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        Pivot newData = getTypicalAddressBook();
        pivot.resetData(newData);
        assertEquals(newData, pivot);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Case editedAlice = new CaseBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        List<Case> newCases = Arrays.asList(ALICE, editedAlice);
        PivotStub newData = new PivotStub(newCases);

        assertThrows(DuplicateCaseException.class, () -> pivot.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> pivot.hasCase(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(pivot.hasCase(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        pivot.addCase(ALICE);
        assertTrue(pivot.hasCase(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        pivot.addCase(ALICE);
        Case editedAlice = new CaseBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(pivot.hasCase(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> pivot.getCaseList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class PivotStub implements ReadOnlyPivot {
        private final ObservableList<Case> cases = FXCollections.observableArrayList();

        PivotStub(Collection<Case> cases) {
            this.cases.setAll(cases);
        }

        @Override
        public ObservableList<Case> getCaseList() {
            return cases;
        }
    }

}
