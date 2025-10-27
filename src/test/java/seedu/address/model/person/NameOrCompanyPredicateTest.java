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
    public void test_companyMatches_success() {
        // Exact match
        NameOrCompanyPredicate predicate =
                new NameOrCompanyPredicate(Optional.empty(), Optional.of("Google"));
        assertTrue(predicate.test(new PersonBuilder().withCompany("Google").build()));

        // Partial match
        predicate = new NameOrCompanyPredicate(Optional.empty(), Optional.of("oog"));
        assertTrue(predicate.test(new PersonBuilder().withCompany("Google").build()));

        // Case-insensitive
        predicate = new NameOrCompanyPredicate(Optional.empty(), Optional.of("gOOgle"));
        assertTrue(predicate.test(new PersonBuilder().withCompany("Google").build()));
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
        NameOrCompanyPredicate predicateSame =
                new NameOrCompanyPredicate(Optional.of("Alice"), Optional.of("Google"));
        NameOrCompanyPredicate predicateDiffCompany =
                new NameOrCompanyPredicate(Optional.of("Alice"), Optional.of("Amazon"));
        NameOrCompanyPredicate predicateDiffName =
                new NameOrCompanyPredicate(Optional.of("Bob"), Optional.of("Google"));

        // same object -> true
        assertTrue(predicateA.equals(predicateA));

        // same name & company -> true
        assertTrue(predicateA.equals(predicateSame));

        // different company keyword -> false
        assertFalse(predicateA.equals(predicateDiffCompany));

        // different name keyword -> false
        assertFalse(predicateA.equals(predicateDiffName));

        // different type -> false
        assertFalse(predicateA.equals("Some String"));

        // null -> false
        assertFalse(predicateA.equals(null));
    }

}
