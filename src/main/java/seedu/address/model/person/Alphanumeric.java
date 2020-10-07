package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public abstract class Alphanumeric {

    public static final String MESSAGE_CONSTRAINTS =
            "Only contain alphanumeric characters and spaces, and it should not be blank";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String alphaNum;

    /**
     * Constructs a {@code Name}.
     *
     * @param alphaNum A valid alphaNum.
     */
    public Alphanumeric(String alphaNum) {
        requireNonNull(alphaNum);
        checkArgument(isValidAlphanum(alphaNum), MESSAGE_CONSTRAINTS);
        this.alphaNum = alphaNum;
    }

    public Alphanumeric() {
        this.alphaNum = "";
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidAlphanum(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return alphaNum;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Alphanumeric // instanceof handles nulls
                && alphaNum.equals(((Alphanumeric) other).alphaNum)); // state check
    }

    @Override
    public int hashCode() {
        return alphaNum.hashCode();
    }
}
