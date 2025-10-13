package seedu.address.model;

import java.util.ArrayList;

/**
 * Represents in-memory Record of Command Usage in order
 */
public class CommandHistory implements ReadOnlyCommandHistory {

    /**
     * Array of stored command strings
     */
    private ArrayList<String> commandHistoryList = new ArrayList<>();

    /**
     * Adds a new command string to commandHistoryList for tracking
     * Commands are added to the end of the array
     */
    public void saveNewCommand(String newCommand) {
        this.commandHistoryList.add(newCommand);
    }

    /**
     * Gets the last command from history
     * @return Last Command String
     */
    public String getLastCommand() {
        int lastIndex = this.commandHistoryList.size() - 1;

        // Out of Bounds ? Return empty
        if (lastIndex < 0) {
            return "";
        }

        return this.commandHistoryList.get(lastIndex);
    }

    /**
     * (Overloaded) Gets the targeted nth command from history
     * @param n (Zero Indexed) Targets Nth Command in history starting from most recent
     * @return target Command String
     */
    public String getLastCommand(int n) {
        int targetIndex = this.commandHistoryList.size() - 1 - n;

        // Out of Bounds ? Return Empty String
        if (targetIndex < 0) {
            return "";
        }

        // If index valid, return command string
        return this.commandHistoryList.get(targetIndex);
    }
}
