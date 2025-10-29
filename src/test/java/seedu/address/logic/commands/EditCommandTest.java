package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
        expectedModel.updateFilteredPersonList(p -> p.equals(editedPerson));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastPerson, editedPerson);
        expectedModel.updateFilteredPersonList(p -> p.equals(editedPerson));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditPersonDescriptor());
        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateFilteredPersonList(p -> p.equals(editedPerson));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
        expectedModel.updateFilteredPersonList(p -> p.equals(editedPerson));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeByName_someFieldsSpecifiedUnfilteredList_success() {
        Person original = model.getFilteredPersonList().get(0);
        String targetName = original.getName().toString();

        Person editedPerson = new PersonBuilder(original).withPhone(VALID_PHONE_BOB).build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditCommand editCommand = new EditCommand(targetName, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(original, editedPerson);
        expectedModel.updateFilteredPersonList(p -> p.equals(editedPerson));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeByName_multipleMatches_failure() {
        // Build a model with two persons of the same name
        AddressBook addressBook = new AddressBook();
        Person john1 = new PersonBuilder().withName("John Smith").withPhone("80000001")
                .withEmail("john1@example.com").withCompany("Google").build();
        Person john2 = new PersonBuilder().withName("John Smith").withPhone("80000002")
                .withEmail("john2@example.com").withCompany("Microsoft").build();
        Person other = new PersonBuilder().withName("Jane Doe").withPhone("80000003")
                .withEmail("jane@example.com").withCompany("Amazon").build();
        addressBook.addPerson(john1);
        addressBook.addPerson(john2);
        addressBook.addPerson(other);
        Model multiModel = new ModelManager(addressBook, new UserPrefs());

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditCommand editCommand = new EditCommand("John   Smith", descriptor); // extra spaces to test normalization

        try {
            editCommand.execute(multiModel);
        } catch (CommandException ce) {
            assertEquals(EditCommand.MESSAGE_MULTIPLE_MATCHING_PERSONS, ce.getMessage());
            // filtered list should now contain only the two Johns
            assertEquals(2, multiModel.getFilteredPersonList().size());
            assertTrue(multiModel.getFilteredPersonList().contains(john1));
            assertTrue(multiModel.getFilteredPersonList().contains(john2));
            return;
        }
        throw new AssertionError("Expected CommandException was not thrown.");
    }

    @Test
    public void executeByName_nameNotFound_failure() {
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditCommand editCommand = new EditCommand("Nonexistent Name", descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_PERSON_NAME_NOT_FOUND);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in contact book
        Person personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(personInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of contact book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of contact book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));

        // name-based vs index-based -> returns false
        assertFalse(standardCommand.equals(new EditCommand("Alice Pauline", DESC_AMY)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditCommand editCommand = new EditCommand(index, editPersonDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", nameReference=" + null
                + ", editPersonDescriptor=" + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

    @Test
    public void toStringMethod_nameBased() {
        String nameRef = "Alice Pauline";
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditCommand editCommand = new EditCommand(nameRef, editPersonDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + null + ", nameReference=" + nameRef
                + ", editPersonDescriptor=" + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

    @Test
    public void equals_nameBased() {
        String nameRef = "Alice Pauline";
        EditPersonDescriptor descriptor = new EditPersonDescriptor(DESC_AMY);

        // same values -> returns true
        assertTrue(
                new EditCommand(nameRef, descriptor)
                        .equals(new EditCommand(nameRef, new EditPersonDescriptor(DESC_AMY)))
        );

        // different name -> returns false
        assertFalse(
                new EditCommand(nameRef, descriptor)
                        .equals(new EditCommand(
                                "Alice   Pauline  ",
                                new EditPersonDescriptor(DESC_AMY)
                        ))
        );
    }

    @Test
    public void executeByName_normalizationSingleMatch_success() throws Exception {
        // Build a model with a single matching name (case-insensitive, extra spaces)
        AddressBook addressBook = new AddressBook();
        Person john = new PersonBuilder().withName("John Smith").withPhone("80000001")
                .withEmail("john@example.com").withCompany("Google").build();
        Person jane = new PersonBuilder().withName("Jane Doe").withPhone("80000002")
                .withEmail("jane@example.com").withCompany("Microsoft").build();
        addressBook.addPerson(john);
        addressBook.addPerson(jane);
        Model singleModel = new ModelManager(addressBook, new UserPrefs());

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditCommand editCommand = new EditCommand("  JOHN    SMITH  ", descriptor);

        Person editedJohn = new PersonBuilder(john).withPhone(VALID_PHONE_BOB).build();
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedJohn));

        Model expectedModel = new ModelManager(new AddressBook(singleModel.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(john, editedJohn);
        expectedModel.updateFilteredPersonList(p -> p.equals(editedJohn));

        assertCommandSuccess(editCommand, singleModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateEmail_failure() {
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        // Try to edit first person to have the same email as second person
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withEmail(secondPerson.getEmail().value).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_EMAIL);
    }

    @Test
    public void execute_sameEmail_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // Edit person with same email (no change in email)
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withEmail(firstPerson.getEmail().value).withPhone(VALID_PHONE_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        Person editedPerson = new PersonBuilder(firstPerson).withEmail(firstPerson.getEmail().value)
                .withPhone(VALID_PHONE_BOB).build();

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        expectedModel.updateFilteredPersonList(p -> p.equals(editedPerson));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addTagsOnly_success() {
        // Get the second person (BENSON) who has "owesMoney" and "friends" tags
        Index indexSecondPerson = INDEX_SECOND_PERSON;
        Person secondPerson = model.getFilteredPersonList().get(indexSecondPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(secondPerson);
        Person editedPerson = personInList.withTags("owesMoney", "friends", "colleague").build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withTagsToAdd("colleague").build();
        EditCommand editCommand = new EditCommand(indexSecondPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(secondPerson, editedPerson);
        expectedModel.updateFilteredPersonList(p -> p.equals(editedPerson));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteTagsOnly_success() {
        // Get the first person (ALICE) who has "friends" tag
        Index indexFirstPerson = INDEX_FIRST_PERSON;
        Person firstPerson = model.getFilteredPersonList().get(indexFirstPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withTags().build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withTagsToDelete("friends").build();
        EditCommand editCommand = new EditCommand(indexFirstPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        expectedModel.updateFilteredPersonList(p -> p.equals(editedPerson));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_placeholderEmail_doesNotCountAsDuplicate() {
        // Build a model with two persons with placeholder emails
        AddressBook addressBook = new AddressBook();
        Person person1 = new PersonBuilder().withName("John Doe").withPhone("80000001")
                .withEmail("unknown@example.com").withCompany("Google").build();
        Person person2 = new PersonBuilder().withName("Jane Smith").withPhone("80000002")
                .withEmail("jane@example.com").withCompany("Microsoft").build();
        addressBook.addPerson(person1);
        addressBook.addPerson(person2);
        Model testModel = new ModelManager(addressBook, new UserPrefs());

        // Edit person2 to have the placeholder email (same as person1)
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withEmail("unknown@example.com").build();
        EditCommand editCommand = new EditCommand(Index.fromOneBased(2), descriptor);

        Person editedPerson2 = new PersonBuilder(person2).withEmail("unknown@example.com").build();
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson2));

        Model expectedModel = new ModelManager(new AddressBook(testModel.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(person2, editedPerson2);
        expectedModel.updateFilteredPersonList(p -> p.equals(editedPerson2));

        assertCommandSuccess(editCommand, testModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addAndDeleteTags_success() {
        // Get the second person (BENSON) who has "owesMoney" and "friends" tags
        Index indexSecondPerson = INDEX_SECOND_PERSON;
        Person secondPerson = model.getFilteredPersonList().get(indexSecondPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(secondPerson);
        Person editedPerson = personInList.withTags("owesMoney", "colleague").build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withTagsToAdd("colleague")
                .withTagsToDelete("friends").build();
        EditCommand editCommand = new EditCommand(indexSecondPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(secondPerson, editedPerson);
        expectedModel.updateFilteredPersonList(p -> p.equals(editedPerson));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteNonExistentTag_failure() {
        // Get the first person (ALICE) who has "friends" tag
        Index indexFirstPerson = INDEX_FIRST_PERSON;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withTagsToDelete("colleague").build();
        EditCommand editCommand = new EditCommand(indexFirstPerson, descriptor);

        assertCommandFailure(editCommand, model, String.format(EditCommand.MESSAGE_TAG_NOT_FOUND, "colleague"));
    }

    @Test
    public void execute_deleteMultipleNonExistentTags_failure() {
        // Get the first person (ALICE) who has "friends" tag
        Index indexFirstPerson = INDEX_FIRST_PERSON;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withTagsToDelete("colleague", "client").build();
        EditCommand editCommand = new EditCommand(indexFirstPerson, descriptor);

        // Should show both non-existent tags in the error message
        // The order may vary, so we just check it starts with the expected prefix
        try {
            editCommand.execute(model);
            throw new AssertionError("Expected CommandException was not thrown.");
        } catch (CommandException ce) {
            String errorMessage = ce.getMessage();
            assertTrue(errorMessage.contains("colleague") && errorMessage.contains("client"),
                    "Error message should contain both non-existent tags");
            assertTrue(errorMessage.contains("do not exist"),
                    "Error message should indicate tags don't exist");
        }
    }

    @Test
    public void execute_deleteMixOfExistentAndNonExistentTags_failure() {
        // Get the second person (BENSON) who has "owesMoney" and "friends" tags
        Index indexSecondPerson = INDEX_SECOND_PERSON;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withTagsToDelete("friends", "colleague").build();
        EditCommand editCommand = new EditCommand(indexSecondPerson, descriptor);

        // Should fail because "colleague" doesn't exist
        assertCommandFailure(editCommand, model, String.format(EditCommand.MESSAGE_TAG_NOT_FOUND, "colleague"));
    }

}
