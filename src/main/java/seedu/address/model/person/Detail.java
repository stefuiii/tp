package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's detail note in the contact book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDetail(String)}
 */
public class Detail {

    public static final int MAX_LENGTH = 300;
    public static final String MESSAGE_CONSTRAINTS =
            "Detail can take any values, and must be at most " + MAX_LENGTH + " characters. "
            + "It can be empty.";

    public static final String EMPTY_DETAIL = "";

    public final String value;

    /**
     * Constructs a {@code Detail}.
     *
     * @param detail A valid detail.
     */
    public Detail(String detail) {
        requireNonNull(detail);
        checkArgument(isValidDetail(detail), MESSAGE_CONSTRAINTS);
        value = detail;
    }

    /**
     * Returns true if a given string is a valid detail value.
     */
    public static boolean isValidDetail(String test) {
        return test.length() <= MAX_LENGTH;
    }

    /**
     * Returns true if this detail is empty.
     */
    public boolean isEmpty() {
        return value.isEmpty();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Detail)) {
            return false;
        }

        Detail otherDetail = (Detail) other;
        return value.equals(otherDetail.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

