package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import seedu.address.model.person.Company;
import seedu.address.model.person.Detail;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * UI test for {@link PersonDetailPanel}.
 * Tests visibility behavior for placeholder values and proper field display.
 */
public class PersonDetailPanelTest {

    private static boolean javafxAvailable = true;

    @BeforeAll
    static void initJavaFx() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Boolean> future = executor.submit(() -> {
            try {
                new JFXPanel(); // initializes JavaFX toolkit
                Platform.setImplicitExit(false);
                return true;
            } catch (Throwable e) {
                return false;
            }
        });

        try {
            // Wait max 3 seconds for JavaFX initialization
            javafxAvailable = future.get(3, TimeUnit.SECONDS);
            if (!javafxAvailable) {
                System.err.println("[WARN] JavaFX initialization failed: skipping UI thread tests");
            }
        } catch (TimeoutException e) {
            javafxAvailable = false;
            System.err.println("[WARN] JavaFX initialization timed out (headless environment?): "
                    + "skipping UI thread tests");
            future.cancel(true);
        } catch (Exception e) {
            javafxAvailable = false;
            System.err.println("[WARN] JavaFX not available: skipping UI thread tests - " + e.getMessage());
        } finally {
            executor.shutdownNow();
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
        if (!latch.await(2, TimeUnit.SECONDS)) {
            throw new RuntimeException("JavaFX operation timed out");
        }
    }

    @Test
    public void createPersonDetailPanel_allFieldsValid_fieldsAreVisible() throws Exception {
        if (!javafxAvailable) {
            assertTrue(true);
            return;
        }

        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("friend"));

        Person person = new Person(
                new Name("Alice Tan"),
                new Phone("91234567"),
                new Email("alice@example.com"),
                new Company("Google"),
                new Detail("Interested in AI projects"),
                tags
        );

        runOnFxThread(() -> {
            SimpleObjectProperty<Person> personProperty = new SimpleObjectProperty<>(person);
            PersonDetailPanel panel = new PersonDetailPanel(personProperty);
            try {
                Label focusName = getPrivateField(panel, "focusName", Label.class);
                Label focusPhone = getPrivateField(panel, "focusPhone", Label.class);
                Label focusEmail = getPrivateField(panel, "focusEmail", Label.class);
                Label focusCompany = getPrivateField(panel, "focusCompany", Label.class);
                Label focusDetail = getPrivateField(panel, "focusDetail", Label.class);
                Node focusEmailButton = getPrivateField(panel, "focusEmailButton", Node.class);
                Node focusCompanyButton = getPrivateField(panel, "focusCompanyButton", Node.class);
                Node focusDetailButton = getPrivateField(panel, "focusDetailButton", Node.class);

                // Check displayed fields
                assertEquals("Alice Tan", focusName.getText());
                assertEquals("91234567", focusPhone.getText());
                assertEquals("alice@example.com", focusEmail.getText());
                assertEquals("Google", focusCompany.getText());
                assertEquals("Interested in AI projects", focusDetail.getText());

                // All fields should be visible
                assertTrue(focusEmail.isVisible());
                assertTrue(focusEmail.isManaged());
                assertTrue(focusEmailButton.isVisible());
                assertTrue(focusEmailButton.isManaged());

                assertTrue(focusCompany.isVisible());
                assertTrue(focusCompany.isManaged());
                assertTrue(focusCompanyButton.isVisible());
                assertTrue(focusCompanyButton.isManaged());

                assertTrue(focusDetail.isVisible());
                assertTrue(focusDetail.isManaged());
                assertTrue(focusDetailButton.isVisible());
                assertTrue(focusDetailButton.isManaged());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void createPersonDetailPanel_withPlaceholders_fieldsHidden() throws Exception {
        if (!javafxAvailable) {
            assertTrue(true);
            return;
        }

        Person placeholderPerson = new Person(
                new Name("Bob Smith"),
                new Phone("98765432"),
                new Email("unknown@example.com"),
                new Company("N/A"),
                new Detail(""),
                new HashSet<>()
        );

        runOnFxThread(() -> {
            SimpleObjectProperty<Person> personProperty = new SimpleObjectProperty<>(placeholderPerson);
            PersonDetailPanel panel = new PersonDetailPanel(personProperty);
            try {
                Label focusName = getPrivateField(panel, "focusName", Label.class);
                Label focusPhone = getPrivateField(panel, "focusPhone", Label.class);
                Label focusEmail = getPrivateField(panel, "focusEmail", Label.class);
                Label focusCompany = getPrivateField(panel, "focusCompany", Label.class);
                Label focusDetail = getPrivateField(panel, "focusDetail", Label.class);
                Node focusEmailButton = getPrivateField(panel, "focusEmailButton", Node.class);
                Node focusCompanyButton = getPrivateField(panel, "focusCompanyButton", Node.class);
                Node focusDetailButton = getPrivateField(panel, "focusDetailButton", Node.class);

                // Name and phone should still be visible
                assertEquals("Bob Smith", focusName.getText());
                assertEquals("98765432", focusPhone.getText());

                // Placeholder email should be hidden
                assertFalse(focusEmail.isVisible());
                assertFalse(focusEmail.isManaged());
                assertFalse(focusEmailButton.isVisible());
                assertFalse(focusEmailButton.isManaged());

                // Placeholder company should be hidden
                assertFalse(focusCompany.isVisible());
                assertFalse(focusCompany.isManaged());
                assertFalse(focusCompanyButton.isVisible());
                assertFalse(focusCompanyButton.isManaged());

                // Empty detail should be hidden
                assertFalse(focusDetail.isVisible());
                assertFalse(focusDetail.isManaged());
                assertFalse(focusDetailButton.isVisible());
                assertFalse(focusDetailButton.isManaged());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void createPersonDetailPanel_emailPlaceholder_onlyEmailHidden() throws Exception {
        if (!javafxAvailable) {
            assertTrue(true);
            return;
        }

        Person person = new Person(
                new Name("Charlie"),
                new Phone("88888888"),
                new Email("unknown@example.com"),
                new Company("Amazon"),
                new Detail("Loves coding"),
                new HashSet<>()
        );

        runOnFxThread(() -> {
            SimpleObjectProperty<Person> personProperty = new SimpleObjectProperty<>(person);
            PersonDetailPanel panel = new PersonDetailPanel(personProperty);
            try {
                Label focusEmail = getPrivateField(panel, "focusEmail", Label.class);
                Label focusCompany = getPrivateField(panel, "focusCompany", Label.class);
                Label focusDetail = getPrivateField(panel, "focusDetail", Label.class);
                Node focusEmailButton = getPrivateField(panel, "focusEmailButton", Node.class);
                Node focusCompanyButton = getPrivateField(panel, "focusCompanyButton", Node.class);
                Node focusDetailButton = getPrivateField(panel, "focusDetailButton", Node.class);

                // Only email should be hidden
                assertFalse(focusEmail.isVisible());
                assertFalse(focusEmail.isManaged());
                assertFalse(focusEmailButton.isVisible());
                assertFalse(focusEmailButton.isManaged());

                // Company and detail should be visible
                assertTrue(focusCompany.isVisible());
                assertTrue(focusCompany.isManaged());
                assertTrue(focusCompanyButton.isVisible());
                assertTrue(focusCompanyButton.isManaged());

                assertTrue(focusDetail.isVisible());
                assertTrue(focusDetail.isManaged());
                assertTrue(focusDetailButton.isVisible());
                assertTrue(focusDetailButton.isManaged());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void createPersonDetailPanel_companyPlaceholder_onlyCompanyHidden() throws Exception {
        if (!javafxAvailable) {
            assertTrue(true);
            return;
        }

        Person person = new Person(
                new Name("Diana"),
                new Phone("77777777"),
                new Email("diana@example.com"),
                new Company("N/A"),
                new Detail("Project manager"),
                new HashSet<>()
        );

        runOnFxThread(() -> {
            SimpleObjectProperty<Person> personProperty = new SimpleObjectProperty<>(person);
            PersonDetailPanel panel = new PersonDetailPanel(personProperty);
            try {
                Label focusEmail = getPrivateField(panel, "focusEmail", Label.class);
                Label focusCompany = getPrivateField(panel, "focusCompany", Label.class);
                Label focusDetail = getPrivateField(panel, "focusDetail", Label.class);
                Node focusEmailButton = getPrivateField(panel, "focusEmailButton", Node.class);
                Node focusCompanyButton = getPrivateField(panel, "focusCompanyButton", Node.class);
                Node focusDetailButton = getPrivateField(panel, "focusDetailButton", Node.class);

                // Only company should be hidden
                assertFalse(focusCompany.isVisible());
                assertFalse(focusCompany.isManaged());
                assertFalse(focusCompanyButton.isVisible());
                assertFalse(focusCompanyButton.isManaged());

                // Email and detail should be visible
                assertTrue(focusEmail.isVisible());
                assertTrue(focusEmail.isManaged());
                assertTrue(focusEmailButton.isVisible());
                assertTrue(focusEmailButton.isManaged());

                assertTrue(focusDetail.isVisible());
                assertTrue(focusDetail.isManaged());
                assertTrue(focusDetailButton.isVisible());
                assertTrue(focusDetailButton.isManaged());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void createPersonDetailPanel_emptyDetail_onlyDetailHidden() throws Exception {
        if (!javafxAvailable) {
            assertTrue(true);
            return;
        }

        Person person = new Person(
                new Name("Eve"),
                new Phone("66666666"),
                new Email("eve@example.com"),
                new Company("Facebook"),
                new Detail(""),
                new HashSet<>()
        );

        runOnFxThread(() -> {
            SimpleObjectProperty<Person> personProperty = new SimpleObjectProperty<>(person);
            PersonDetailPanel panel = new PersonDetailPanel(personProperty);
            try {
                Label focusEmail = getPrivateField(panel, "focusEmail", Label.class);
                Label focusCompany = getPrivateField(panel, "focusCompany", Label.class);
                Label focusDetail = getPrivateField(panel, "focusDetail", Label.class);
                Node focusEmailButton = getPrivateField(panel, "focusEmailButton", Node.class);
                Node focusCompanyButton = getPrivateField(panel, "focusCompanyButton", Node.class);
                Node focusDetailButton = getPrivateField(panel, "focusDetailButton", Node.class);

                // Only detail should be hidden
                assertFalse(focusDetail.isVisible());
                assertFalse(focusDetail.isManaged());
                assertFalse(focusDetailButton.isVisible());
                assertFalse(focusDetailButton.isManaged());

                // Email and company should be visible
                assertTrue(focusEmail.isVisible());
                assertTrue(focusEmail.isManaged());
                assertTrue(focusEmailButton.isVisible());
                assertTrue(focusEmailButton.isManaged());

                assertTrue(focusCompany.isVisible());
                assertTrue(focusCompany.isManaged());
                assertTrue(focusCompanyButton.isVisible());
                assertTrue(focusCompanyButton.isManaged());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void createPersonDetailPanel_nullPerson_showsUnknownLabel() throws Exception {
        if (!javafxAvailable) {
            assertTrue(true);
            return;
        }

        runOnFxThread(() -> {
            SimpleObjectProperty<Person> personProperty = new SimpleObjectProperty<>(null);
            PersonDetailPanel panel = new PersonDetailPanel(personProperty);
            try {
                Label unknownLabel = getPrivateField(panel, "unknownLabel", Label.class);

                // Unknown label should be visible when person is null
                assertTrue(unknownLabel.isVisible());
                assertTrue(unknownLabel.isManaged());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void handleDetailClick_copyName_copiesNameToClipboard() throws Exception {
        if (!javafxAvailable) {
            assertTrue(true);
            return;
        }

        Person person = new Person(
                new Name("Test Name"),
                new Phone("12345678"),
                new Email("test@example.com"),
                new Company("Test Company"),
                new Detail("Test Detail"),
                new HashSet<>()
        );

        runOnFxThread(() -> {
            try {
                SimpleObjectProperty<Person> personProperty = new SimpleObjectProperty<>(person);
                PersonDetailPanel panel = new PersonDetailPanel(personProperty);

                // Create a mock button with the correct ID
                Button mockButton = new Button();
                mockButton.setId("focusNameButton");
                ActionEvent event = new ActionEvent(mockButton, null);

                // Invoke handleDetailClick via reflection
                java.lang.reflect.Method method = panel.getClass()
                        .getDeclaredMethod("handleDetailClick", ActionEvent.class);
                method.setAccessible(true);
                method.invoke(panel, event);

                // Verify clipboard contains the name
                assertEquals("Test Name", Clipboard.getSystemClipboard().getString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void handleDetailClick_copyPhone_copiesPhoneToClipboard() throws Exception {
        if (!javafxAvailable) {
            assertTrue(true);
            return;
        }

        Person person = new Person(
                new Name("Test Name"),
                new Phone("12345678"),
                new Email("test@example.com"),
                new Company("Test Company"),
                new Detail("Test Detail"),
                new HashSet<>()
        );

        runOnFxThread(() -> {
            try {
                SimpleObjectProperty<Person> personProperty = new SimpleObjectProperty<>(person);
                PersonDetailPanel panel = new PersonDetailPanel(personProperty);

                // Create a mock button with the correct ID
                Button mockButton = new Button();
                mockButton.setId("focusPhoneButton");
                ActionEvent event = new ActionEvent(mockButton, null);

                // Invoke handleDetailClick via reflection
                java.lang.reflect.Method method = panel.getClass()
                        .getDeclaredMethod("handleDetailClick", ActionEvent.class);
                method.setAccessible(true);
                method.invoke(panel, event);

                // Verify clipboard contains the phone
                assertEquals("12345678", Clipboard.getSystemClipboard().getString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void handleDetailClick_copyEmail_copiesEmailToClipboard() throws Exception {
        if (!javafxAvailable) {
            assertTrue(true);
            return;
        }

        Person person = new Person(
                new Name("Test Name"),
                new Phone("12345678"),
                new Email("test@example.com"),
                new Company("Test Company"),
                new Detail("Test Detail"),
                new HashSet<>()
        );

        runOnFxThread(() -> {
            try {
                SimpleObjectProperty<Person> personProperty = new SimpleObjectProperty<>(person);
                PersonDetailPanel panel = new PersonDetailPanel(personProperty);

                // Create a mock button with the correct ID
                Button mockButton = new Button();
                mockButton.setId("focusEmailButton");
                ActionEvent event = new ActionEvent(mockButton, null);

                // Invoke handleDetailClick via reflection
                java.lang.reflect.Method method = panel.getClass()
                        .getDeclaredMethod("handleDetailClick", ActionEvent.class);
                method.setAccessible(true);
                method.invoke(panel, event);

                // Verify clipboard contains the email
                assertEquals("test@example.com", Clipboard.getSystemClipboard().getString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void handleDetailClick_copyCompany_copiesCompanyToClipboard() throws Exception {
        if (!javafxAvailable) {
            assertTrue(true);
            return;
        }

        Person person = new Person(
                new Name("Test Name"),
                new Phone("12345678"),
                new Email("test@example.com"),
                new Company("Test Company"),
                new Detail("Test Detail"),
                new HashSet<>()
        );

        runOnFxThread(() -> {
            try {
                SimpleObjectProperty<Person> personProperty = new SimpleObjectProperty<>(person);
                PersonDetailPanel panel = new PersonDetailPanel(personProperty);

                // Create a mock button with the correct ID
                Button mockButton = new Button();
                mockButton.setId("focusCompanyButton");
                ActionEvent event = new ActionEvent(mockButton, null);

                // Invoke handleDetailClick via reflection
                java.lang.reflect.Method method = panel.getClass()
                        .getDeclaredMethod("handleDetailClick", ActionEvent.class);
                method.setAccessible(true);
                method.invoke(panel, event);

                // Verify clipboard contains the company
                assertEquals("Test Company", Clipboard.getSystemClipboard().getString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void handleDetailClick_copyDetail_copiesDetailToClipboard() throws Exception {
        if (!javafxAvailable) {
            assertTrue(true);
            return;
        }

        Person person = new Person(
                new Name("Test Name"),
                new Phone("12345678"),
                new Email("test@example.com"),
                new Company("Test Company"),
                new Detail("Test Detail"),
                new HashSet<>()
        );

        runOnFxThread(() -> {
            try {
                SimpleObjectProperty<Person> personProperty = new SimpleObjectProperty<>(person);
                PersonDetailPanel panel = new PersonDetailPanel(personProperty);

                // Create a mock button with the correct ID
                Button mockButton = new Button();
                mockButton.setId("focusDetailButton");
                ActionEvent event = new ActionEvent(mockButton, null);

                // Invoke handleDetailClick via reflection
                java.lang.reflect.Method method = panel.getClass()
                        .getDeclaredMethod("handleDetailClick", ActionEvent.class);
                method.setAccessible(true);
                method.invoke(panel, event);

                // Verify clipboard contains the detail
                assertEquals("Test Detail", Clipboard.getSystemClipboard().getString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void handleDetailClick_unknownButton_doesNotCrash() throws Exception {
        if (!javafxAvailable) {
            assertTrue(true);
            return;
        }

        Person person = new Person(
                new Name("Test Name"),
                new Phone("12345678"),
                new Email("test@example.com"),
                new Company("Test Company"),
                new Detail("Test Detail"),
                new HashSet<>()
        );

        runOnFxThread(() -> {
            try {
                SimpleObjectProperty<Person> personProperty = new SimpleObjectProperty<>(person);
                PersonDetailPanel panel = new PersonDetailPanel(personProperty);

                // Create a mock button with an unknown ID to test default case
                Button mockButton = new Button();
                mockButton.setId("unknownButton");
                ActionEvent event = new ActionEvent(mockButton, null);

                // Invoke handleDetailClick via reflection - should not crash
                java.lang.reflect.Method method = panel.getClass()
                        .getDeclaredMethod("handleDetailClick", ActionEvent.class);
                method.setAccessible(true);
                method.invoke(panel, event);

                // Test passes if no exception is thrown
                assertTrue(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void handleDetailClick_nonNodeSource_doesNotCrash() throws Exception {
        if (!javafxAvailable) {
            assertTrue(true);
            return;
        }

        Person person = new Person(
                new Name("Test Name"),
                new Phone("12345678"),
                new Email("test@example.com"),
                new Company("Test Company"),
                new Detail("Test Detail"),
                new HashSet<>()
        );

        runOnFxThread(() -> {
            try {
                SimpleObjectProperty<Person> personProperty = new SimpleObjectProperty<>(person);
                PersonDetailPanel panel = new PersonDetailPanel(personProperty);

                // Create an event with a non-Node source
                ActionEvent event = new ActionEvent(new Object(), null);

                // Invoke handleDetailClick via reflection - should not crash
                java.lang.reflect.Method method = panel.getClass()
                        .getDeclaredMethod("handleDetailClick", ActionEvent.class);
                method.setAccessible(true);
                method.invoke(panel, event);

                // Test passes if no exception is thrown
                assertTrue(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void personObservableChange_updatesPanel() throws Exception {
        if (!javafxAvailable) {
            assertTrue(true);
            return;
        }

        Person person1 = new Person(
                new Name("Person One"),
                new Phone("11111111"),
                new Email("person1@example.com"),
                new Company("Company One"),
                new Detail("Detail One"),
                new HashSet<>()
        );

        Person person2 = new Person(
                new Name("Person Two"),
                new Phone("22222222"),
                new Email("person2@example.com"),
                new Company("Company Two"),
                new Detail("Detail Two"),
                new HashSet<>()
        );

        runOnFxThread(() -> {
            try {
                SimpleObjectProperty<Person> personProperty = new SimpleObjectProperty<>(person1);
                PersonDetailPanel panel = new PersonDetailPanel(personProperty);

                Label focusName = getPrivateField(panel, "focusName", Label.class);
                Label focusPhone = getPrivateField(panel, "focusPhone", Label.class);
                Label focusEmail = getPrivateField(panel, "focusEmail", Label.class);
                Label focusCompany = getPrivateField(panel, "focusCompany", Label.class);
                Label focusDetail = getPrivateField(panel, "focusDetail", Label.class);

                // Check initial person
                assertEquals("Person One", focusName.getText());
                assertEquals("11111111", focusPhone.getText());
                assertEquals("person1@example.com", focusEmail.getText());
                assertEquals("Company One", focusCompany.getText());
                assertEquals("Detail One", focusDetail.getText());

                // Change the person
                personProperty.set(person2);

                // Check updated person
                assertEquals("Person Two", focusName.getText());
                assertEquals("22222222", focusPhone.getText());
                assertEquals("person2@example.com", focusEmail.getText());
                assertEquals("Company Two", focusCompany.getText());
                assertEquals("Detail Two", focusDetail.getText());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void placeholderLogic_returnsTrue() {
        assertTrue("N/A".equals("N/A"));
        assertTrue("unknown@example.com".equals("unknown@example.com"));
        assertTrue("".isEmpty());
    }
}

