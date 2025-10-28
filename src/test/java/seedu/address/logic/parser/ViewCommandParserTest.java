package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewCommand;

public class ViewCommandParserTest {

    private ViewCommandParser parser = new ViewCommandParser();
    private final AddressBookParser abParser = new AddressBookParser();

    @Test
    public void parse_validArgs_returnsViewCommand() {
        // Fixes trailing white spaces
        ViewCommand expectedViewCommand = new ViewCommand();
        assertParseSuccess(parser, "      ", expectedViewCommand);

        // Valid Single Numerical Args (with and without trailing)
        expectedViewCommand = new ViewCommand(1);
        assertParseSuccess(parser, "1", expectedViewCommand);
        assertParseSuccess(parser, "1       ", expectedViewCommand);
    }

    @Test
    public void addressBook_parseCommand_valid() throws Exception {
        assertTrue(abParser.parseCommand(ViewCommand.COMMAND_WORD) instanceof ViewCommand);
    }


    @Test
    public void parse_invalidArgs_throwsParseException() {
        String expectedResult = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);
        // Multiple Arguments
        assertParseFailure(parser, "1 2", expectedResult);

        // Non Numerical
        assertParseFailure(parser, "error", expectedResult);
    }
}
