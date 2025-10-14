package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Company}.
 */
public class CompanyTest {

    @Test
    public void hashCode_sameValue_returnsSameHash() {
        Company companyA = new Company("Suntory");
        Company companyB = new Company("Suntory");

        // same hash
        assertEquals(companyA.hashCode(), companyB.hashCode());
    }

    @Test
    public void hashCode_differentValue_returnsDifferentHash() {
        Company companyA = new Company("Suntory");
        Company companyB = new Company("NUS Computing");

        // diff hash
        assertNotEquals(companyA.hashCode(), companyB.hashCode());
    }

    @Test
    public void hashCode_consistentAcrossCalls() {
        Company company = new Company("Suntory");
        int hash1 = company.hashCode();
        int hash2 = company.hashCode();

        // same hash
        assertEquals(hash1, hash2);
    }
}
