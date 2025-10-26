package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.EndOfCommandHistoryException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.AddressBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }
    @Test
    public void deletePerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deletePerson(null));
    }

    @Test
    public void deletePerson_personNotInAddressBook_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> modelManager.deletePerson(ALICE));
    }

    @Test
    public void deletePerson_personInAddressBook_personRemovedFromAddressBook() {
        modelManager.addPerson(ALICE);
        modelManager.deletePerson(ALICE);
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void deletePerson_personInAddressBook_filteredListUpdated() {
        modelManager.addPerson(ALICE);
        modelManager.updateFilteredPersonList(person -> person.equals(ALICE));
        assertEquals(1, modelManager.getFilteredPersonList().size());

        modelManager.deletePerson(ALICE);

        assertEquals(0, modelManager.getFilteredPersonList().size());
    }

    @Test
    public void deletePerson_personDeletedTwice_throwsPersonNotFoundException() {
        modelManager.addPerson(ALICE);
        modelManager.deletePerson(ALICE);

        assertThrows(PersonNotFoundException.class, () -> modelManager.deletePerson(ALICE));
    }

    @Test
    public void deletePerson_addressBookNull_throwsAssertionError() {
        ModelManager manager = new ModelManager();
        setAddressBook(manager, null);

        assertThrows(AssertionError.class, () -> manager.deletePerson(ALICE));
    }

    @Test
    public void deletePerson_personNotRemoved_throwsAssertionError() {
        ModelManager manager = new ModelManager();
        AddressBook stubbornAddressBook = new AddressBookThatDoesNotRemove(ALICE);
        setAddressBook(manager, stubbornAddressBook);

        assertThrows(AssertionError.class, () -> manager.deletePerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }

    @Test
    public void saveAndRetrieveCommandsFromHistory() {
        // Save commands
        modelManager.saveNewCommand("test3");
        modelManager.saveNewCommand("test2");
        modelManager.saveNewCommand("test1");
        modelManager.saveNewCommand("test0");

        for (int i = 0; i < 5; i++) {
            try {
                // Get the first 4 and assert commands are accurate
                String command = modelManager.getPreviousCommand();
                assertEquals("test" + i, command);
            } catch (EndOfCommandHistoryException e) {
                // Check out-of-bounds return proper error message
                assertEquals("End of Command History reached", e.getMessage());
            }
        }

        // Check get Next Features
        for (int i = 3; i >= 0; i--) {
            // Reverse direction and get the 4 commands again using NextCommand
            String command = modelManager.getNextCommand();
            assertEquals("test" + i, command);
        }

        // Check twice getting beyond latest command (Expect return empty string)
        assertEquals("", modelManager.getNextCommand());
        assertEquals("", modelManager.getNextCommand());
    }

    private void setAddressBook(ModelManager manager, AddressBook replacement) {
        try {
            Field addressBookField = ModelManager.class.getDeclaredField("addressBook");
            addressBookField.setAccessible(true);
            addressBookField.set(manager, replacement);
        } catch (ReflectiveOperationException e) {
            throw new AssertionError("Unable to configure contact book for test", e);
        }
    }

    private static class AddressBookThatDoesNotRemove extends AddressBook {
        AddressBookThatDoesNotRemove(Person stubbornPerson) {
            addPerson(stubbornPerson);
        }

        @Override
        public void removePerson(Person key) {
            // Do nothing to simulate a failed removal
        }
    }
}
