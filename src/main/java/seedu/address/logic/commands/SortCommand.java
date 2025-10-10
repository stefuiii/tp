package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts all persons in the address book for the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": sorts all persons in address book "
            + "based on the sorting comparator passed in.\n"
            + "Parameters: f/[FIELD] o/[ORDER]\n"
            + "Example: " + COMMAND_WORD + " f/name o/asc";

    public static final String MESSAGE_SUCCESS = "Sorted all persons by %s in %s order";

    public static final String INVALID_FIELD_MESSAGE = "Invalid field! Valid fields: name, tag";
    public static final String INVALID_ORDER_MESSAGE = "Invalid order! Use 'asc' or 'desc'";

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

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Comparator<Person> comparator = getComparator();
        model.sortPersons(comparator);
        return new CommandResult(String.format(MESSAGE_SUCCESS, field, getOrderFullFormat()));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof SortCommand)) {
            return false;
        }

        SortCommand other = (SortCommand) o;

        return this.field.equals(other.field) && this.order.equals(other.order);
    }

    private Comparator<Person> getComparator() throws CommandException {
        Comparator<Person> comparator;

        switch (field) {
        case "name":
            comparator = Comparator.comparing(person -> person.getName().toString(), String.CASE_INSENSITIVE_ORDER);
            break;
        case "tag":
            comparator = Comparator.comparing(person ->
                    person.getTags().stream()
                          .map(tag -> tag.tagName.toLowerCase())
                          .sorted()
                          .findFirst()
                          .orElse(""));
            break;
        default:
            throw new CommandException(INVALID_FIELD_MESSAGE);
        }

        switch (order) {
        case "asc":
        case "ascending":
            return comparator;
        case "desc":
        case "descending":
            return comparator.reversed();
        default:
            throw new CommandException(INVALID_ORDER_MESSAGE);
        }
    }

    /**
     * Returns the full format of a speicifed order
     * Precondition: Order is valid and is either an abbreviation or the full format
     * @return String containing the full format of the order
     */
    private String getOrderFullFormat() {
        switch (order) {
        case "asc":
            return "ascending";
        case "desc":
            return "descending";
        default:
            return order;
        }
    }
}
