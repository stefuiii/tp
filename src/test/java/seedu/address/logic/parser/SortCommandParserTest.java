package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIELD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSortCommand() {
        // no leading and trailing whitespaces
        SortCommand expectedSortCommand = new SortCommand("names", "asc");
        assertParseSuccess(parser, " f/ names o/ asc", expectedSortCommand);

        // leading and traling whitespaces
        assertParseSuccess(parser, "\n  f/ \n names  \n \t  o/\n asc \n", expectedSortCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String expectedResult = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);
        // input between sort and first prefix
        assertParseFailure(parser, "error f/names o/asc", expectedResult);

        // PREFIX_FIELD not present
        assertParseFailure(parser, " o/asc", expectedResult);

        // PREFIX_ORDER not present
        assertParseFailure(parser, " f/names", expectedResult);

        // BOTH PREFIX_ORDER and PREFIX_FIELD not present
        assertParseFailure(parser, "", expectedResult);
        assertParseFailure(parser, " names asc", expectedResult);

        // Duplicate PREFIX
        assertParseFailure(parser, " f/names f/tags o/asc", Messages.getErrorMessageForDuplicatePrefixes(PREFIX_FIELD));
        assertParseFailure(parser, " f/names o/asc o/desc", Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ORDER));
        assertParseFailure(parser, " f/names f/tags o/asc o/desc",
            Messages.getErrorMessageForDuplicatePrefixes(PREFIX_FIELD, PREFIX_ORDER)
        );
        assertParseFailure(parser, " f/names f/tags", expectedResult);
        assertParseFailure(parser, " o/asc o/desc", expectedResult);
    }
}
