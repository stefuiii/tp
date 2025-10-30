package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DetailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Detail(null));
    }

    @Test
    public void constructor_invalidDetail_throwsIllegalArgumentException() {
        String invalidDetail = "a".repeat(301); // 301 characters
        assertThrows(IllegalArgumentException.class, () -> new Detail(invalidDetail));
    }

    @Test
    public void isValidDetail() {
        // null detail
        assertThrows(NullPointerException.class, () -> Detail.isValidDetail(null));

        // invalid details (exceeds max length)
        assertFalse(Detail.isValidDetail("a".repeat(301))); // 301 characters
        assertFalse(Detail.isValidDetail("a".repeat(500))); // 500 characters

        // valid details
        assertTrue(Detail.isValidDetail("")); // empty string
        assertTrue(Detail.isValidDetail(" ")); // single space
        assertTrue(Detail.isValidDetail("Short note")); // normal detail
        assertTrue(Detail.isValidDetail("a".repeat(300))); // exactly 300 characters
        assertTrue(Detail.isValidDetail("This is a longer note with multiple words and special characters !@#$%^&*()"));
    }

    @Test
    public void isEmpty() {
        assertTrue(new Detail("").isEmpty());
        assertFalse(new Detail(" ").isEmpty());
        assertFalse(new Detail("Some detail").isEmpty());
    }

    @Test
    public void equals() {
        Detail detail = new Detail("Hello");

        // same values -> returns true
        assertTrue(detail.equals(new Detail("Hello")));

        // same object -> returns true
        assertTrue(detail.equals(detail));

        // null -> returns false
        assertFalse(detail.equals(null));

        // different types -> returns false
        assertFalse(detail.equals(5.0f));

        // different values -> returns false
        assertFalse(detail.equals(new Detail("Bye")));

        // empty details
        Detail emptyDetail = new Detail("");
        assertTrue(emptyDetail.equals(new Detail("")));
    }

    @Test
    public void hashCode_test() {
        Detail detail1 = new Detail("Test");
        Detail detail2 = new Detail("Test");
        Detail detail3 = new Detail("Different");

        // same values -> same hash code
        assertEquals(detail1.hashCode(), detail2.hashCode());

        // different values -> likely different hash code
        // (not guaranteed but very likely)
        assertTrue(detail1.hashCode() != detail3.hashCode());
    }

    @Test
    public void toString_test() {
        assertEquals("Test detail", new Detail("Test detail").toString());
        assertEquals("", new Detail("").toString());
    }

    @Test
    public void maxLengthDetail() {
        String maxLengthDetail = "a".repeat(300);
        Detail detail = new Detail(maxLengthDetail);
        assertEquals(maxLengthDetail, detail.toString());
    }
}

