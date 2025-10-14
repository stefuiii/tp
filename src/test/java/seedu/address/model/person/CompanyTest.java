package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CompanyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Company(null));
    }

    @Test
    public void constructor_invalidCompany_throwsIllegalArgumentException() {
        String invalidCompany = "";
        assertThrows(IllegalArgumentException.class, () -> new Company(invalidCompany));
    }

    @Test
    public void isValidCompany() {
        // null company
        assertThrows(NullPointerException.class, () -> Company.isValidCompany(null));

        // invalid company names
        assertFalse(Company.isValidCompany("")); // empty string
        assertFalse(Company.isValidCompany("   ")); // spaces only
        assertFalse(Company.isValidCompany("@#$$")); // symbols only

        // valid company names
        assertTrue(Company.isValidCompany("Google"));
        assertTrue(Company.isValidCompany("NUS Computing"));
        assertTrue(Company.isValidCompany("ByteDance Singapore"));
    }

    @Test
    public void equals() {
        Company google = new Company("Google");
        Company googleCopy = new Company("Google");
        Company apple = new Company("Apple");

        // same object -> returns true
        assertTrue(google.equals(google));

        // same value -> returns true
        assertTrue(google.equals(googleCopy));

        // different value -> returns false
        assertFalse(google.equals(apple));

        // different type -> returns false
        assertFalse(google.equals(1));

        // null -> returns false
        assertFalse(google.equals(null));
    }

    @Test
    public void toString_returnsValue() {
        Company company = new Company("NUS Computing");
        assertEquals("NUS Computing", company.toString());
    }
}
