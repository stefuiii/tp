package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.logging.Logger;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a contact with only name and phone to the contact book.
 */
public class AddCommandBasic extends Command {

    public static final String COMMAND_WORD = "addbasic";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a contact to contact book "
            + "with only name and phone. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Jadon "
            + PREFIX_PHONE + "88880000";

    public static final String MESSAGE_SUCCESS = "You have successfully added this contact: \n%1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the contact book";
    public static final String MESSAGE_DUPLICATE_EMAIL = "This email already exists in the contact book";
    private static final String PLACEHOLDER_EMAIL = "unknown@example.com";

    private static final Logger logger = Logger.getLogger(AddCommandBasic.class.getName());
    private final Person toAdd;

    /**
     * Creates an AddCommandBasic to add the specified {@code Person}
     */
    public AddCommandBasic(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        if (isEmailDuplicated(model)) {
            throw new CommandException(MESSAGE_DUPLICATE_EMAIL);
        }

        model.addPerson(toAdd);
        logger.info("Adding a new person with basic information: " + toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    private boolean isEmailDuplicated(Model model) {
        String emailValue = toAdd.getEmail().value;
        if (PLACEHOLDER_EMAIL.equals(emailValue)) {
            return false;
        }

        return model.getAddressBook().getPersonList().stream()
                .anyMatch(person -> person.getEmail().value.equals(emailValue));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddCommandBasic)) {
            return false;
        }

        AddCommandBasic otherAddCommandBasic = (AddCommandBasic) other;
        return toAdd.equals(otherAddCommandBasic.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
