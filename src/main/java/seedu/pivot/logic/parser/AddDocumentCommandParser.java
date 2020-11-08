package seedu.pivot.logic.parser;

import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_CASE_PAGE;
import static seedu.pivot.commons.core.UserMessages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pivot.logic.parser.AddCommandParser.arePrefixesPresent;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_REFERENCE;

import seedu.pivot.commons.core.index.Index;
import seedu.pivot.logic.commands.documentcommands.AddDocumentCommand;
import seedu.pivot.logic.parser.exceptions.ParseException;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.investigationcase.Document;
import seedu.pivot.model.investigationcase.Name;
import seedu.pivot.model.investigationcase.Reference;

public class AddDocumentCommandParser implements Parser<AddDocumentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddDocumentCommand
     * and returns an AddDocumentCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddDocumentCommand parse(String args) throws ParseException {
        assert(StateManager.atCasePage()) : ASSERT_CASE_PAGE;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_REFERENCE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_REFERENCE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDocumentCommand.MESSAGE_USAGE));
        }

        Index index = StateManager.getState();


        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Reference reference = ParserUtil.parseReference(argMultimap.getValue(PREFIX_REFERENCE).get());

        if (!reference.isExists()) {
            throw new ParseException(Reference.MESSAGE_CONSTRAINTS);
        }
        Document doc = new Document(name, reference);

        return new AddDocumentCommand(index, doc);
    }
}
