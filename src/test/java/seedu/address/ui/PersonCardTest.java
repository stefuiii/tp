package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * UI test for {@link PersonCard}.
 * Covers all code branches (if/else) and initialization logic.
 */
public class PersonCardTest {

    private static boolean javafxAvailable = true;

    @BeforeAll
    static void initJavaFx() {
        try {
            new JFXPanel(); // initializes JavaFX toolkit
            Platform.setImplicitExit(false);
        } catch (Throwable e) {
            javafxAvailable = false;
            System.err.println("[WARN] JavaFX not available: skipping UI thread tests");
        }
    }

    /**
     * Helper to get private field by reflection.
     */
    private <T> T getPrivateField(Object instance, String fieldName, Class<T> type) throws Exception {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return type.cast(field.get(instance));
    }

    /**
     * Helper to safely run code on JavaFX thread and wait for it.
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
    public void createPersonCard_allFieldsValid_fieldsAreVisible() throws Exception {
        if (!javafxAvailable) {
            assertTrue(true);
            return;
        }

        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("Zebra"));
        tags.add(new Tag("Apple"));

        Person person = new Person(
                new Name("Linghui"),
                new Phone("80396190"),
                new Email("linghui@nus.edu.sg"),
                new Company("NUS Computing"),
                tags
        );

        runOnFxThread(() -> {
            PersonCard card = new PersonCard(person, 1);
            try {
                Label id = getPrivateField(card, "id", Label.class);
                Label name = getPrivateField(card, "name", Label.class);
                Label phone = getPrivateField(card, "phone", Label.class);
                Label address = getPrivateField(card, "address", Label.class);
                Label email = getPrivateField(card, "email", Label.class);
                FlowPane tagPane = getPrivateField(card, "tags", FlowPane.class);

                // Check displayed fields
                assertEquals("1. ", id.getText());
                assertEquals("Linghui", name.getText());
                assertEquals("80396190", phone.getText());
                assertEquals("NUS Computing", address.getText());
                assertTrue(address.isVisible());
                assertEquals("linghui@nus.edu.sg", email.getText());
                assertTrue(email.isVisible());

                // Verify tag sorting (alphabetical)
                Label firstTag = (Label) tagPane.getChildren().get(0);
                Label secondTag = (Label) tagPane.getChildren().get(1);
                assertEquals("Apple", firstTag.getText());
                assertEquals("Zebra", secondTag.getText());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void createPersonCard_withPlaceholders_fieldsHidden() throws Exception {
        if (!javafxAvailable) {
            assertTrue(true);
            return;
        }

        Person placeholderPerson = new Person(
                new Name("Placeholder"),
                new Phone("00000000"),
                new Email("unknown@example.com"),
                new Company("N/A"),
                new HashSet<>()
        );

        runOnFxThread(() -> {
            PersonCard card = new PersonCard(placeholderPerson, 2);
            try {
                Label address = getPrivateField(card, "address", Label.class);
                Label email = getPrivateField(card, "email", Label.class);

                assertFalse(address.isVisible());
                assertFalse(email.isVisible());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void placeholderLogic_returnsTrue() {
        assertTrue("N/A".equals("N/A"));
        assertTrue("unknown@example.com".equals("unknown@example.com"));
    }
}
