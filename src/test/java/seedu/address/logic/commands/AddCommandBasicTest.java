package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void equals() {
        Person john = new Person(
                new Name("John Doe"),
                new Phone("88880000"),
                new Email("unknown@example.com"),
                new Address("N/A"),
                new HashSet<Tag>());

        Person amy = new Person(
                new Name("Amy Bee"),
                new Phone("85355255"),
                new Email("unknown@example.com"),
                new Address("N/A"),
                new HashSet<Tag>());

        AddCommandBasic addJohnCommand = new AddCommandBasic(john);
        AddCommandBasic addJohnCommandCopy = new AddCommandBasic(john);
        AddCommandBasic addAmyCommand = new AddCommandBasic(amy);

        // same object -> returns true
        assertEquals(addJohnCommand, addJohnCommand);

        // same values -> returns true
        assertEquals(addJohnCommand, addJohnCommandCopy);

        // different types -> returns false
        assertNotEquals(addJohnCommand, 1);

        // null -> returns false
        assertNotEquals(addJohnCommand, null);

        // different person -> returns false
        assertNotEquals(addJohnCommand, addAmyCommand);
    }

    @Test
    public void toString_validFormat_success() {
        Person john = new Person(
                new Name("John Doe"),
                new Phone("88880000"),
                new Email("unknown@example.com"),
                new Address("N/A"),
                new HashSet<Tag>());

        AddCommandBasic command = new AddCommandBasic(john);
        String expected = "AddCommandBasic{toAdd=" + john.toString() + "}";

        // toString should contain the class name and person's name
        String actual = command.toString();
        assertTrue(actual.contains("toAdd"));
        assertTrue(actual.contains("John Doe"));
    }
}
