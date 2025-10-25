package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ViewCommand.TOGGLE_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ViewCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void toggle_view_success() {
        CommandResult expectedCommandResult = new CommandResult(TOGGLE_ACKNOWLEDGEMENT, false, false, true);
        assertCommandSuccess(new ViewCommand(), model, expectedCommandResult, expectedModel);
    }
}
