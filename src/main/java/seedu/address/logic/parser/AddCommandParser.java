package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INCORRECT_CASE_PAGE;
import static seedu.address.commons.core.Messages.MESSAGE_INCORRECT_MAIN_PAGE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.Command.TYPE_CASE;
import static seedu.address.logic.commands.Command.TYPE_DESC;
import static seedu.address.logic.commands.Command.TYPE_DOC;
import static seedu.address.logic.commands.Command.TYPE_SUSPECT;
import static seedu.address.logic.commands.Command.TYPE_VICTIM;
import static seedu.address.logic.commands.Command.TYPE_WITNESS;
import static seedu.address.logic.parser.AddressBookParser.BASIC_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.state.StateManager;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(args.trim());

        if (StateManager.atMainPage()) {
            if (!matcher.matches()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddCommand.MESSAGE_USAGE_MAIN_PAGE));
            }

            final String commandWord = matcher.group("commandWord");
            final String arguments = matcher.group("arguments");
            switch (commandWord) {

            case TYPE_CASE:
                return new AddCaseCommandParser().parse(arguments);

            case TYPE_DESC:
            case TYPE_DOC:
            case TYPE_SUSPECT:
            case TYPE_WITNESS:
            case TYPE_VICTIM:
                throw new ParseException(MESSAGE_INCORRECT_CASE_PAGE);

            default:
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            AddCommand.MESSAGE_USAGE_MAIN_PAGE));
            }
        }

        if (StateManager.atCasePage()) {
            if (!matcher.matches()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddCommand.MESSAGE_USAGE_CASE_PAGE));
            }

            final String commandWord = matcher.group("commandWord");
            final String arguments = matcher.group("arguments");
            switch (commandWord) {

            case TYPE_CASE:
                throw new ParseException(MESSAGE_INCORRECT_MAIN_PAGE);

            case TYPE_DESC:
                //TODO: return individual parser

            case TYPE_DOC:
                return new AddDocumentCommandParser().parse(arguments);

            case TYPE_SUSPECT:
                //TODO: return individual parser

            case TYPE_WITNESS:
                //TODO: return individual parser

            case TYPE_VICTIM:
                //TODO: return individual parser

            default:
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            AddCommand.MESSAGE_USAGE_CASE_PAGE));
            }
        }

        throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
