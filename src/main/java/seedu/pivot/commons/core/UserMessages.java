package seedu.pivot.commons.core;

/**
 * Container for user visible messages.
 */
public class UserMessages {

    // Common messages
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format!\n%1$s";
    public static final String MESSAGE_MISSING_PREFIX_INVALID_COMMAND = "Please specify a field to change!\n%1$s";

    // Main page messages
    public static final String MESSAGE_INVALID_CASE_DISPLAYED_INDEX = "The case index provided is invalid";
    public static final String MESSAGE_CASES_LISTED_OVERVIEW = "%1$d cases listed!";
    public static final String MESSAGE_INCORRECT_MAIN_PAGE = "Invalid command. "
            + "Please return to the main page to use this command.\n"
            + "To return to the main page, type 'return'";
    public static final String MESSAGE_INCORRECT_CASE_PAGE = "Invalid command. "
            + "Please open a case to use this command.";

    // Case page messages
    public static final String MESSAGE_INVALID_DOCUMENT_DISPLAYED_INDEX = "The document index provided is invalid";
    public static final String MESSAGE_INVALID_SUSPECT_DISPLAYED_INDEX = "The suspect index provided is invalid";
    public static final String MESSAGE_INVALID_WITNESS_DISPLAYED_INDEX = "The witness index provided is invalid";
    public static final String MESSAGE_INVALID_VICTIM_DISPLAYED_INDEX = "The victim index provided is invalid";

    // Reference invalid message
    public static final String MESSAGE_REFERENCE_DOES_NOT_EXIST = "This document with reference %1$s does not exist.";
    public static final String MESSAGE_ERROR_OPENING_FILE = "There was an error opening your file!";
    public static final String MESSAGE_DESKTOP_API_NOT_AVAILABLE = "Desktop API is not available, "
            + "we are unable to open your file.";

    //Duplicate Messages
    public static final String MESSAGE_DUPLICATE_TITLE = "A case with this title already exists.";
    public static final String MESSAGE_DUPLICATE_DOCUMENT = "This document already exists in the case.";
    public static final String MESSAGE_DUPLICATE_WITNESS = "This witness already exists in the case.";
    public static final String MESSAGE_DUPLICATE_SUSPECT = "This suspect already exists in the case.";
    public static final String MESSAGE_DUPLICATE_VICTIM = "This victim already exists in the case.";

}
