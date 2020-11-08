package seedu.pivot.logic.parser;

import static seedu.pivot.commons.core.UserMessages.MESSAGE_INCORRECT_CASE_PAGE;
import static seedu.pivot.commons.core.UserMessages.MESSAGE_INCORRECT_MAIN_PAGE;
import static seedu.pivot.commons.core.UserMessages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pivot.logic.commands.Command.TYPE_CASE;
import static seedu.pivot.logic.commands.Command.TYPE_DESC;
import static seedu.pivot.logic.commands.Command.TYPE_DOC;
import static seedu.pivot.logic.commands.Command.TYPE_SUSPECT;
import static seedu.pivot.logic.commands.Command.TYPE_VICTIM;
import static seedu.pivot.logic.commands.Command.TYPE_WITNESS;
import static seedu.pivot.logic.parser.PivotParser.BASIC_COMMAND_FORMAT;

import java.util.regex.Matcher;

import seedu.pivot.commons.core.index.Index;
import seedu.pivot.logic.commands.DeleteCommand;
import seedu.pivot.logic.commands.casecommands.DeleteCaseCommand;
import seedu.pivot.logic.commands.casecommands.descriptioncommands.DeleteDescriptionCommand;
import seedu.pivot.logic.commands.documentcommands.DeleteDocumentCommand;
import seedu.pivot.logic.commands.suspectcommands.DeleteSuspectCommand;
import seedu.pivot.logic.commands.victimcommands.DeleteVictimCommand;
import seedu.pivot.logic.commands.witnesscommands.DeleteWitnessCommand;
import seedu.pivot.logic.parser.exceptions.ParseException;
import seedu.pivot.logic.state.StateManager;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(args.trim());

        if (StateManager.atMainPage()) {
            return parseMainPage(matcher);
        }

        if (StateManager.atCasePage()) {
            return parseCasePage(matcher);
        }

        // Might be Dead code, above if conditions are binary
        throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
    }

    /**
     * Returns relevant DeleteCommand for main page.
     *
     * @param matcher
     * @return DeleteCommand
     * @throws ParseException if matcher does not match or incorrect type.
     */
    public DeleteCommand parseMainPage(Matcher matcher) throws ParseException {
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteCommand.MESSAGE_USAGE_MAIN_PAGE));
        }
        final String deleteType = matcher.group("commandWord");
        final String indexString = matcher.group("arguments");

        Index index;

        switch (deleteType) {
        case TYPE_CASE:
            index = ParserUtil.getParsedIndex(indexString, DeleteCommand.MESSAGE_USAGE_MAIN_PAGE);
            return new DeleteCaseCommand(index);
        case TYPE_DOC:
        case TYPE_SUSPECT:
        case TYPE_WITNESS:
        case TYPE_VICTIM:
            throw new ParseException(MESSAGE_INCORRECT_CASE_PAGE);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteCommand.MESSAGE_USAGE_MAIN_PAGE));
        }
    }

    /**
     * Returns relevant DeleteCommand for case page.
     *
     * @param matcher
     * @return DeleteCommand
     * @throws ParseException if matcher does not match or incorrect type.
     */
    public DeleteCommand parseCasePage(Matcher matcher) throws ParseException {
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteCommand.MESSAGE_USAGE_CASE_PAGE));
        }

        Index caseIndex = StateManager.getState();

        final String deleteType = matcher.group("commandWord");
        if (deleteType.equals(TYPE_DESC)) {
            return new DeleteDescriptionCommand(caseIndex);
        }
        final String indexString = matcher.group("arguments");

        if (deleteType.equals(TYPE_CASE)) {
            throw new ParseException(MESSAGE_INCORRECT_MAIN_PAGE);
        }

        Index index;

        switch (deleteType) {
        case TYPE_DOC:
            index = ParserUtil.getParsedIndex(indexString, DeleteCommand.MESSAGE_USAGE_CASE_PAGE);
            return new DeleteDocumentCommand(caseIndex, index);
        case TYPE_SUSPECT:
            index = ParserUtil.getParsedIndex(indexString, DeleteCommand.MESSAGE_USAGE_CASE_PAGE);
            return new DeleteSuspectCommand(caseIndex, index);
        case TYPE_WITNESS:
            index = ParserUtil.getParsedIndex(indexString, DeleteCommand.MESSAGE_USAGE_CASE_PAGE);
            return new DeleteWitnessCommand(caseIndex, index);
        case TYPE_VICTIM:
            index = ParserUtil.getParsedIndex(indexString, DeleteCommand.MESSAGE_USAGE_CASE_PAGE);
            return new DeleteVictimCommand(caseIndex, index);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteCommand.MESSAGE_USAGE_CASE_PAGE));
        }
    }

}
