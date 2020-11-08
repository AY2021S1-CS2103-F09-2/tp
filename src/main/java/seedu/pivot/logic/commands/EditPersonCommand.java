package seedu.pivot.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Optional;

import seedu.pivot.commons.core.index.Index;
import seedu.pivot.commons.util.CollectionUtil;
import seedu.pivot.model.investigationcase.Name;
import seedu.pivot.model.investigationcase.caseperson.Address;
import seedu.pivot.model.investigationcase.caseperson.Email;
import seedu.pivot.model.investigationcase.caseperson.Phone;
import seedu.pivot.model.investigationcase.caseperson.Sex;

/**
 * Represents an Edit command for editing a Case Person in PIVOT.
 */
public abstract class EditPersonCommand extends EditCommand {

    protected final Index caseIndex;
    protected final Index personIndex;
    protected final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param caseIndex of the Case Person in the filtered person list to edit.
     * @param editPersonDescriptor contains details to edit the person with.
     */
    public EditPersonCommand(Index caseIndex, Index personIndex, EditPersonDescriptor editPersonDescriptor) {
        requireAllNonNull(caseIndex, personIndex, editPersonDescriptor);
        this.caseIndex = caseIndex;
        this.personIndex = personIndex;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with.
     * Each non-empty field value will replace the corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Sex sex;
        private Phone phone;
        private Email email;
        private Address address;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            requireNonNull(toCopy);
            setName(toCopy.name);
            setSex(toCopy.sex);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, sex, phone, email, address);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setSex(Sex sex) {
            this.sex = sex;
        }

        public Optional<Sex> getSex() {
            return Optional.ofNullable(sex);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getSex().equals(e.getSex())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress());
        }
    }
}
