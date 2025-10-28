package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_PATH;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportCommand object.
 * Rules enforced:
 *  All paths are ignored — only the filename part is used.</li>
 *  Slashes ("/", "\\") are not allowed.</li>
 *  Multiple spaces are normalized into a single space.</li>
 *  Only safe filename characters are allowed (letters, digits, spaces, '_', '-', '.', '()').</li>
 *
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    @Override
    public ExportCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FILE_PATH);

        // Check presence of prefix
        if (!argMultimap.getValue(PREFIX_FILE_PATH).isPresent()) {
            throw new ParseException("File name is required. Usage: export f/<filename>");
        }

        // Trim any surrounding whitespace
        String fileName = argMultimap.getValue(PREFIX_FILE_PATH).get().trim();

        // Normalize spaces: collapse multiple spaces into one
        fileName = fileName.replaceAll("\\s+", " ").trim();

        // Reject if empty or just whitespace
        if (fileName.isEmpty()) {
            throw new ParseException("File name cannot be empty. Usage: export f/<filename>");
        }

        // Validate filename characters (cross-platform safe)
        if (!fileName.matches("^[a-zA-Z0-9._()\\-\\s]+$")) {
            throw new ParseException("Invalid file name: only letters, "
                    + "numbers, spaces, '_', '-', '.', and '()' are allowed.");
        }

        // Disallow path separators
        if (fileName.contains("/") || fileName.contains("\\")) {
            throw new ParseException("Invalid file name: '/' and '\\' are not allowed in file names.");
        }

        return new ExportCommand(fileName);
    }
}
