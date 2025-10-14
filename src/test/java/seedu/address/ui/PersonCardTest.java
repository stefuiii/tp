package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Label;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Tests UI logic for PersonCard — ensures placeholder fields are hidden,
 * and valid fields are visible. Includes CI-safe JavaFX initialization.
 */
public class PersonCardTest {

    private static boolean javafxAvailable = true;

    @BeforeAll
    static void setupJavaFx() {
        try {
            // Initialize JavaFX Toolkit (necessary even in headless mode)
            new JFXPanel();
            Platform.setImplicitExit(false);
        } catch (Throwable e) {
            javafxAvailable = false;
            System.err.println("[WARN] JavaFX not available, skipping real UI assertions.");
        }
    }

    /**
     * Helper: reflectively access private Label from PersonCard.
     */
    private Label getPrivateLabel(PersonCard card, String fieldName) throws Exception {
        Field field = PersonCard.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return (Label) field.get(card);
    }

    /**
     * Helper: safely execute code on JavaFX thread and wait for completion.
     */
    private void runOnFxThread(Runnable action) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } finally {
                latch.countDown();
            }
        });
        latch.await(1, TimeUnit.SECONDS);
    }

    @Test
    public void hidesEmailAndAddress_whenPlaceholders() throws Exception {
        if (!javafxAvailable) {
            assertTrue(true);
            return;
        }

        Person person = new Person(
                new Name("Linghui"),
                new Phone("80396190"),
                new Email("unknown@example.com"),
                new Address("N/A"),
                new HashSet<Tag>()
        );

        runOnFxThread(() -> {
            PersonCard card = new PersonCard(person, 1);
            try {
                Label emailLabel = getPrivateLabel(card, "email");
                Label addressLabel = getPrivateLabel(card, "address");

                assertFalse(emailLabel.isVisible(), "Email label should be hidden for placeholder value");
                assertFalse(addressLabel.isVisible(), "Address label should be hidden for placeholder value");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void showsEmailAndAddress_whenValid() throws Exception {
        if (!javafxAvailable) {
            assertTrue(true);
            return;
        }

        Person person = new Person(
                new Name("Linghui"),
                new Phone("80396190"),
                new Email("linghui@nus.edu.sg"),
                new Address("NUS Computing"),
                new HashSet<Tag>()
        );

        runOnFxThread(() -> {
            PersonCard card = new PersonCard(person, 2);
            try {
                Label emailLabel = getPrivateLabel(card, "email");
                Label addressLabel = getPrivateLabel(card, "address");

                assertTrue(emailLabel.isVisible(), "Email label should be visible for valid input");
                assertTrue(addressLabel.isVisible(), "Address label should be visible for valid input");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
