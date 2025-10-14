package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel; // initializes JavaFX
import javafx.scene.control.Label;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Tests for PersonCard UI logic without lookup() or TestFX.
 */
public class PersonCardTest {

    @BeforeAll
    static void setupJavaFx() {
        try {
            // Skip UI initialization in CI headless mode
            if (System.getenv("CI") != null) {
                System.out.println("Running in CI environment, skipping JFXPanel initialization.");
                return;
            }
            new JFXPanel(); // only runs locally
        } catch (UnsupportedOperationException e) {
            System.out.println("JavaFX not supported in this environment, skipping initialization.");
        }
    }

    private Label getPrivateLabel(PersonCard card, String fieldName) throws Exception {
        Field field = PersonCard.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return (Label) field.get(card);
    }

    @Test
    public void hidesEmailAndAddress_whenPlaceholders() throws Exception {
        Person person = new Person(
                new Name("Linghui"),
                new Phone("80396190"),
                new Email("unknown@example.com"),
                new Address("N/A"),
                new java.util.HashSet<Tag>()
        );

        PersonCard card = new PersonCard(person, 1);

        Label emailLabel = getPrivateLabel(card, "email");
        Label addressLabel = getPrivateLabel(card, "address");

        assertFalse(emailLabel.isVisible(), "Email label should be hidden for placeholder value");
        assertFalse(addressLabel.isVisible(), "Address label should be hidden for placeholder value");
    }

    @Test
    public void showsEmailAndAddress_whenValid() throws Exception {
        Person person = new Person(
                new Name("Linghui"),
                new Phone("80396190"),
                new Email("linghui@nus.edu.sg"),
                new Address("NUS Computing"),
                new java.util.HashSet<Tag>()
        );

        PersonCard card = new PersonCard(person, 1);

        Label emailLabel = getPrivateLabel(card, "email");
        Label addressLabel = getPrivateLabel(card, "address");

        assertTrue(emailLabel.isVisible(), "Email label should be visible for valid input");
        assertTrue(addressLabel.isVisible(), "Address label should be visible for valid input");
    }
}
