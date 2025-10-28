package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_COMPANY = new Prefix("c/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_TAG_ADD = new Prefix("t+/");
    public static final Prefix PREFIX_TAG_DELETE = new Prefix("t-/");
    public static final Prefix PREFIX_FIELD = new Prefix("f/");
    public static final Prefix PREFIX_ORDER = new Prefix("o/");

}
