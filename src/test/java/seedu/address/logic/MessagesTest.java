package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Company;
import seedu.address.model.person.Detail;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

public class MessagesTest {

    @Test
    public void format_includesAllFields_whenValidPerson() {
        Person person = new Person(
                new Name("Linghui"),
                new Phone("80396190"),
                new Email("linghui@nus.edu.sg"), // valid
                new Company("NUS Computing"), // valid
                new HashSet<>()
        );

        String formatted = Messages.format(person);

        assertTrue(formatted.contains("Name: Linghui"));
        assertTrue(formatted.contains("Email: linghui@nus.edu.sg"));
        assertTrue(formatted.contains("Company: NUS Computing"));
    }

    @Test
    public void format_includesDetail_whenDetailIsNotEmpty() {
        Person person = new Person(
                new Name("John Doe"),
                new Phone("91234567"),
                new Email("john@example.com"),
                new Company("Google"),
                new Detail("Important client"),
                new HashSet<>()
        );

        String formatted = Messages.format(person);

        assertTrue(formatted.contains("Detail: Important client"));
    }

    @Test
    public void format_excludesDetail_whenDetailIsEmpty() {
        Person person = new Person(
                new Name("Jane Doe"),
                new Phone("91234567"),
                new Email("jane@example.com"),
                new Company("Google"),
                new Detail(""),
                new HashSet<>()
        );

        String formatted = Messages.format(person);

        assertFalse(formatted.contains("Detail:"));
    }
}
