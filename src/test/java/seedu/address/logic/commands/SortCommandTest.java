package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SortCommandTest {
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
