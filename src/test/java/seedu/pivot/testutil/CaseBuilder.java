package seedu.pivot.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.pivot.model.investigationcase.ArchiveStatus;
import seedu.pivot.model.investigationcase.Case;
import seedu.pivot.model.investigationcase.Description;
import seedu.pivot.model.investigationcase.Document;
import seedu.pivot.model.investigationcase.Name;
import seedu.pivot.model.investigationcase.Reference;
import seedu.pivot.model.investigationcase.Status;
import seedu.pivot.model.investigationcase.Title;
import seedu.pivot.model.investigationcase.caseperson.Suspect;
import seedu.pivot.model.investigationcase.caseperson.Victim;
import seedu.pivot.model.investigationcase.caseperson.Witness;
import seedu.pivot.model.tag.Tag;
import seedu.pivot.model.util.SampleDataUtil;

/**
 * A utility class to help with building Case objects.
 */
public class CaseBuilder {

    public static final String DEFAULT_TITLE = "Kovan Thefts";
    public static final String DEFAULT_DESCRIPTION = "";
    public static final String DEFAULT_STATUS = "active";

    private Title title;
    private Description description;
    private Status status;
    private List<Document> documents;
    private List<Suspect> suspects;
    private List<Victim> victims;
    private Set<Tag> tags;
    private List<Witness> witnesses;
    private ArchiveStatus archiveStatus;

    /**
     * Creates a {@code CaseBuilder} with the default details.
     */
    public CaseBuilder() {
        title = new Title(DEFAULT_TITLE);
        description = new Description(DEFAULT_DESCRIPTION);
        status = Status.createStatus(DEFAULT_STATUS);
        documents = new ArrayList<>();
        suspects = new ArrayList<>();
        victims = new ArrayList<>();
        witnesses = new ArrayList<>();
        tags = new HashSet<>();
        archiveStatus = ArchiveStatus.DEFAULT;
    }

    /**
     * Initializes the CaseBuilder with the data of {@code caseToCopy}.
     */
    public CaseBuilder(Case caseToCopy) {
        title = caseToCopy.getTitle();
        description = caseToCopy.getDescription();
        status = caseToCopy.getStatus();
        documents = caseToCopy.getDocuments();
        suspects = caseToCopy.getSuspects();
        victims = caseToCopy.getVictims();
        witnesses = caseToCopy.getWitnesses();
        tags = new HashSet<>(caseToCopy.getTags());
        archiveStatus = caseToCopy.getArchiveStatus();
    }

    /**
     * Sets the {@code Title} of the {@code Case} that we are building.
     */
    public CaseBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Case} that we are building.
     */
    public CaseBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Case} that we are building.
     */
    public CaseBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Case} that we are building.
     */
    public CaseBuilder withStatus(String status) {
        this.status = Status.createStatus(status);
        return this;
    }

    /**
     * Sets the {@code Document} of the {@code Case} that we are building.
     */
    public CaseBuilder withDocument(String name, String ref) {
        if (this.documents == null) {
            this.documents = new ArrayList<>();
        }
        this.documents.add(new Document(new Name(name), new Reference(ref)));
        return this;
    }

    /**
     * Parses the {@code suspects} into a {@code List<Suspect>} and set it to the {@code Case} that we are building.
     */
    public CaseBuilder withSuspects(Suspect ... suspects) {
        this.suspects = Arrays.asList(suspects);
        return this;
    }

    /**
     * Parses the {@code victims} into a {@code List<Victim>} and set it to the {@code Case} that we are building.
     */
    public CaseBuilder withVictims(Victim ... victims) {
        this.victims = Arrays.asList(victims);
        return this;
    }

    /**
     * Parses the {@code witnesses} into a {@code List<Witness>} and set it to the {@code Case} that we are building.
     */
    public CaseBuilder withWitnesses(Witness ... witnesses) {
        this.witnesses = Arrays.asList(witnesses);
        return this;
    }

    /**
     * Parses the {@code suspects} into a {@code List<Suspect>} and adds it to the {@code Case} that we are building.
     */
    public CaseBuilder addSuspects(Suspect ... suspects) {
        if (this.suspects == null) {
            this.suspects = new ArrayList<>();
        }
        this.suspects.addAll(Arrays.asList(suspects));
        return this;
    }

    /**
     * Parses the {@code victims} into a {@code List<Victim>} and adds it to the {@code Case} that we are building.
     */
    public CaseBuilder addVictims(Victim ... victims) {
        if (this.victims == null) {
            this.victims = new ArrayList<>();
        }
        this.victims.addAll(Arrays.asList(victims));
        return this;
    }

    /**
     * Parses the {@code witnesses} into a {@code List<Witness>} and adds it to the {@code Case} that we are building.
     */
    public CaseBuilder addWitnesses(Witness ... witnesses) {
        if (this.witnesses == null) {
            this.witnesses = new ArrayList<>();
        }
        this.witnesses.addAll(Arrays.asList(witnesses));
        return this;
    }

    /**
     * Sets the {@code ArchiveStatus} of the {@code Case} that we are building.
     */
    public CaseBuilder withArchiveStatus(ArchiveStatus archiveStatus) {
        this.archiveStatus = archiveStatus;
        return this;
    }

    /**
     * Generates a {@code Case} object with existing fields.
     * @return Person object
     */
    public Case build() {
        return new Case(title, description, status, documents, suspects, victims, witnesses, tags,
                archiveStatus);
    }

}
