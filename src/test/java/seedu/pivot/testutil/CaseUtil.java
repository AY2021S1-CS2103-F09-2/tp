package seedu.pivot.testutil;

import static seedu.pivot.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.pivot.logic.commands.AddCommand;
import seedu.pivot.logic.commands.EditCommand.EditCaseDescriptor;
import seedu.pivot.model.investigationcase.Case;

/**
 * A utility class for Case.
 */
public class CaseUtil {

    /**
     * Returns an add command string for adding the {@code Case}.
     */
    public static String getAddCommand(Case investigationCase) {
        return AddCommand.COMMAND_WORD + " " + AddCommand.TYPE_CASE + " " + getCaseDetails(investigationCase);
    }

    /**
     * Returns the part of command string for the given {@code Case}'s details.
     */
    public static String getCaseDetails(Case investigationCase) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TITLE + investigationCase.getTitle().getAlphaNum() + " ");
        sb.append(PREFIX_STATUS + investigationCase.getStatus().name() + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditCaseDescriptor}'s details.
     */
    public static String getEditCaseDescriptorDetails(EditCaseDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getTitle().ifPresent(title -> sb.append(PREFIX_TITLE).append(title.getAlphaNum()).append(" "));
        descriptor.getStatus().ifPresent(status -> sb.append(PREFIX_STATUS).append(status.name()).append(" "));
        return sb.toString();
    }
}
