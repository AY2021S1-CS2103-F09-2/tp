package seedu.pivot.logic.commands.victimcommands;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_CASE_PAGE;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_VALID_INDEX;
import static seedu.pivot.commons.core.UserMessages.MESSAGE_DUPLICATE_SUSPECT;
import static seedu.pivot.commons.core.UserMessages.MESSAGE_DUPLICATE_VICTIM;
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
import seedu.pivot.model.investigationcase.caseperson.Victim;

/**
 * Represents an Edit command for editing a Victim in a Case in PIVOT.
 */
public class EditVictimCommand extends EditPersonCommand implements Undoable {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + TYPE_VICTIM
            + ": Edits a person in the opened case in PIVOT.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_SEX + "GENDER] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS]\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_VICTIM + " 1 "
            + PREFIX_EMAIL + "newEmail@mail.com "
            + PREFIX_ADDRESS + "New Road Crescent\n\n";

    public static final String MESSAGE_EDIT_VICTIM_SUCCESS = "Edited Victim: %1$s";

    private static final Page pageType = Page.CASE;
    private static final Logger logger = LogsCenter.getLogger(EditVictimCommand.class);

    public EditVictimCommand(Index caseIndex, Index personIndex, EditPersonDescriptor editPersonDescriptor) {
        super(caseIndex, personIndex, editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Editing a victim in the opened case...");
        requireNonNull(model);

        List<Case> lastShownList = model.getFilteredCaseList();

        assert(StateManager.atCasePage()) : ASSERT_CASE_PAGE;
        assert(caseIndex.getZeroBased() < lastShownList.size()) : ASSERT_VALID_INDEX;

        Case caseToEdit = lastShownList.get(caseIndex.getZeroBased());
        List<Victim> editedVictims = caseToEdit.getVictims();

        if (personIndex.getZeroBased() >= editedVictims.size()) {
            throw new CommandException(UserMessages.MESSAGE_INVALID_VICTIM_DISPLAYED_INDEX);
        }

        Victim victimToEdit = editedVictims.get(personIndex.getZeroBased());
        Victim editedVictim = createEditedPerson(victimToEdit, editPersonDescriptor);

        if (editedVictims.contains(editedVictim)) {
            logger.info("Failed to edit victim: The edited victim has the same name, sex, phone, "
                    + "email and address as an existing victim in PIVOT.");
            throw new CommandException(MESSAGE_DUPLICATE_VICTIM);
        }

        List<Victim> victimsToNotEdit = new ArrayList<>(editedVictims);
        victimsToNotEdit.remove(victimToEdit);
        if (victimsToNotEdit.stream().anyMatch(editedVictim:: isSamePerson)) {
            logger.info("Failed to edit victim: The edited victim has the same name, sex, phone as an "
                    + "existing victim in PIVOT.");
            throw new CommandException(MESSAGE_DUPLICATE_SUSPECT);
        }

        editedVictims.set(personIndex.getZeroBased(), editedVictim);
        Case editedCase = new Case(caseToEdit.getTitle(), caseToEdit.getDescription(), caseToEdit.getStatus(),
                caseToEdit.getDocuments(), caseToEdit.getSuspects(), editedVictims, caseToEdit.getWitnesses(),
                caseToEdit.getTags(), caseToEdit.getArchiveStatus());

        model.setCase(caseToEdit, editedCase);
        model.commitPivot(String.format(MESSAGE_EDIT_VICTIM_SUCCESS, editedVictim), this);

        return new CommandResult(String.format(MESSAGE_EDIT_VICTIM_SUCCESS, editedVictim));
    }

    private Victim createEditedPerson(Victim victimToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert victimToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(victimToEdit.getName());
        Sex updatedSex = editPersonDescriptor.getSex().orElse(victimToEdit.getSex());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(victimToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(victimToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(victimToEdit.getAddress());

        return new Victim(updatedName, updatedSex, updatedPhone, updatedEmail, updatedAddress);
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

        if (!(other instanceof EditVictimCommand)) {
            return false;
        }

        EditVictimCommand otherEditVictimCommand = (EditVictimCommand) other;
        return otherEditVictimCommand.caseIndex.equals(caseIndex)
                && otherEditVictimCommand.personIndex.equals(personIndex)
                && otherEditVictimCommand.editPersonDescriptor.equals(editPersonDescriptor);
    }
}
