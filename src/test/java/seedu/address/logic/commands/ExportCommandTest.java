package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code ExportCommand}.
 */
public class ExportCommandTest {

    private Model model;
    private String desktopPath;
    private File exportedFile;

    @BeforeEach
    public void setUp() {
        // Initialize model with typical persons
        model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

        // Get the user's Desktop directory
        desktopPath = System.getProperty("user.home") + File.separator + "Desktop" + File.separator;
    }

    @AfterEach
    public void tearDown() {
        // Clean up exported file after test
        if (exportedFile != null && exportedFile.exists()) {
            exportedFile.delete();
        }
    }

    @Test
    public void execute_validFileName_success() throws IOException {
        String fileName = "test_contacts.csv";
        ExportCommand command = new ExportCommand(fileName);

        CommandResult expectedResult = new CommandResult(
                String.format(ExportCommand.MESSAGE_SUCCESS, fileName));

        // Execute the command
        assertCommandSuccess(command, model, expectedResult, model);

        // Check if the file exists on Desktop
        exportedFile = new File(desktopPath + fileName);
        assertTrue(exportedFile.exists(), "Exported CSV file should exist on Desktop.");

        // Verify CSV header and sample content
        try (BufferedReader reader = new BufferedReader(new FileReader(exportedFile))) {
            String header = reader.readLine();
            assertEquals("Name,Phone,Email,Company,Tags", header, "CSV header should match");

            String firstDataLine = reader.readLine();
            assertTrue(firstDataLine.contains(","), "First data line should contain commas");
        }
    }

    @Test
    public void execute_missingExtension_autoAppendsCsv() {
        String fileName = "test_without_extension";
        ExportCommand command = new ExportCommand(fileName);

        CommandResult expectedResult = new CommandResult(
                String.format(ExportCommand.MESSAGE_SUCCESS, fileName + ".csv"));

        // Execute the command
        assertCommandSuccess(command, model, expectedResult, model);

        // Check file with .csv appended
        exportedFile = new File(desktopPath + fileName + ".csv");
        assertTrue(exportedFile.exists(), "File should be saved with .csv extension automatically");
    }

    @Test
    public void execute_emptyModel_stillCreatesHeader() throws IOException {
        // Model with no contacts
        model = new ModelManager();
        String fileName = "empty_contacts.csv";
        ExportCommand command = new ExportCommand(fileName);

        CommandResult expectedResult = new CommandResult(
                String.format(ExportCommand.MESSAGE_SUCCESS, fileName));

        assertCommandSuccess(command, model, expectedResult, model);

        exportedFile = new File(desktopPath + fileName);
        assertTrue(exportedFile.exists(), "Empty export file should still be created.");

        // Verify only header line exists
        try (BufferedReader reader = new BufferedReader(new FileReader(exportedFile))) {
            String header = reader.readLine();
            String nextLine = reader.readLine();
            assertEquals("Name,Phone,Email,Company,Tags", header);
            assertEquals(null, nextLine, "There should be no contact lines.");
        }
    }

    @Test
    public void equals_sameFilename_returnsTrue() {
        ExportCommand a = new ExportCommand("contacts.csv");
        ExportCommand b = new ExportCommand("contacts.csv");
        assertTrue(a.equals(b));
    }

    @Test
    public void equals_differentFilename_returnsFalse() {
        ExportCommand a = new ExportCommand("contacts.csv");
        ExportCommand b = new ExportCommand("backup.csv");
        assertTrue(!a.equals(b));
    }
}
