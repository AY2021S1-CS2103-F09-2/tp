package seedu.pivot.logic.commands.witnesscommands;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_CASE_PAGE;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_VALID_INDEX;
import static seedu.pivot.commons.core.UserMessages.MESSAGE_DUPLICATE_WITNESS;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_SEX;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.pivot.commons.core.LogsCenter;
import seedu.pivot.commons.core.UserMessages;
import seedu.pivot.commons.core.index.Index;
import seedu.pivot.logic.commands.CommandResult;
import seedu.pivot.logic.commands.EditPersonCommand;
import seedu.pivot.logic.commands.Page;
import seedu.pivot.logic.commands.Undoable;
import seedu.pivot.logic.commands.exceptions.CommandException;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.Model;
import seedu.pivot.model.investigationcase.Case;
import seedu.pivot.model.investigationcase.Name;
import seedu.pivot.model.investigationcase.caseperson.Address;
import seedu.pivot.model.investigationcase.caseperson.Email;
import seedu.pivot.model.investigationcase.caseperson.Phone;
import seedu.pivot.model.investigationcase.caseperson.Sex;
import seedu.pivot.model.investigationcase.caseperson.Witness;

/**
 * Represents an Edit command for editing a Witness in a Case in PIVOT.
 */
public class EditWitnessCommand extends EditPersonCommand implements Undoable {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + TYPE_WITNESS
            + ": Edits a person in the opened case in PIVOT.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_SEX + "GENDER] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS]\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_WITNESS + " 1 "
            + PREFIX_EMAIL + "newEmail@mail.com "
            + PREFIX_ADDRESS + "New Road Crescent\n\n";

    public static final String MESSAGE_EDIT_WITNESS_SUCCESS = "Edited Witness: %1$s";

    private static final Page pageType = Page.CASE;
    private static final Logger logger = LogsCenter.getLogger(EditWitnessCommand.class);

    public EditWitnessCommand(Index caseIndex, Index personIndex, EditPersonDescriptor editPersonDescriptor) {
        super(caseIndex, personIndex, editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Editing a witness in the opened case...");
        requireNonNull(model);

        List<Case> lastShownList = model.getFilteredCaseList();

        assert(StateManager.atCasePage()) : ASSERT_CASE_PAGE;
        assert(caseIndex.getZeroBased() < lastShownList.size()) : ASSERT_VALID_INDEX;

        Case caseToEdit = lastShownList.get(caseIndex.getZeroBased());
        List<Witness> editedWitnesses = caseToEdit.getWitnesses();

        if (personIndex.getZeroBased() >= editedWitnesses.size()) {
            throw new CommandException(UserMessages.MESSAGE_INVALID_WITNESS_DISPLAYED_INDEX);
        }

        Witness witnessToEdit = editedWitnesses.get(personIndex.getZeroBased());
        Witness editedWitness = createEditedPerson(witnessToEdit, editPersonDescriptor);

        if (editedWitnesses.contains(editedWitness)) {
            logger.info("Failed to edit witness: The edited witness has the same name, sex, phone, "
                    + "email and address as an existing witness in PIVOT.");
            throw new CommandException(MESSAGE_DUPLICATE_WITNESS);
        }

        List<Witness> witnessesToNotEdit = new ArrayList<>(editedWitnesses);
        witnessesToNotEdit.remove(witnessToEdit);
        if (witnessesToNotEdit.stream().anyMatch(editedWitness:: isSamePerson)) {
            logger.info("Failed to edit witness: The edited witness has the same name, sex, phone as an "
                    + "existing witness in PIVOT.");
            throw new CommandException(MESSAGE_DUPLICATE_WITNESS);
        }

        editedWitnesses.set(personIndex.getZeroBased(), editedWitness);
        Case editedCase = new Case(caseToEdit.getTitle(), caseToEdit.getDescription(), caseToEdit.getStatus(),
                caseToEdit.getDocuments(), caseToEdit.getSuspects(), caseToEdit.getVictims(), editedWitnesses,
                caseToEdit.getTags(), caseToEdit.getArchiveStatus());

        model.setCase(caseToEdit, editedCase);
        model.commitPivot(String.format(MESSAGE_EDIT_WITNESS_SUCCESS, editedWitness), this);

        return new CommandResult(String.format(MESSAGE_EDIT_WITNESS_SUCCESS, editedWitness));
    }

    private Witness createEditedPerson(Witness witnessToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert witnessToEdit != null : "Witness provided is null";
        assert editPersonDescriptor != null : "EditPersonDescriptor provided is null";

        Name updatedName = editPersonDescriptor.getName().orElse(witnessToEdit.getName());
        Sex updatedSex = editPersonDescriptor.getSex().orElse(witnessToEdit.getSex());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(witnessToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(witnessToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(witnessToEdit.getAddress());

        return new Witness(updatedName, updatedSex, updatedPhone, updatedEmail, updatedAddress);
    }

    @Override
    public Page getPage() {
        return pageType;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditWitnessCommand)) {
            return false;
        }

        EditWitnessCommand otherEditWitnessCommand = (EditWitnessCommand) other;
        return otherEditWitnessCommand.caseIndex.equals(caseIndex)
                && otherEditWitnessCommand.personIndex.equals(personIndex)
                && otherEditWitnessCommand.editPersonDescriptor.equals(editPersonDescriptor);
    }
}
