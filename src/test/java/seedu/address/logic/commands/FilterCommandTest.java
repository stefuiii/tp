package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagsContainTagPredicate;

public class FilterCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        TagsContainTagPredicate firstPredicate = createPredicate("friend");
        TagsContainTagPredicate secondPredicate = createPredicate("colleague");

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different list -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_emptyTagList_showsNoPersons() {
        // EP: Empty tag list (boundary case)
        TagsContainTagPredicate predicate = new TagsContainTagPredicate(Collections.emptyList());
        assertFilterResult(predicate, 0);
    }

    @Test
    public void execute_notPresentTag_noPersonDisplayed() {
        TagsContainTagPredicate predicate = createPredicate("a");
        assertFilterResult(predicate, 0);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_noPresentTags_noPersonDisplayed() {
        TagsContainTagPredicate predicate = createPredicate("nonexistent1", "nonexistent2");
        assertFilterResult(predicate, 0);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_onePresentTag_somePersonsDisplayed() {
        TagsContainTagPredicate predicate = createPredicate("oWEsmoney");

        // Expected number of people are found manually in TypicalPersons.
        // Please update this value accordingly if TypicalPersons is updated.
        assertFilterResult(predicate, 1);
    }

    @Test
    public void execute_allPresentTags_morePersonsDisplayed() {
        TagsContainTagPredicate predicate = createPredicate("owesMoney", "FRIENDS");

        // Expected number of people are found manually in TypicalPersons.
        // Please update this value accordingly if TypicalPersons is updated.
        assertFilterResult(predicate, 3);
    }

    /**
     * Creates a TagsContainTagPredicate from tag names.
     * @param tagNames One or more tag names to be used to create tags
     * @return TagsContainTagPredicate object
     */
    private TagsContainTagPredicate createPredicate(String... tagNames) {
        List<Tag> tags = Arrays.stream(tagNames)
                .map(Tag::new)
                .collect(Collectors.toList());
        return new TagsContainTagPredicate(tags);
    }

    /**
     * Executes filter command and asserts expected count.
     */
    private void assertFilterResult(TagsContainTagPredicate predicate, int expectedCount) {
        FilterCommand command = new FilterCommand(predicate);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, expectedCount);

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedCount, model.getFilteredPersonList().size());
    }
}
