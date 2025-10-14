package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Company}.
 */
public class CompanyTest {

    @Test
    public void toString_returnsValue() {
        Company company = new Company("Suntory");
        assertEquals("Suntory", company.toString());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Company company = new Company("Suntory");
        // self-equality branch
        assertTrue(company.equals(company));
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        Company companyA = new Company("Suntory");
        Company companyB = new Company("Suntory");
        assertEquals(companyA, companyB);
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        Company companyA = new Company("Suntory");
        Company companyB = new Company("NUS Computing");
        assertNotEquals(companyA, companyB);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Company company = new Company("Suntory");
        assertNotEquals(company, "Suntory"); // trigger instanceof false branch
    }

    @Test
    public void hashCode_sameValue_returnsSameHash() {
        Company companyA = new Company("Suntory");
        Company companyB = new Company("Suntory");
        assertEquals(companyA.hashCode(), companyB.hashCode());
    }

    @Test
    public void hashCode_differentValue_returnsDifferentHash() {
        Company companyA = new Company("Suntory");
        Company companyB = new Company("NUS Computing");
        assertNotEquals(companyA.hashCode(), companyB.hashCode());
    }

    @Test
    public void hashCode_consistentAcrossCalls() {
        Company company = new Company("Suntory");
        int hash1 = company.hashCode();
        int hash2 = company.hashCode();
        assertEquals(hash1, hash2);
    }
}
