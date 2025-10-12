package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagsContainTagPredicate;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "    ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand = new FilterCommand(
                new TagsContainTagPredicate(Arrays.asList(new Tag("friend"), new Tag("colleague"))));
        assertParseSuccess(parser, " " + PREFIX_TAG + " friend " + PREFIX_TAG + " colleague ", expectedFilterCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String expectedResult = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE);
        // input between filter and first prefix
        assertParseFailure(parser, "error " + PREFIX_TAG + " friend ", expectedResult);

        // PREFIX_TAG not present
        assertParseFailure(parser, "error", expectedResult);

        // PREFIX_TAG present but empty tag
        assertParseFailure(parser, " " + PREFIX_TAG, expectedResult);

        // Invalid tag name
        assertParseFailure(parser, " " + PREFIX_TAG + " invalid   tag", Tag.MESSAGE_CONSTRAINTS);

        // Empty tag in a stream of tags
        assertParseFailure(
                parser, " " + PREFIX_TAG + " friend " + PREFIX_TAG + " " + PREFIX_TAG + " colleague", expectedResult);
    }
}
