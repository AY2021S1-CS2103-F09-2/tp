package seedu.pivot.model.investigationcase.caseperson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pivot.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.pivot.model.investigationcase.Description;
import seedu.pivot.model.investigationcase.Name;
import seedu.pivot.model.investigationcase.Title;

public class NameTest {
    private static final String ALPHANUMERIC = "ABC123";
    private static final String ALPHA = "ABC";
    private static final String NUMERIC = "123";
    private static final String NOT_ALPHANUMERIC = "ASdsa14@#$%^";
    private static final String BLANK = " ";
    private static final String EMPTY = "";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_notAlphanum_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Name(NOT_ALPHANUMERIC));
    }

    @Test
    public void constructor_blank_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Name(BLANK));
        assertThrows(IllegalArgumentException.class, () -> new Name(EMPTY));
    }

    @Test
    public void isValidName_blank_false() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // valid values
        assertTrue(Name.isValidName(ALPHANUMERIC));
        assertTrue(Name.isValidName(ALPHA));
        assertTrue(Name.isValidName(NUMERIC));

        // invalid values
        assertFalse(Name.isValidName(NOT_ALPHANUMERIC));

        // blank -> false
        assertFalse(Name.isValidName(BLANK));
        assertFalse(Name.isValidName(EMPTY));
    }

    @Test
    public void equals() {
        assertNotEquals(new Name(ALPHANUMERIC), new Title(ALPHANUMERIC));
        assertNotEquals(new Name(ALPHANUMERIC), new Description(ALPHANUMERIC));

        Name name = new Name(ALPHANUMERIC);
        // same values -> returns true
        assertTrue(name.equals(new Name(ALPHANUMERIC)));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different type -> returns false
        assertFalse(name.equals(5));

        // different alphanum -> returns false
        assertFalse(name.equals(new Name(ALPHA)));
    }
}
