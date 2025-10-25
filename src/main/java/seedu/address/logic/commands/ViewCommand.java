package seedu.address.logic.commands;
import seedu.address.model.Model;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Toggles Detail Pane \n"
            + "Parameters: [CONTACT_INDEX]\n"
            + "Example: " + COMMAND_WORD + "1";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Toggling Detail Pane";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, false, true);
    }
}
