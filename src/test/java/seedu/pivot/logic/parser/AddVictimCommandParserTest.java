package seedu.pivot.logic.parser;

import static seedu.pivot.commons.core.UserMessages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.NAME_DESC_AMY;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.NAME_DESC_BOB;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.SEX_DESC_BOB;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.VALID_CASEPERSON_NAME_BOB;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.VALID_CASEPERSON_PHONE;
import static seedu.pivot.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.pivot.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.pivot.testutil.TypicalIndexes.FIRST_INDEX;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.pivot.commons.core.index.Index;
import seedu.pivot.logic.commands.testutil.CommandTestUtil;
import seedu.pivot.logic.commands.victimcommands.AddVictimCommand;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.investigationcase.Name;
import seedu.pivot.model.investigationcase.caseperson.Victim;
import seedu.pivot.testutil.CasePersonBuilder;

public class AddVictimCommandParserTest {

    private static Index index = Index.fromZeroBased(FIRST_INDEX.getZeroBased());

    private AddVictimCommandParser parser = new AddVictimCommandParser();

    @BeforeAll
    public static void setStateZero() {
        StateManager.setState(index);
    }

    @AfterAll
    public static void setStateNull() {
        StateManager.resetState();
    }

    //TODO: add all the fields in for victim here
    @Test
    public void parse_allFieldsPresent_success() {
        Victim expectedVictim = new CasePersonBuilder().withName(VALID_CASEPERSON_NAME_BOB)
                .withSex(CommandTestUtil.VALID_CASEPERSON_SEX_BOB).withPhone(VALID_CASEPERSON_PHONE)
                .buildVictim();

        // normal input
        assertParseSuccess(parser, NAME_DESC_BOB + SEX_DESC_BOB + PHONE_DESC_BOB,
                new AddVictimCommand(index, expectedVictim));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + SEX_DESC_BOB + PHONE_DESC_BOB,
                new AddVictimCommand(index, expectedVictim));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + SEX_DESC_BOB + PHONE_DESC_BOB,
                new AddVictimCommand(index, expectedVictim));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddVictimCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, NAME_DESC_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + SEX_DESC_BOB + PHONE_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + SEX_DESC_BOB + PHONE_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddVictimCommand.MESSAGE_USAGE));
    }
}
