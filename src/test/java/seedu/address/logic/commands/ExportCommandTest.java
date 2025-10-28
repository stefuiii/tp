package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;

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
        model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        desktopPath = System.getProperty("user.home") + File.separator + "Desktop" + File.separator;
    }

    @AfterEach
    public void tearDown() {
        if (exportedFile != null && exportedFile.exists()) {
            exportedFile.delete();
        }
    }

    @Test
    public void execute_validFileName_success() throws IOException {
        String fileName = "test_contacts.csv";
        ExportCommand command = new ExportCommand(fileName);
        CommandResult expectedResult = new CommandResult(String.format(ExportCommand.MESSAGE_SUCCESS, fileName));

        assertCommandSuccess(command, model, expectedResult, model);

        exportedFile = new File(desktopPath + fileName);
        assertTrue(exportedFile.exists(), "Exported CSV file should exist on Desktop.");

        try (BufferedReader reader = new BufferedReader(new FileReader(exportedFile))) {
            String header = reader.readLine();
            assertEquals("Name,Phone,Email,Company,Tags", header, "CSV header should match");
        }
    }

    @Test
    public void execute_missingExtension_autoAppendsCsv() {
        String fileName = "test_without_extension";
        ExportCommand command = new ExportCommand(fileName);
        CommandResult expectedResult = new CommandResult(String.format(
                ExportCommand.MESSAGE_SUCCESS, fileName + ".csv"));

        assertCommandSuccess(command, model, expectedResult, model);

        exportedFile = new File(desktopPath + fileName + ".csv");
        assertTrue(exportedFile.exists(), "File should be saved with .csv extension automatically");
    }

    @Test
    public void execute_emptyModel_stillCreatesHeader() throws IOException {
        model = new ModelManager();
        String fileName = "empty_contacts.csv";
        ExportCommand command = new ExportCommand(fileName);
        CommandResult expectedResult = new CommandResult(String.format(ExportCommand.MESSAGE_SUCCESS, fileName));

        assertCommandSuccess(command, model, expectedResult, model);

        exportedFile = new File(desktopPath + fileName);
        assertTrue(exportedFile.exists(), "Empty export file should still be created.");

        try (BufferedReader reader = new BufferedReader(new FileReader(exportedFile))) {
            String header = reader.readLine();
            String nextLine = reader.readLine();
            assertEquals("Name,Phone,Email,Company,Tags", header);
            assertEquals(null, nextLine, "There should be no contact lines.");
        }
    }

    @Test
    public void escapeCsv_nullInput_returnsEmptyString() throws Exception {
        ExportCommand command = new ExportCommand("test.csv");
        Method m = ExportCommand.class.getDeclaredMethod("escapeCsv", String.class);
        m.setAccessible(true);
        String result = (String) m.invoke(command, (String) null);
        assertEquals("", result, "Null input should return empty string.");
    }

    @Test
    public void escapeCsv_withQuotes_escapesCorrectly() throws Exception {
        ExportCommand command = new ExportCommand("test.csv");
        Method m = ExportCommand.class.getDeclaredMethod("escapeCsv", String.class);
        m.setAccessible(true);
        String input = "Hello \"World\"";
        String result = (String) m.invoke(command, input);
        assertEquals("Hello \"\"World\"\"", result,
                "Quotes inside fields should be doubled for valid CSV formatting.");
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        ExportCommand cmd = new ExportCommand("file.csv");
        assertTrue(cmd.equals(cmd));
    }

    @Test
    public void equals_sameFilename_returnsTrue() {
        ExportCommand a = new ExportCommand("file.csv");
        ExportCommand b = new ExportCommand("file.csv");
        assertTrue(a.equals(b), "Commands with the same filename should be equal.");
    }

    @Test
    public void equals_differentFilename_returnsFalse() {
        ExportCommand a = new ExportCommand("file1.csv");
        ExportCommand b = new ExportCommand("file2.csv");
        assertTrue(!a.equals(b), "Commands with different filenames should not be equal.");
    }

    @Test
    public void equals_differentType_returnsFalse() {
        ExportCommand a = new ExportCommand("file.csv");
        Object other = "not a command";
        assertTrue(!a.equals(other), "ExportCommand should not equal non-command objects.");
    }
}
