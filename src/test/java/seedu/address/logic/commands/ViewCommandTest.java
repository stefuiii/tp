package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ViewCommand.MESSAGE_SHOWING;
import static seedu.address.logic.commands.ViewCommand.TOGGLE_ACKNOWLEDGEMENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;



public class ViewCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void toggle_view_success() {
        CommandResult expectedCommandResult = new CommandResult(TOGGLE_ACKNOWLEDGEMENT, false, false, true, -1);
        assertCommandSuccess(new ViewCommand(), model, expectedCommandResult, expectedModel);

        int targetIndex = 1;
        expectedCommandResult = new CommandResult(MESSAGE_SHOWING + targetIndex, false, false, false, targetIndex);
        assertCommandSuccess(new ViewCommand(targetIndex), model, expectedCommandResult, model);
    }

    @Test
    public void focusIndexTest() {
        final ViewCommand successVc = new ViewCommand(1);
        // Successful
        assertDoesNotThrow(() -> successVc.execute(model));

        // Out Of Bounds -> Throws Command Exception
        final ViewCommand oobVc = new ViewCommand(0);
        assertThrows(CommandException.class, () -> oobVc.execute(model));
    }

    @Test
    public void equalTest() {
        ViewCommand original = new ViewCommand(1);
        ViewCommand same = new ViewCommand(1);
        ViewCommand empty = new ViewCommand();
        ViewCommand empty2 = new ViewCommand();

        assertEquals(original, original);
        assertEquals(original, same);
        assertEquals(empty, empty2);

        // Invalid Equality
        assertNotEquals(original, empty);
        assertNotEquals(empty2, same);

    }
}
