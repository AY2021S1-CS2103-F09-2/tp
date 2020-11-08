package seedu.pivot.logic.parser;

import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_CASE_PAGE;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_REFERENCE_VALID;
import static seedu.pivot.commons.core.UserMessages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pivot.commons.core.UserMessages.MESSAGE_MISSING_PREFIX_INVALID_COMMAND;
import static seedu.pivot.logic.parser.AddCommandParser.arePrefixesPresent;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_REFERENCE;
import static seedu.pivot.logic.parser.PivotParser.BASIC_COMMAND_FORMAT;

import java.util.Optional;
import java.util.regex.Matcher;

import seedu.pivot.commons.core.index.Index;
import seedu.pivot.logic.commands.documentcommands.EditDocumentCommand;
import seedu.pivot.logic.parser.exceptions.ParseException;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.investigationcase.Name;
import seedu.pivot.model.investigationcase.Reference;

public class EditDocumentCommandParser implements Parser<EditDocumentCommand> {
    @Override
    public EditDocumentCommand parse(String args) throws ParseException {

        assert(StateManager.atCasePage()) : ASSERT_CASE_PAGE;

        //get document index and arguments
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditDocumentCommand.MESSAGE_USAGE));
        }
        final String docIndex = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        //convert docIndex to Index class
        Index documentIndex = ParserUtil.getParsedIndex(docIndex, EditDocumentCommand.MESSAGE_USAGE);

        //get case from state
        Index caseIndex = StateManager.getState();

        //set up argMultimap
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(arguments, PREFIX_NAME, PREFIX_REFERENCE);

        boolean noPrefix = !arePrefixesPresent(argMultimap, PREFIX_NAME)
                && !arePrefixesPresent(argMultimap, PREFIX_REFERENCE);

        //check for prefixes or command validity
        if (noPrefix) {
            throw new ParseException(String.format(MESSAGE_MISSING_PREFIX_INVALID_COMMAND,
                    EditDocumentCommand.MESSAGE_USAGE));
        } else if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditDocumentCommand.MESSAGE_USAGE));
        }

        //initialize possible Name and Reference
        Optional<Name> name = Optional.empty();
        Optional<Reference> reference = Optional.empty();

        //update name if valid
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            name = Optional.of(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        //update reference if valid
        if (argMultimap.getValue(PREFIX_REFERENCE).isPresent()) {
            reference = Optional.of(ParserUtil.parseReference(argMultimap.getValue(PREFIX_REFERENCE).get()));
            assert(reference.isPresent()) : ASSERT_REFERENCE_VALID;
            if (!reference.get().isExists()) {
                throw new ParseException(Reference.MESSAGE_CONSTRAINTS);
            }
        }

        return new EditDocumentCommand(caseIndex, documentIndex, name, reference);

    }
}
