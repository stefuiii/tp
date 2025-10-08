package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;


public class AddCommandBasicTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
    }

    @Test
    public void execute_newPerson_success() throws Exception {
        Person validPerson = new Person(
                new Name("John Doe"),
                new Phone("88880000"),
                new Email("unknown@example.com"),
                new Address("N/A"),
                new HashSet<Tag>());

        AddCommandBasic command = new AddCommandBasic(validPerson);
        CommandResult result = command.execute(model);

        String expectedMessage = String.format(AddCommandBasic.MESSAGE_SUCCESS, Messages.format(validPerson));
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Person validPerson = new Person(
                new Name("John Doe"),
                new Phone("88880000"),
                new Email("unknown@example.com"),
                new Address("N/A"),
                new HashSet<Tag>());

        model.addPerson(validPerson);
        AddCommandBasic command = new AddCommandBasic(validPerson);

        assertThrows(CommandException.class, () -> command.execute(model));
    }
}
