package seedu.pivot.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.pivot.model.Pivot;
import seedu.pivot.model.ReadOnlyPivot;
import seedu.pivot.model.investigationcase.ArchiveStatus;
import seedu.pivot.model.investigationcase.Case;
import seedu.pivot.model.investigationcase.Description;
import seedu.pivot.model.investigationcase.Document;
import seedu.pivot.model.investigationcase.Name;
import seedu.pivot.model.investigationcase.Reference;
import seedu.pivot.model.investigationcase.Status;
import seedu.pivot.model.investigationcase.Title;
import seedu.pivot.model.investigationcase.caseperson.Address;
import seedu.pivot.model.investigationcase.caseperson.Email;
import seedu.pivot.model.investigationcase.caseperson.Phone;
import seedu.pivot.model.investigationcase.caseperson.Sex;
import seedu.pivot.model.investigationcase.caseperson.Suspect;
import seedu.pivot.model.investigationcase.caseperson.Victim;
import seedu.pivot.model.investigationcase.caseperson.Witness;
import seedu.pivot.model.tag.Tag;

/**
 * Contains utility methods for populating {@code PIVOT} with sample data.
 */
public class SampleDataUtil {

    private static final List<Suspect> sampleSuspects = new ArrayList<>(
            List.of(new Suspect(new Name("Tom"), Sex.M, new Phone("91234567"),
                    new Email("abc@gmail.com"), new Address("Blk 123 Beach Road"))));
    private static final List<Witness> sampleWitnesses = new ArrayList<>(
            List.of(new Witness(new Name("John"), Sex.M, new Phone("91234567"),
                            new Email("abc@gmail.com"), new Address("Blk 123 Beach Road")),
                    new Witness(new Name("Janice"), Sex.F, new Phone("91234567"),
                            new Email("abc@gmail.com"), new Address("Blk 123 Beach Road"))));
    private static final List<Victim> sampleVictims = new ArrayList<>(
            List.of(new Victim(new Name("Mary"), Sex.F, new Phone("91234567"),
                            new Email("abc@gmail.com"), new Address("Blk 123 Beach Road"))));
    private static final List<Document> sampleDocuments =
            List.of(new Document(new Name("name"), new Reference("test1.txt")));
    private static final Description sampleDescription = new Description("7 people arrested for rioting");

    public static Case[] getSampleCases() {
        return new Case[] {
            new Case(new Title("Ang Mo Kio Car Theft"), sampleDescription,
                        Status.createStatus("active"), sampleDocuments, sampleSuspects, sampleVictims,
                        sampleWitnesses, getTagSet(), ArchiveStatus.DEFAULT),
            new Case(new Title("Bishan Shopping Theft"), sampleDescription,
                        Status.createStatus("closed"), sampleDocuments,
                        sampleSuspects, sampleVictims, sampleWitnesses, getTagSet(), ArchiveStatus.DEFAULT),
            new Case(new Title("Cha Bee Hoon Stall Fire"), sampleDescription,
                        Status.createStatus("cold"), sampleDocuments,
                        sampleSuspects, sampleVictims, sampleWitnesses, getTagSet(), ArchiveStatus.DEFAULT),
            new Case(new Title("Dhoby Ghaut Murder Case"), sampleDescription,
                        Status.createStatus("active"), sampleDocuments,
                        sampleSuspects, sampleVictims, sampleWitnesses, getTagSet(), ArchiveStatus.DEFAULT),
            new Case(new Title("IceCream Man Harassment"), sampleDescription,
                        Status.createStatus("active"), sampleDocuments,
                        sampleSuspects, sampleVictims, sampleWitnesses, getTagSet(), ArchiveStatus.DEFAULT),
            new Case(new Title("Klook Scam"), sampleDescription,
                        Status.createStatus("active"), sampleDocuments,
                        sampleSuspects, sampleVictims, sampleWitnesses, getTagSet(), ArchiveStatus.ARCHIVED),
            new Case(new Title("Roti Prata Shop Robbery"), sampleDescription,
                        Status.createStatus("active"), sampleDocuments,
                        sampleSuspects, sampleVictims, sampleWitnesses, getTagSet(), ArchiveStatus.ARCHIVED)
        };
    }

    public static ReadOnlyPivot getSamplePivot() {
        Pivot sampleAb = new Pivot();
        for (Case sampleCase : getSampleCases()) {
            sampleAb.addCase(sampleCase);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
