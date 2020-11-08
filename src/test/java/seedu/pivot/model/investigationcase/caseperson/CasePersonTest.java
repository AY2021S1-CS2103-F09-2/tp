package seedu.pivot.model.investigationcase.caseperson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pivot.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.pivot.model.investigationcase.Name;

public class CasePersonTest {
    private static final Name DEFAULT_NAME = new Name("Test Name");
    private static final Phone DEFAULT_PHONE = new Phone("91234567");
    private static final Address DEFAULT_ADDRESS = new Address("Blk 123");
    private static final Email DEFAULT_EMAIL = new Email("abc@kmail.com");
    private static final Sex DEFAULT_SEX = Sex.M;

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new CasePersonStub(null, null, null, null, null));
        assertThrows(NullPointerException.class, () ->
                new CasePersonStub(null, DEFAULT_SEX, DEFAULT_PHONE, DEFAULT_EMAIL, DEFAULT_ADDRESS));
        assertThrows(NullPointerException.class, () ->
                new CasePersonStub(DEFAULT_NAME, null, DEFAULT_PHONE, DEFAULT_EMAIL, DEFAULT_ADDRESS));
        assertThrows(NullPointerException.class, () ->
                new CasePersonStub(DEFAULT_NAME, DEFAULT_SEX, null, DEFAULT_EMAIL, DEFAULT_ADDRESS));
        assertThrows(NullPointerException.class, () ->
                new CasePersonStub(DEFAULT_NAME, DEFAULT_SEX, DEFAULT_PHONE, null, DEFAULT_ADDRESS));
        assertThrows(NullPointerException.class, () ->
                new CasePersonStub(DEFAULT_NAME, DEFAULT_SEX, DEFAULT_PHONE, DEFAULT_EMAIL, null));
    }

    @Test
    public void testGetter() {
        CasePerson person =
                new CasePersonStub(DEFAULT_NAME, DEFAULT_SEX, DEFAULT_PHONE, DEFAULT_EMAIL, DEFAULT_ADDRESS);
        assertEquals(DEFAULT_NAME, person.getName());
        assertEquals(DEFAULT_SEX, person.getSex());
        assertEquals(DEFAULT_PHONE, person.getPhone());
        assertEquals(DEFAULT_EMAIL, person.getEmail());
        assertEquals(DEFAULT_ADDRESS, person.getAddress());
    }

    @Test
    public void equals() {
        CasePerson person =
                new CasePersonStub(DEFAULT_NAME, DEFAULT_SEX, DEFAULT_PHONE, DEFAULT_EMAIL, DEFAULT_ADDRESS);
        // same values -> returns true
        assertTrue(person.equals(
                new CasePersonStub(DEFAULT_NAME, DEFAULT_SEX, DEFAULT_PHONE, DEFAULT_EMAIL, DEFAULT_ADDRESS)));

        // same object -> returns true
        assertTrue(person.equals(person));

        // null -> returns false
        assertFalse(person.equals(null));

        // different type -> returns false
        assertFalse(person.equals(5));

        // different values -> returns false
        assertFalse(person.equals(
                new CasePersonStub(new Name("Tommy"), DEFAULT_SEX, DEFAULT_PHONE, DEFAULT_EMAIL, DEFAULT_ADDRESS)));
        assertFalse(person.equals(
                new CasePersonStub(DEFAULT_NAME, Sex.F, DEFAULT_PHONE, DEFAULT_EMAIL, DEFAULT_ADDRESS)));
        assertFalse(person.equals(
                new CasePersonStub(DEFAULT_NAME, DEFAULT_SEX, new Phone("923"), DEFAULT_EMAIL, DEFAULT_ADDRESS)));
        assertFalse(person.equals(new CasePersonStub(DEFAULT_NAME, DEFAULT_SEX, DEFAULT_PHONE,
                new Email("Tommy@hello.com"), DEFAULT_ADDRESS)));
        assertFalse(person.equals(
                new CasePersonStub(DEFAULT_NAME, DEFAULT_SEX, DEFAULT_PHONE, DEFAULT_EMAIL,
                        new Address("Blk 231231"))));
    }

    private class CasePersonStub extends CasePerson {

        /**
         * Every field must be present and not null.
         *
         * @param name
         * @param sex
         * @param phone
         * @param email
         * @param address
         */
        public CasePersonStub(Name name, Sex sex, Phone phone, Email email, Address address) {
            super(name, sex, phone, email, address);
        }

        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                    || (other instanceof CasePersonStub // instanceof handles nulls
                    && getName().equals(((CasePersonStub) other).getName())
                    && getAddress().equals(((CasePersonStub) other).getAddress())
                    && getEmail().equals(((CasePersonStub) other).getEmail())
                    && getPhone().equals(((CasePersonStub) other).getPhone())
                    && getSex().equals(((CasePersonStub) other).getSex())); // state check
        }
    }
}
