package seedu.pivot.logic.parser;

import static seedu.pivot.commons.core.UserMessages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pivot.logic.parser.AddCommandParser.arePrefixesPresent;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_REFERENCE;
import static seedu.pivot.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.pivot.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.pivot.testutil.Assert.assertThrows;
import static seedu.pivot.testutil.TypicalIndexes.FIRST_INDEX;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.pivot.commons.core.index.Index;
import seedu.pivot.commons.util.FileUtil;
import seedu.pivot.logic.commands.documentcommands.AddDocumentCommand;
import seedu.pivot.logic.parser.exceptions.ParseException;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.investigationcase.Document;
import seedu.pivot.model.investigationcase.Name;
import seedu.pivot.model.investigationcase.Reference;

public class AddDocumentCommandParserTest {

    @TempDir
    public static Path testFolder;

    public static final String PREFIX_NAME_TEST = " " + PREFIX_NAME.getPrefix();
    public static final String VALID_NAME = "I am a valid name";
    public static final String INVALID_NAME = "| am |nval|d name";
    public static final String PREFIX_REFERENCE_TEST = " " + CliSyntax.PREFIX_REFERENCE.getPrefix();
    public static final String VALID_REFERENCE = "existingReference.txt";
    public static final String INVALID_REFERENCE = "invalidReference.myExt";

    private static Index index = Index.fromZeroBased(FIRST_INDEX.getZeroBased());

    private AddDocumentCommandParser parser = new AddDocumentCommandParser();

    private static Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }


    /**
     * Represents a Reference with separate default paths for testing.
     */
    private static class ReferenceStub extends Reference {
        private static final String DEFAULT_TESTPATH = "./testDirectory/";

        public ReferenceStub(String fileName) {
            super(fileName);
        }
        @Override
        public String getFilePath() {
            return DEFAULT_TESTPATH;
        }

        //override isExists to check the correct test file location
        @Override
        public boolean isExists() {
            return FileUtil.isFileExists(getTempFilePath(path.toString()));
        }

    }

    private static class AddDocumentCommandParserStub extends AddDocumentCommandParser {
        @Override
        public AddDocumentCommand parse(String args) throws ParseException {
            assert(StateManager.atCasePage()) : "Program should be at case page";

            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_REFERENCE);

            if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_REFERENCE)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddDocumentCommand.MESSAGE_USAGE));
            }

            Index index = StateManager.getState();


            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            Reference reference = new ReferenceStub(argMultimap.getValue(PREFIX_REFERENCE).get());

            if (!reference.isExists()) {
                throw new ParseException(Reference.MESSAGE_CONSTRAINTS);
            }
            Document doc = new Document(name, reference);

            return new AddDocumentCommand(index, doc);
        }
    }


    @BeforeAll
    public static void setStateZero() {
        StateManager.setState(index);
    }

    @AfterAll
    public static void setStateNull() {
        StateManager.resetState();
    }

    @Test
    public void parse_allFieldsArePresent_success() throws IOException {
        final AddDocumentCommandParserStub parserStub = new AddDocumentCommandParserStub();
        FileUtil.createFile(getTempFilePath("./testDirectory/" + VALID_REFERENCE));
        Reference existingReference = new ReferenceStub(VALID_REFERENCE);

        Document legalDocument = new Document(new Name(VALID_NAME), existingReference);

        // normal input
        assertParseSuccess(parserStub, PREFIX_NAME_TEST + VALID_NAME + PREFIX_REFERENCE_TEST + VALID_REFERENCE,
                new AddDocumentCommand(index, legalDocument));
    }

    @Test
    public void parse_prefixesMissing_throwsParseException() {
        // missing name prefix
        assertThrows(ParseException.class, () -> parser.parse(PREFIX_REFERENCE_TEST + VALID_REFERENCE));
        assertParseFailure(parser, PREFIX_REFERENCE_TEST + VALID_REFERENCE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddDocumentCommand.MESSAGE_USAGE));

        // missing reference prefix
        assertThrows(ParseException.class, () -> parser.parse(PREFIX_NAME_TEST + VALID_NAME));
        assertParseFailure(parser, PREFIX_NAME_TEST + VALID_NAME, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddDocumentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArguments_throwsParseException() {
        // invalid name
        assertThrows(ParseException.class, () -> parser.parse(PREFIX_NAME_TEST + INVALID_NAME
                + PREFIX_REFERENCE_TEST + VALID_REFERENCE));
        assertParseFailure(parser, PREFIX_NAME_TEST + INVALID_NAME
                + PREFIX_REFERENCE_TEST + VALID_REFERENCE, Name.MESSAGE_CONSTRAINTS);

        // invalid reference
        assertThrows(ParseException.class, () -> parser.parse(PREFIX_NAME_TEST + VALID_NAME
                + PREFIX_REFERENCE_TEST + INVALID_REFERENCE));
        assertParseFailure(parser, PREFIX_NAME_TEST + VALID_NAME
                        + PREFIX_REFERENCE_TEST + INVALID_REFERENCE, Reference.MESSAGE_CONSTRAINTS);
    }

}
