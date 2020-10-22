package seedu.pivot.logic.parser;

import static seedu.pivot.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pivot.logic.parser.AddCommandParser.arePrefixesPresent;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_DESC;

import seedu.pivot.commons.core.index.Index;
import seedu.pivot.logic.commands.casecommands.AddDescriptionCommand;
import seedu.pivot.logic.parser.exceptions.ParseException;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.investigationcase.Description;

public class AddDescriptionCommandParser implements Parser<AddDescriptionCommand> {
    @Override
    public AddDescriptionCommand parse(String args) throws ParseException {
        assert(StateManager.atCasePage()) : "Program should be at case page";

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESC);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESC)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddDescriptionCommand.MESSAGE_USAGE));
        }

        Description description = ParserUtil.parseDescription(argMultimap
                        .getValue(PREFIX_DESC).orElse(""));
        Index index = StateManager.getState();
        return new AddDescriptionCommand(index, description);
    }
}
