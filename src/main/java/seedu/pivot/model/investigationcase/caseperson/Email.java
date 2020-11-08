package seedu.pivot.model.investigationcase.caseperson;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's email in PIVOT.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {

    private static final String SPECIAL_CHARACTERS = "_!#$%&'*+/=?`{|}~^.-";
    public static final String MESSAGE_CONSTRAINTS = "Emails should be of the format "
            + "local-part@domain.top-level-domain and adhere to the following constraints:\n"
            + "1. The local-part should only contain alphanumeric characters and these special characters, excluding "
            + "the parentheses, (" + SPECIAL_CHARACTERS + ") . The local part should not start with a '.'. \n"
            + "2. This is followed by a '@' and then a domain name and then followed by a top-level domain "
            + "(e.g. '.com'). "
            + "The domain name must:\n"
            + "    - start and end with alphanumeric characters.\n"
            + "    - consist of alphanumeric characters, a period or a hyphen for the characters in between, if any.\n"
            + "    - not contain consecutive periods, but consecutive hyphens are allowed.";


    // Regex below reused from https://emailregex.com/
    private static final String VALIDATION_REGEX_CHECK = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
            + "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f"
            + "]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9]"
            + "(?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]"
            + "?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\["
            + "\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    public final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param email A valid email address.
     */
    public Email(String email) {
        requireNonNull(email);
        String trimmedLowerCaseEmail = email.trim().toLowerCase();
        checkArgument(isValidEmail(trimmedLowerCaseEmail), MESSAGE_CONSTRAINTS);
        value = trimmedLowerCaseEmail;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidEmail(String test) {
        String trimmedLowerCaseTest = test.trim().toLowerCase();
        return trimmedLowerCaseTest.isBlank() || trimmedLowerCaseTest.matches(VALIDATION_REGEX_CHECK);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Email // instanceof handles nulls
                && value.toLowerCase().equals(((Email) other).value.toLowerCase())); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
