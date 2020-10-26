package seedu.pivot.model.investigationcase;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pivot.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DescriptionTest {
    private static final String ALPHANUMERIC = "ABC123";
    private static final String ALPHA = "ABC";
    private static final String NUMERIC = "123";
    private static final String NOT_ALPHANUMERIC = "ASdsa14@#$%^";
    private static final String BLANK = " ";
    private static final String EMPTY = "";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_notAlphanum_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Description(NOT_ALPHANUMERIC));
    }

    // Description is an Alphanumeric that can be blank.
    @Test
    public void isValidDescription() {
        // null description
        assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // valid values
        assertTrue(Description.isValidDescription(ALPHANUMERIC));
        assertTrue(Description.isValidDescription(ALPHA));
        assertTrue(Description.isValidDescription(NUMERIC));

        // invalid values
        assertFalse(Description.isValidDescription(NOT_ALPHANUMERIC));

        // blank -> true
        assertTrue(Description.isValidDescription(BLANK));
        assertTrue(Description.isValidDescription(EMPTY));
    }
}
