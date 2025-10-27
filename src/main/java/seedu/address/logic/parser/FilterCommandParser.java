package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIELD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagsContainTagPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    private static final Logger logger = LogsCenter.getLogger(FilterCommandParser.class);
    private static final Prefix[] DISALLOWED_PREFIXES = new Prefix[]{
            PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_COMPANY, PREFIX_FIELD, PREFIX_ORDER
    };

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     *
     * @throws ParseException if the user does not confirm to the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        logger.fine("Parsing filter command with arguments: " + args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        checkValidTokens(args, argMultimap);

        TagsContainTagPredicate predicate =
                new TagsContainTagPredicate(argMultimap.getAllValues(PREFIX_TAG).stream()
                        .map(token -> token.toLowerCase())
                        .distinct()
                        .map(token -> new Tag(token))
                        .toList());

        logger.info("Successfully parsed filter command with tags: "
                + argMultimap.getAllValues(PREFIX_TAG));

        return new FilterCommand(predicate);
    }

    /**
     * Checks if the {@code argMultmap} object is valid after tokenizing.
     *
     * Checks include:
     * <ul>
     *   <li>Check that PREFIX_TAG tokens exist</li>
     *   <li>Ensure that there is no input between filter and first prefix</li>
     *   <li>Ensure that there are no empty tags</li>
     *   <li>Ensure that all tags are valid names</li>
     * </ul>
     *
     * Postcondition: argMultimap contains mapping for PREFIX_TAG
     * where the mappings are valid tag names.
     * @param argMultimap Tokenized multimap to be checked
     * @throws ParseException If {@code argMultimap} is invalid
     */
    private void checkValidTokens(String args, ArgumentMultimap argMultimap) throws ParseException {
        String errorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE);

        if (!argMultimap.getValue(PREFIX_TAG).isPresent()) {
            logger.warning("No tags provided in filter command");
            throw new ParseException(errorMessage);
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            logger.warning("Preamble found in filter command");
            throw new ParseException(errorMessage);
        }

        List<String> tagValues = argMultimap.getAllValues(PREFIX_TAG);

        boolean containsEmptyTag = tagValues.stream()
                .anyMatch(tagName -> tagName.isEmpty());

        if (containsEmptyTag) {
            logger.warning("Empty tag found in filter command");
            throw new ParseException(errorMessage);
        }

        if (containsTagWithLeadingWhitespace(args, tagValues)) {
            logger.warning("Whitespace found between tag prefix and tag value");
            throw new ParseException(errorMessage);
        }

        if (tagValues.stream()
                .anyMatch(tagName -> !Tag.isValidTagName(tagName))) {
            logger.warning("Invalid tag name found in filter command");
            throw new ParseException(errorMessage);
        }

        boolean containsUnexpectedPrefixes = tagValues.stream()
                .anyMatch(tagName -> containsDisallowedPrefix(tagName));

        if (containsUnexpectedPrefixes) {
            logger.warning("Unexpected prefix found in filter command");
            throw new ParseException(errorMessage);
        }
    }

    private boolean containsDisallowedPrefix(String tagName) {
        return Stream.of(DISALLOWED_PREFIXES)
                .map(prefix -> prefix.getPrefix())
                .anyMatch(prefix -> tagName.startsWith(prefix) || tagName.contains(" " + prefix));
    }

    private boolean containsTagWithLeadingWhitespace(String args, List<String> tagValues) {
        return tagValues.stream()
                .filter(tagName -> !tagName.isEmpty())
                .anyMatch(tagName -> hasLeadingWhitespaceBetweenPrefixAndValue(args, tagName));
    }

    private boolean hasLeadingWhitespaceBetweenPrefixAndValue(String args, String tagName) {
        String tagSpacingPattern = String.format("(^|\\s)%s\\s+%s(?=\\s|$)",
                Pattern.quote(PREFIX_TAG.getPrefix()), Pattern.quote(tagName));
        return Pattern.compile(tagSpacingPattern).matcher(args).find();
    }
}
