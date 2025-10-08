package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CommandHistoryTest {
    @Test
    public void saveNewCommand_withValidCommandString_savesCommand() {
        // getLastCommand(n) is also simultaneously tested due to usage

        CommandHistory commandHistory = new CommandHistory();
        // saves command testN in reverse order as getLastCommand indexes backwards
        commandHistory.saveNewCommand("test2");
        commandHistory.saveNewCommand("test1");
        commandHistory.saveNewCommand("test0"); // Latest Command

        assertEquals("test1", commandHistory.getLastCommand(1));
        assertEquals("test0", commandHistory.getLastCommand(0));
        assertEquals("test2", commandHistory.getLastCommand(2));
    }


    @Test
    public void getLastCommand_emptyOrOutOfBounds_returnsEmptyString() {
        CommandHistory commandHistory = new CommandHistory();

        String emptyHistoryGetResult = commandHistory.getLastCommand();
        assertEquals("", emptyHistoryGetResult);

        // Add temporary commands for out of bounds test
        commandHistory.saveNewCommand("test1");
        commandHistory.saveNewCommand("test0");

        String outOfBoundsHistoryGetResult = commandHistory.getLastCommand(2);
        assertEquals("", emptyHistoryGetResult);
    }
}
