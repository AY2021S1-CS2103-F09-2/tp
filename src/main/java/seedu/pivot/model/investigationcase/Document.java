package seedu.pivot.model.investigationcase;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents a Document in a Case in PIVOT.
 */
public class Document {

    public final Name name;
    public final Reference reference;

    /**
     * Constructs an {@code Document}.
     *
     * @param name A valid name.
     * @param reference A valid reference.
     */
    public Document(Name name, Reference reference) {
        requireNonNull(name);
        requireNonNull(reference);
        this.name = name;
        this.reference = reference;
    }

    public Name getName() {
        return this.name;
    }

    public Reference getReference() {
        return this.reference;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Document // instanceof handles nulls
                && name.equals(((Document) other).name)
                && reference.equals(((Document) other).reference)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, reference);
    }

    @Override
    public String toString() {
        return "Name: " + this.name + ", Reference: " + this.reference;
    }
}
