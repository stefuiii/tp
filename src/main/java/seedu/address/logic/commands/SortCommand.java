package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Sorts all persons in the address book for the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": sorts all persons in address book "
            + "based on the sorting comparator passed in.\n"
            + "Parameters: f/[FIELD] o/[ORDER]\n"
            + "Example: " + COMMAND_WORD + " f/name o/asc";

    public static final String MESSAGE_SUCCESS = "Sorted all persons";

    private String field;
    private String order;

    /**
     * Creates a SortCommand object with field and order
     * initialized.
     *
     * @param field Comparator to be used for sorting
     * @param order Order in which to sort contacts
     */
    public SortCommand(String field, String order) {
        requireAllNonNull(field, order);
        this.field = field;
        this.order = order;
    }

    // TODO
    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult("NOT IMPLEMENTED YET");
    }
}
