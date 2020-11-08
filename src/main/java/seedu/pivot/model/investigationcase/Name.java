package seedu.pivot.model.investigationcase;

/**
 * Represents a CasePerson/Document's name in the PIVOT.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name extends Alphanumeric {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";
    private static final boolean CAN_BE_BLANK = false;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        super(name, CAN_BE_BLANK);
    }

    public static boolean isValidName(String name) {
        return isValidAlphanum(name, CAN_BE_BLANK);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && getAlphaNum().toLowerCase().equals(((Name) other).getAlphaNum().toLowerCase())); // state check
    }
}
