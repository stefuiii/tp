package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

/**
 * Unit tests for {@code NameOrCompanyPredicate}.
 */
public class NameOrCompanyPredicateTest {

    @Test
    public void equals() {
        NameOrCompanyPredicate firstPredicate =
                new NameOrCompanyPredicate(Optional.of("Alice"), Optional.empty());
        NameOrCompanyPredicate secondPredicate =
                new NameOrCompanyPredicate(Optional.of("Bob"), Optional.empty());
        NameOrCompanyPredicate thirdPredicate =
                new NameOrCompanyPredicate(Optional.empty(), Optional.of("Google"));

        // same object -> true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> true
        NameOrCompanyPredicate firstPredicateCopy =
                new NameOrCompanyPredicate(Optional.of("Alice"), Optional.empty());
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> false
        assertFalse(firstPredicate.equals(1));

        // null -> false
        assertFalse(firstPredicate.equals(null));

        // different name keyword -> false
        assertFalse(firstPredicate.equals(secondPredicate));

        // different company keyword -> false
        assertFalse(firstPredicate.equals(thirdPredicate));
    }

    @Test
    public void test_nameMatches_success() {
        // Exact match
        NameOrCompanyPredicate predicate =
                new NameOrCompanyPredicate(Optional.of("Alice"), Optional.empty());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Pauline").build()));

        // Partial match
        predicate = new NameOrCompanyPredicate(Optional.of("Ali"), Optional.empty());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Pauline").build()));

        // Case-insensitive match
        predicate = new NameOrCompanyPredicate(Optional.of("aLiCe"), Optional.empty());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Pauline").build()));
    }

    @Test
    public void test_nameDoesNotMatch_returnsFalse() {
        NameOrCompanyPredicate predicate =
                new NameOrCompanyPredicate(Optional.of("Bob"), Optional.empty());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Pauline").build()));
    }

    @Test
    public void test_companyKeywordMatches_success() {
        Person person = new PersonBuilder().withCompany("Google SG").build();

        // Case insensitive
        NameOrCompanyPredicate predicate1 = new NameOrCompanyPredicate(Optional.empty(), Optional.of("google"));
        assertTrue(predicate1.test(person));

        // Different word
        NameOrCompanyPredicate predicate2 = new NameOrCompanyPredicate(Optional.empty(), Optional.of("SG"));
        assertTrue(predicate2.test(person));

        // Wrong word
        NameOrCompanyPredicate predicate3 = new NameOrCompanyPredicate(Optional.empty(), Optional.of("Amazon"));
        assertFalse(predicate3.test(person));

        // Partial token
        NameOrCompanyPredicate predicate4 = new NameOrCompanyPredicate(Optional.empty(), Optional.of("oog"));
        assertFalse(predicate4.test(person));
    }


    @Test
    public void test_companyDoesNotMatch_returnsFalse() {
        NameOrCompanyPredicate predicate =
                new NameOrCompanyPredicate(Optional.empty(), Optional.of("Amazon"));
        assertFalse(predicate.test(new PersonBuilder().withCompany("Google").build()));
    }

    @Test
    public void test_bothNameAndCompanyMatch_success() {
        NameOrCompanyPredicate predicate =
                new NameOrCompanyPredicate(Optional.of("Alice"), Optional.of("Google"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Pauline").withCompany("Google").build()));
    }

    @Test
    public void test_nameAndCompanyMismatch_returnsFalse() {
        // Name matches but company doesn't
        NameOrCompanyPredicate predicate =
                new NameOrCompanyPredicate(Optional.of("Alice"), Optional.of("Amazon"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Pauline").withCompany("Google").build()));

        // Company matches but name doesn't
        predicate = new NameOrCompanyPredicate(Optional.of("Bob"), Optional.of("Google"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Pauline").withCompany("Google").build()));
    }

    @Test
    public void test_noKeywords_returnsFalse() {
        NameOrCompanyPredicate predicate =
                new NameOrCompanyPredicate(Optional.empty(), Optional.empty());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withCompany("Google").build()));
    }

    @Test
    public void equals_companyKeywordVariants() {
        NameOrCompanyPredicate predicateA =
                new NameOrCompanyPredicate(Optional.of("Alice"), Optional.of("Google"));
        NameOrCompanyPredicate predicateBSame =
                new NameOrCompanyPredicate(Optional.of("Alice"), Optional.of("Google"));
        NameOrCompanyPredicate predicateCDiffCompany =
                new NameOrCompanyPredicate(Optional.of("Alice"), Optional.of("Amazon"));
        NameOrCompanyPredicate predicateDDiffName =
                new NameOrCompanyPredicate(Optional.of("Bob"), Optional.of("Google"));

        // same object -> true
        assertTrue(predicateA.equals(predicateA));

        // same name & company -> true
        assertTrue(predicateA.equals(predicateBSame));

        // different company keyword -> false
        assertFalse(predicateA.equals(predicateCDiffCompany));

        // different name keyword -> false
        assertFalse(predicateA.equals(predicateDDiffName));

        // different type -> false
        assertFalse(predicateA.equals("Some String"));

        // null -> false
        assertFalse(predicateA.equals(null));
    }

}
