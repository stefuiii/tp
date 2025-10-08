package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Deletes a person identified using their phone number from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the phone number.\n"
            + "Parameters: PHONE NUMBER (must be a valid number)\n"
            + "Example: " + COMMAND_WORD + "8355 6666";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final Phone targetPhone;

    public DeleteCommand(Phone targetPhone) {
        this.targetPhone = targetPhone;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        for (Person person : model.getAddressBook().getPersonList()) {
            if (person.getPhone().equals(targetPhone)) {
                model.deletePerson(person);
                return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(person)));
            }
        }
        throw new CommandException(Messages.MESSAGE_INVALID_PERSON_PHONE);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetPhone.equals(otherDeleteCommand.targetPhone);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetPhone", targetPhone)
                .toString();
    }
}
