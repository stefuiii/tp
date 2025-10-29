package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Exports all contacts from the address book to a CSV file on the user's Desktop.
 *
 * This command enforces that all exports go to the user's Desktop folder,
 * regardless of what file path the user provides. The user can only customize the filename.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports all contacts to a CSV file on your Desktop. "
            + "Parameters: f/FILENAME.csv\n"
            + "Example: " + COMMAND_WORD + " f/contacts.csv";

    public static final String MESSAGE_SUCCESS = "Contacts exported successfully to Desktop: %s";
    public static final String MESSAGE_FAILURE = "Failed to export contacts: %s";

    private final String userInputName;

    /**
     * Save the parsed input as the .csv file name
     */
    public ExportCommand(String userInputName) {
        requireNonNull(userInputName);
        this.userInputName = userInputName;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Person> contacts = model.getAddressBook().getPersonList();

        // Get the Desktop directory of the current user
        String desktopPath = System.getProperty("user.home") + File.separator + "Desktop" + File.separator;

        // Ensure Desktop exists (important for CI environments)
        File desktopDir = new File(desktopPath);
        if (!desktopDir.exists()) {
            desktopDir.mkdirs(); // create Desktop if it doesn’t exist
        }

        // Extract only the filename part, in case the user includes a path
        String filename = new File(userInputName).getName();

        // Automatically add ".csv" if the user didn't specify an extension
        if (!filename.toLowerCase().endsWith(".csv")) {
            filename = filename + ".csv";
        }

        // Build the full file path to Desktop
        String fullPath = desktopPath + filename;

        try (FileWriter writer = new FileWriter(fullPath)) {
            // Write the CSV header row
            writer.append("Name,Phone,Email,Company,Tags\n");

            // Write one line per contact
            for (Person p : contacts) {
                String tags = p.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.joining(";"));

                writer.append(String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"\n",
                        escapeCsv(p.getName().fullName),
                        escapeCsv(p.getPhone().value),
                        escapeCsv(p.getEmail().value),
                        escapeCsv(p.getCompany().value),
                        escapeCsv(tags)
                ));
            }

            // Return success message
            return new CommandResult(String.format(MESSAGE_SUCCESS, filename));

        } catch (IOException e) {
            // Return error message if file writing fails
            return new CommandResult(String.format(MESSAGE_FAILURE, e.getMessage()));
        }
    }

    /**
     * Escapes quotation marks inside fields to ensure valid CSV formatting.
     * Example:  Hello "World" -> Hello ""World""
     */
    private String escapeCsv(String input) {
        if (input == null) {
            return "";
        }
        return input.replace("\"", "\"\"");
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ExportCommand
                && userInputName.equals(((ExportCommand) other).userInputName));
    }
}
