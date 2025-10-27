package seedu.address.logic.parser;

import java.util.Objects;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ClearCommand object
 */
public class ClearCommandParser implements Parser<ClearCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ClearCommand
     * and returns a ClearCommand object for execution.
     * @throws ParseException if the user input does not conform to standard Confirmation Format
     */
    public ClearCommand parse(String args) throws ParseException {
        String[] processedArgs = args.trim().split("\\s+");

        // If provided potential confirmation code
        if (processedArgs.length == 1 && processedArgs[0] != "") {
            String confirmationCode = processedArgs[0];
            if (Objects.equals(confirmationCode, ClearCommand.COMMAND_CONFIRMATION_WORD)) {
                // Confirmation Valid -> Proceed with clearing
                return new ClearCommand(true);
            } else {
                // Invalid Confirmation Code
                throw new ParseException(ClearCommand.MESSAGE_CLEAR_CANCELLED);
            }
        }

        return new ClearCommand();

    }
}
