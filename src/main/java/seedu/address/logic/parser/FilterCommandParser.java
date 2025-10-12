package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagsContainTagPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     *
     * @throws ParseException if the user does not confirm to the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        checkValidTokens(argMultimap);

        TagsContainTagPredicate predicate =
                new TagsContainTagPredicate(argMultimap.getAllValues(PREFIX_TAG).stream()
                                                       .map(token -> token.toLowerCase())
                                                       .map(token -> new Tag(token))
                                                       .toList());

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
    private void checkValidTokens(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getValue(PREFIX_TAG).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        boolean containsEmptyTag = argMultimap.getAllValues(PREFIX_TAG).stream()
                                              .anyMatch(tagName -> tagName.isEmpty());

        if (containsEmptyTag) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getAllValues(PREFIX_TAG).stream()
                       .anyMatch(tagName -> !Tag.isValidTagName(tagName))) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
    }
}
