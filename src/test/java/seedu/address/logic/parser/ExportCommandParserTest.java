package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Unit tests for {@code ExportCommandParser}.
 *
 * <p>This parser is responsible for converting user input such as
 * "export f/contacts.csv" into an {@code ExportCommand} object.</p>
 *
 * <p>The parser enforces that:
 * <ul>
 *   <li>The prefix {@code f/} must be provided.</li>
 *   <li>The filename cannot be empty.</li>
 *   <li>If no .csv extension is provided, it will be appended automatically
 *       inside {@code ExportCommand} when executing.</li>
 * </ul>
 * </p>
 */
public class ExportCommandParserTest {

    private ExportCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new ExportCommandParser();
    }

    @Test
    public void parse_validFileName_success() throws Exception {
        String userInput = " f/contacts";
        ExportCommand expectedCommand = new ExportCommand("contacts");

        ExportCommand result = parser.parse(userInput);
        assertEquals(expectedCommand, result,
                "Parser should successfully create ExportCommand with correct filename.");
    }

    @Test
    public void parse_validFileNameWithoutExtension_success() throws Exception {
        // Even if the user omits the .csv extension, parsing still succeeds.
        String userInput = " f/myfile";
        ExportCommand expectedCommand = new ExportCommand("myfile");

        ExportCommand result = parser.parse(userInput);
        assertEquals(expectedCommand, result,
                "Parser should accept filename without extension (auto-added later).");
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        String userInput = " contacts";
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                "Parser should throw if prefix f/ is missing.");
    }

    @Test
    public void parse_emptyFilename_throwsParseException() {
        String userInput = " f/";
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                "Parser should throw if filename after f/ is empty.");
    }

    @Test
    public void parse_blankInput_throwsParseException() {
        String userInput = " ";
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                "Parser should throw if no arguments are provided.");
    }

    @Test
    public void parse_filenameWithSlash_throwsParseException() {
        String userInput = " f/test/today";
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                "Parser should reject filenames containing slashes.");
    }

    @Test
    public void parse_filenameWithMultipleSpaces_normalized() throws Exception {
        String userInput = " f/  my   spaced   file";
        ExportCommand expectedCommand = new ExportCommand("my spaced file");
        ExportCommand result = parser.parse(userInput);
        assertEquals(expectedCommand, result,
                "Parser should normalize multiple spaces into one and trim edges.");
    }

    @Test
    public void parse_filenameWithForwardSlash_throwsParseException() {
        String userInput = " f/test/contacts.csv";
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                "Parser should reject filenames containing forward slashes ('/').");
    }

    @Test
    public void parse_filenameWithBackslash_throwsParseException() {
        String userInput = " f\\test\\contacts.csv";
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                "Parser should reject filenames containing backslashes ('\\').");
    }

    @Test
    public void parse_filenameWithMixedSlashes_throwsParseException() {
        String userInput = " f/test\\contacts.csv";
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                "Parser should reject filenames containing mixed slashes ('/' or '\\').");
    }

}
