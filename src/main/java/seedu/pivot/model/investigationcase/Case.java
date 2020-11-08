package seedu.pivot.model.investigationcase;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.pivot.model.investigationcase.caseperson.Suspect;
import seedu.pivot.model.investigationcase.caseperson.Victim;
import seedu.pivot.model.investigationcase.caseperson.Witness;
import seedu.pivot.model.tag.Tag;

/**
 * Represents a Case in PIVOT.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Case {

    private static final String DESCRIPTION_HEADER = "Description: ";
    private static final String STATUS_HEADER = "Status: ";
    private static final String DOCUMENTS_HEADER = "Documents: ";
    private static final String SUSPECTS_HEADER = "Suspects: ";
    private static final String VICTIMS_HEADER = "Victims: ";
    private static final String WITNESSES_HEADER = "Witnesses: ";
    private static final String TAGS_HEADER = "Tags: ";

    // Identity fields
    private final Title title;

    // Data fields
    private final Description description;
    private final Status status;
    private final ArchiveStatus archiveStatus;
    private final List<Suspect> suspects = new ArrayList<>();
    private final List<Victim> victims = new ArrayList<>();
    private final Set<Tag> tags = new HashSet<>();
    private final List<Witness> witnesses = new ArrayList<>();
    private final List<Document> documents = new ArrayList<>();

    /**
     * Every field must be present and not null.
     */
    public Case(Title title, Description description, Status status, List<Document> documents,
                List<Suspect> suspects, List<Victim> victims, List<Witness> witnesses, Set<Tag> tags,
                ArchiveStatus archiveStatus) {
        requireAllNonNull(title, description, status, documents, suspects, victims, witnesses, tags);
        this.title = title;
        this.description = description;
        this.status = status;
        this.documents.addAll(documents);
        this.suspects.addAll(suspects);
        this.victims.addAll(victims);
        this.tags.addAll(tags);
        this.witnesses.addAll(witnesses);
        this.archiveStatus = archiveStatus;
    }

    /**
     * Creates a deep copy of a Case.
     *
     * @param toCopy Original case to be copied.
     */
    public Case(Case toCopy) {
        requireNonNull(toCopy);
        this.title = toCopy.getTitle();
        this.description = toCopy.getDescription();
        this.status = toCopy.getStatus();
        this.documents.addAll(toCopy.getDocuments());
        this.suspects.addAll(toCopy.getSuspects());
        this.victims.addAll(toCopy.getVictims());
        this.tags.addAll(toCopy.getTags());
        this.witnesses.addAll(toCopy.getWitnesses());
        this.archiveStatus = toCopy.getArchiveStatus();
    }

    public Title getTitle() {
        return new Title(title.getAlphaNum());
    }

    public Description getDescription() {
        return new Description(description.getDescription());
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Returns a copy of the list of documents in the Case.
     *
     * @return List of Documents in the Case.
     */
    public List<Document> getDocuments() {
        return documents.stream().collect(Collectors.toList());
    }

    /**
     * Returns a copy of the list of suspects in the Case.
     *
     * @return List of suspects in the Case.
     */
    public List<Suspect> getSuspects() {
        return suspects.stream().collect(Collectors.toList());
    }

    /**
     * Returns a copy of the list of victims in the Case.
     *
     * @return List of victims in the Case.
     */
    public List<Victim> getVictims() {
        return victims.stream().collect(Collectors.toList());
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns a copy of the list of witnesses in the Case.
     *
     * @return List of witnesses in the Case.
     */
    public List<Witness> getWitnesses() {
        return witnesses.stream().collect(Collectors.toList());
    }

    public ArchiveStatus getArchiveStatus() {
        return archiveStatus;
    }

    /**
     * Returns true if both cases have the same title.
     * This defines a weaker notion of equality between two cases.
     */
    public boolean isSameCase(Case otherCase) {
        if (otherCase == this) {
            return true;
        }

        return otherCase != null
                && otherCase.getTitle().equals(getTitle());
    }

    /**
     * Returns true if both cases have the same identity and data fields.
     * This defines a stronger notion of equality between two cases.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Case)) {
            return false;
        }

        Case otherCase = (Case) other;
        return otherCase.getTitle().equals(getTitle())
                && otherCase.getDescription().equals(getDescription())
                && otherCase.getStatus().equals(getStatus())
                && otherCase.getDocuments().equals(getDocuments())
                && otherCase.getSuspects().equals(getSuspects())
                && otherCase.getVictims().equals(getVictims())
                && otherCase.getWitnesses().equals(getWitnesses())
                && otherCase.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, description, status, documents,
                            suspects, victims, witnesses, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle()).append("\n")
                .append(DESCRIPTION_HEADER).append(getDescription()).append("\n")
                .append(STATUS_HEADER).append(getStatus());

        // Documents
        if (!getDocuments().isEmpty()) {
            builder.append("\n");
            builder.append(DOCUMENTS_HEADER);
        }
        Stream<String> docs = getDocuments().stream().map(x -> x.getName().toString());
        builder.append(docs.collect(Collectors.joining(", ")));

        // Suspects
        if (!getSuspects().isEmpty()) {
            builder.append("\n");
            builder.append(SUSPECTS_HEADER);
        }
        Stream<String> suspects = getSuspects().stream().map(x -> x.getName().toString());
        builder.append(suspects.collect(Collectors.joining(", ")));

        // Victims
        if (!getVictims().isEmpty()) {
            builder.append("\n");
            builder.append(VICTIMS_HEADER);
        }
        Stream<String> victims = getVictims().stream().map(x -> x.getName().toString());
        builder.append(victims.collect(Collectors.joining(", ")));

        // Witnesses
        if (!getWitnesses().isEmpty()) {
            builder.append("\n");
            builder.append(WITNESSES_HEADER);
        }
        Stream<String> witnesses = getWitnesses().stream().map(x -> x.getName().toString());
        builder.append(witnesses.collect(Collectors.joining(", ")));

        // Tags
        if (!getTags().isEmpty()) {
            builder.append("\n");
            builder.append(TAGS_HEADER);
        }
        for (Tag tag : getTags()) {
            builder.append(tag.tagName);
        }

        return builder.toString();
    }

}
