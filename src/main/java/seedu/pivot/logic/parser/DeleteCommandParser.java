package seedu.pivot.logic.parser;

import static seedu.pivot.commons.core.Messages.MESSAGE_INCORRECT_CASE_PAGE;
import static seedu.pivot.commons.core.Messages.MESSAGE_INCORRECT_MAIN_PAGE;
import static seedu.pivot.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pivot.logic.commands.Command.TYPE_CASE;
import static seedu.pivot.logic.commands.Command.TYPE_DOC;
import static seedu.pivot.logic.commands.Command.TYPE_SUSPECT;
import static seedu.pivot.logic.commands.Command.TYPE_VICTIM;
import static seedu.pivot.logic.commands.Command.TYPE_WITNESS;
import static seedu.pivot.logic.parser.PivotParser.BASIC_COMMAND_FORMAT;

import java.util.regex.Matcher;

import seedu.pivot.commons.core.index.Index;
import seedu.pivot.logic.commands.DeleteCommand;
import seedu.pivot.logic.commands.casecommands.DeleteCaseCommand;
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
        Index index;
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(args.trim());

        if (StateManager.atMainPage()) {
            if (!matcher.matches()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        DeleteCommand.MESSAGE_USAGE_MAIN_PAGE));
            }
            final String deleteType = matcher.group("commandWord");
            final String indexString = matcher.group("arguments");

            switch (deleteType) {

            case TYPE_CASE:
                try {
                    index = ParserUtil.parseIndex(indexString);
                } catch (ParseException pe) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE_MAIN_PAGE),
                            pe);
                }
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

        if (StateManager.atCasePage()) {
            if (!matcher.matches()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        DeleteCommand.MESSAGE_USAGE_CASE_PAGE));
            }
            final String deleteType = matcher.group("commandWord");
            final String indexString = matcher.group("arguments");

            if (deleteType.equals(TYPE_CASE)) {
                throw new ParseException(MESSAGE_INCORRECT_MAIN_PAGE);
            }

            try {
                index = ParserUtil.parseIndex(indexString);
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE_CASE_PAGE),
                        pe);
            }

            Index caseIndex = StateManager.getState();

            switch (deleteType) {

            case TYPE_DOC:
                return new DeleteDocumentCommand(caseIndex, index);

            case TYPE_SUSPECT:
                return new DeleteSuspectCommand(caseIndex, index);

            case TYPE_WITNESS:
                return new DeleteWitnessCommand(caseIndex, index);

            case TYPE_VICTIM:
                return new DeleteVictimCommand(caseIndex, index);

            default:
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        DeleteCommand.MESSAGE_USAGE_CASE_PAGE));
            }
        }

        throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
    }

}
