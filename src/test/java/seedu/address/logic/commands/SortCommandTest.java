package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class SortCommandTest {

    private static final String NAME_FIELD = "name";
    private static final String TAGS_FIELD = "tag";
    private static final String INVALID_FIELD = "invalid";


    private static final String ASCENDING_ORDER = "asc";
    private static final String DESCENDING_ORDER = "desc";
    private static final String ASCENDING_ORDER_FULL_FORMAT = "ascending";
    private static final String DESCENDING_ORDER_FULL_FORMAT = "descending";
    private static final String INVALID_ORDER = "invalid";

    private static final Comparator<Person> NAME_ASCENDING_COMPARATOR =
            Comparator.comparing(person -> person.getName().toString(), String.CASE_INSENSITIVE_ORDER);

    private static final Comparator<Person> NAME_DESCENDING_COMPARATOR = NAME_ASCENDING_COMPARATOR.reversed();

    private static final Comparator<Person> TAGS_ASCENDING_COMPARATOR =
            Comparator.comparing(person -> person.getTags().stream()
                                                 .map(tag -> tag.tagName.toLowerCase())
                                                 .sorted()
                                                 .findFirst()
                                                 .orElse(""));
    private static final Comparator<Person> TAGS_DESCENDING_COMPARATOR = TAGS_ASCENDING_COMPARATOR.reversed();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

    @Test
    public void execute_fullOrderFormatArg_success() {
        SortCommand command = new SortCommand(NAME_FIELD, ASCENDING_ORDER_FULL_FORMAT);
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, NAME_FIELD, ASCENDING_ORDER_FULL_FORMAT);
        expectedModel.sortPersons(NAME_ASCENDING_COMPARATOR);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Repeat for descending order full format
        command = new SortCommand(NAME_FIELD, DESCENDING_ORDER_FULL_FORMAT);
        expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, NAME_FIELD, DESCENDING_ORDER_FULL_FORMAT);
        expectedModel.sortPersons(NAME_DESCENDING_COMPARATOR);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortNamesAscending_success() {
        SortCommand command = new SortCommand(NAME_FIELD, ASCENDING_ORDER);
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, NAME_FIELD, ASCENDING_ORDER_FULL_FORMAT);
        expectedModel.sortPersons(NAME_ASCENDING_COMPARATOR);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortNamesDescending_success() {
        SortCommand command = new SortCommand(NAME_FIELD, DESCENDING_ORDER);
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, NAME_FIELD, DESCENDING_ORDER_FULL_FORMAT);
        expectedModel.sortPersons(NAME_DESCENDING_COMPARATOR);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortTagsAscending_success() {
        SortCommand command = new SortCommand(TAGS_FIELD, ASCENDING_ORDER);
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, TAGS_FIELD, ASCENDING_ORDER_FULL_FORMAT);
        expectedModel.sortPersons(TAGS_ASCENDING_COMPARATOR);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortTagsDescending_success() {
        SortCommand command = new SortCommand(TAGS_FIELD, DESCENDING_ORDER);
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, TAGS_FIELD, DESCENDING_ORDER_FULL_FORMAT);
        expectedModel.sortPersons(TAGS_DESCENDING_COMPARATOR);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortInvalidField_throwsCommandException() {
        SortCommand command = new SortCommand(INVALID_FIELD, ASCENDING_ORDER);
        assertCommandFailure(command, model, SortCommand.INVALID_FIELD_MESSAGE);
    }

    @Test
    public void execute_sortInvalidOrder_throwsCommandException() {
        SortCommand command = new SortCommand(NAME_FIELD, INVALID_ORDER);
        assertCommandFailure(command, model, SortCommand.INVALID_ORDER_MESSAGE);
    }

    @Test
    public void execute_sortInvalidArgs_throwsCommandException() {
        SortCommand command = new SortCommand(INVALID_FIELD, INVALID_ORDER);

        // Checks if field is valid before checking if order is valid
        assertCommandFailure(command, model, SortCommand.INVALID_FIELD_MESSAGE);
    }

    @Test
    public void equals() {
        final SortCommand standardCommand = new SortCommand("name", "asc");
        SortCommand commandWithSameValues = new SortCommand("name", "asc");

        assertTrue(standardCommand.equals(commandWithSameValues));
        assertTrue(standardCommand.equals(standardCommand));
        assertFalse(standardCommand.equals(null));
        assertFalse(standardCommand.equals(new ClearCommand()));
        assertFalse(standardCommand.equals(new SortCommand("name", "desc")));
        assertFalse(standardCommand.equals(new SortCommand("tag", "asc")));

    }
}
