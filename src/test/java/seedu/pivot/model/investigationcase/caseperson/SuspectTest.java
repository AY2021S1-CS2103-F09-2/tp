package seedu.pivot.model.investigationcase.caseperson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pivot.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.pivot.model.investigationcase.Name;

public class SuspectTest {
    private static final Name DEFAULT_NAME = new Name("Test Name");
    private static final Phone DEFAULT_PHONE = new Phone("91234567");
    private static final Address DEFAULT_ADDRESS = new Address("Blk 123");
    private static final Email DEFAULT_EMAIL = new Email("abc@kmail.com");
    private static final Sex DEFAULT_SEX = Sex.M;

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new Suspect(null, null, null, null, null));
        assertThrows(NullPointerException.class, () ->
                new Suspect(null, DEFAULT_SEX, DEFAULT_PHONE, DEFAULT_EMAIL, DEFAULT_ADDRESS));
        assertThrows(NullPointerException.class, () ->
                new Suspect(DEFAULT_NAME, null, DEFAULT_PHONE, DEFAULT_EMAIL, DEFAULT_ADDRESS));
        assertThrows(NullPointerException.class, () ->
                new Suspect(DEFAULT_NAME, DEFAULT_SEX, null, DEFAULT_EMAIL, DEFAULT_ADDRESS));
        assertThrows(NullPointerException.class, () ->
                new Suspect(DEFAULT_NAME, DEFAULT_SEX, DEFAULT_PHONE, null, DEFAULT_ADDRESS));
        assertThrows(NullPointerException.class, () ->
                new Suspect(DEFAULT_NAME, DEFAULT_SEX, DEFAULT_PHONE, DEFAULT_EMAIL, null));
    }

    @Test
    public void equals() {
        Suspect suspect =
                new Suspect(DEFAULT_NAME, DEFAULT_SEX, DEFAULT_PHONE, DEFAULT_EMAIL, DEFAULT_ADDRESS);
        Victim victim =
                new Victim(DEFAULT_NAME, DEFAULT_SEX, DEFAULT_PHONE, DEFAULT_EMAIL, DEFAULT_ADDRESS);
        Witness witness =
                new Witness(DEFAULT_NAME, DEFAULT_SEX, DEFAULT_PHONE, DEFAULT_EMAIL, DEFAULT_ADDRESS);

        assertNotEquals(witness, suspect);
        assertNotEquals(victim, suspect);

        // same values -> returns true
        assertTrue(suspect.equals(
                new Suspect(DEFAULT_NAME, DEFAULT_SEX, DEFAULT_PHONE, DEFAULT_EMAIL, DEFAULT_ADDRESS)));

        // same object -> returns true
        assertTrue(suspect.equals(suspect));

        // null -> returns false
        assertFalse(suspect.equals(null));

        // different type -> returns false
        assertFalse(suspect.equals(5));

        // different values -> returns false
        assertFalse(suspect.equals(
                new Suspect(new Name("Tommy"), DEFAULT_SEX, DEFAULT_PHONE, DEFAULT_EMAIL, DEFAULT_ADDRESS)));
        assertFalse(suspect.equals(
                new Suspect(DEFAULT_NAME, Sex.F, DEFAULT_PHONE, DEFAULT_EMAIL, DEFAULT_ADDRESS)));
        assertFalse(suspect.equals(
                new Suspect(DEFAULT_NAME, DEFAULT_SEX, new Phone("923"), DEFAULT_EMAIL, DEFAULT_ADDRESS)));
    }

    @Test
    public void isSamePerson() {
        Suspect suspect =
                new Suspect(DEFAULT_NAME, DEFAULT_SEX, DEFAULT_PHONE, DEFAULT_EMAIL, DEFAULT_ADDRESS);

        // same name, sex, phone, different email -> returns true
        assertTrue(suspect.isSamePerson(new Suspect(DEFAULT_NAME, DEFAULT_SEX, DEFAULT_PHONE,
                new Email("Tommy@hello.com"), DEFAULT_ADDRESS)));

        // same name, sex, phone, different address -> returns true
        assertTrue(suspect.isSamePerson(
                new Suspect(DEFAULT_NAME, DEFAULT_SEX, DEFAULT_PHONE, DEFAULT_EMAIL,
                        new Address("Blk 231231"))));
    }
}
