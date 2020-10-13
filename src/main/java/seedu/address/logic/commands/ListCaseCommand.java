package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CASES;

import seedu.address.model.Model;

public class ListCaseCommand extends ListCommand {

    public static final String MESSAGE_SUCCESS = "Listed all cases";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCaseList(PREDICATE_SHOW_ALL_CASES);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof ListCaseCommand; // instanceof handles nulls
    }

}
