package seedu.pivot.model.investigationcase.caseperson;

import static seedu.pivot.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.pivot.model.investigationcase.Name;

/**
 * Encapsulates a Person related to an Investigation Case.
 */
public abstract class CasePerson {
    // Identity fields
    private final Name name;
    private final Sex sex;
    private final Phone phone;

    // Data fields
    private final Email email;
    private final Address address;

    /**
     * Every field must be present and not null.
     */
    public CasePerson(Name name, Sex sex, Phone phone, Email email, Address address) {
        requireAllNonNull(name, sex, phone, email, address);
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Name getName() {
        return name;
    }

    public Sex getSex() {
        return sex;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public abstract boolean equals(Object other);

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, sex, phone, email, address);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Sex: ")
                .append(getSex());

        if (!phone.toString().isBlank()) {
            builder.append(" Phone: ")
                    .append(getPhone());
        }

        if (!email.toString().isBlank()) {
            builder.append(" Email: ")
                    .append(getEmail());
        }

        if (!address.toString().isBlank()) {
            builder.append(" Address: ")
                    .append(getAddress());
        }

        return builder.toString();
    }


}
