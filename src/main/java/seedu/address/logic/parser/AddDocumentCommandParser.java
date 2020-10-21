package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.AddCommandParser.arePrefixesPresent;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REFERENCE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.documentcommands.AddDocumentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.state.StateManager;
import seedu.address.model.investigationcase.Document;
import seedu.address.model.investigationcase.Name;
import seedu.address.model.investigationcase.Reference;

public class AddDocumentCommandParser implements Parser<AddDocumentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddDocumentCommand
     * and returns an AddDocumentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddDocumentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_REFERENCE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_REFERENCE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDocumentCommand.MESSAGE_USAGE));
        }

        assert(StateManager.atCasePage() == true) : "Program should be at case page";
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
