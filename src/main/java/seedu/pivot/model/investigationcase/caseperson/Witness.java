package seedu.pivot.model.investigationcase.caseperson;

import seedu.pivot.model.investigationcase.Name;

/**
 * Represents a Witness in the investigation case.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Witness extends CasePerson {

    /**
     * Constructs a {@code Witness}.
     *
     * @param name A valid name.
     */
    public Witness(Name name, Sex sex, Phone phone, Email email, Address address) {
        super(name, sex, phone, email, address);
    }

    /**
     * Returns true if both cases have the same name, sex and phone.
     * This defines a weaker notion of equality between two witnesses.
     */
    public boolean isSamePerson(Witness otherWitness) {
        if (otherWitness == this) {
            return true;
        }

        return otherWitness.getName().equals(getName())
                && otherWitness.getSex().equals(getSex())
                && otherWitness.getPhone().equals(getPhone());
    }

    /**
     * Returns true if both suspects have the same identity and data fields.
     * This defines a stronger notion of equality between two witnesses.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Witness)) {
            return false;
        }

        Witness otherWitness = (Witness) other;
        return otherWitness.getName().equals(getName())
                && otherWitness.getSex().equals(getSex())
                && otherWitness.getPhone().equals(getPhone())
                && otherWitness.getEmail().equals(getEmail())
                && otherWitness.getAddress().equals(getAddress());
    }

}
