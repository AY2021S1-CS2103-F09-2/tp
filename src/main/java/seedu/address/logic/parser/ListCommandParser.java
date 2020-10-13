package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INCORRECT_MAIN_PAGE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.Command.TYPE_CASE;
import static seedu.address.logic.parser.AddressBookParser.BASIC_COMMAND_FORMAT;

import java.util.regex.Matcher;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.casecommands.ListCaseCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.state.StateManager;

public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");

        if (!commandWord.equals(TYPE_CASE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        if (StateManager.atCasePage()) {
            throw new ParseException(MESSAGE_INCORRECT_MAIN_PAGE);
        }

        return new ListCaseCommand();
    }
}
