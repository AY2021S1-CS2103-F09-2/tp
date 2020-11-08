package seedu.pivot.model.investigationcase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.VALID_TITLE_BOB;
import static seedu.pivot.testutil.Assert.assertThrows;
import static seedu.pivot.testutil.TypicalCases.ALICE_PAULINE_ASSAULT;
import static seedu.pivot.testutil.TypicalCases.BOB_CHOO_SALON_THEFT;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.pivot.model.investigationcase.caseperson.Suspect;
import seedu.pivot.model.investigationcase.caseperson.Victim;
import seedu.pivot.model.investigationcase.caseperson.Witness;
import seedu.pivot.model.tag.Tag;
import seedu.pivot.testutil.CaseBuilder;
import seedu.pivot.testutil.CasePersonBuilder;

public class CaseTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Case investigationCase = new CaseBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> investigationCase.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE_PAULINE_ASSAULT.isSameCase(ALICE_PAULINE_ASSAULT));

        // null -> returns false
        assertFalse(ALICE_PAULINE_ASSAULT.isSameCase(null));

        // different title -> returns false
        Case editedAlice = new CaseBuilder(ALICE_PAULINE_ASSAULT).withTitle(VALID_TITLE_BOB).build();
        assertFalse(ALICE_PAULINE_ASSAULT.isSameCase(editedAlice));

        // different status -> returns true
        editedAlice = new CaseBuilder(ALICE_PAULINE_ASSAULT).withStatus("closed").build();
        assertTrue(ALICE_PAULINE_ASSAULT.isSameCase(editedAlice));

        // same name, different attributes -> returns true
        editedAlice = new CaseBuilder(ALICE_PAULINE_ASSAULT)
                .withTags(VALID_TAG_HUSBAND)
                .withDescription("Some Random Description").build();
        assertTrue(ALICE_PAULINE_ASSAULT.isSameCase(editedAlice));

        //TODO: Might want to test for permutations of different attributes if multiple fields in future.
        // Currently isSamePerson tests for equality in title and status.
    }

    @Test
    public void equals() {
        // same values -> returns true
        Case aliceCopy = new CaseBuilder(ALICE_PAULINE_ASSAULT).build();
        assertTrue(ALICE_PAULINE_ASSAULT.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE_PAULINE_ASSAULT.equals(ALICE_PAULINE_ASSAULT));

        // null -> returns false
        assertFalse(ALICE_PAULINE_ASSAULT.equals(null));

        // different type -> returns false
        assertFalse(ALICE_PAULINE_ASSAULT.equals(5));

        // different person -> returns false
        assertFalse(ALICE_PAULINE_ASSAULT.equals(BOB_CHOO_SALON_THEFT));

        // different title -> returns false
        Case editedAlice = new CaseBuilder(ALICE_PAULINE_ASSAULT).withTitle(VALID_TITLE_BOB).build();
        assertFalse(ALICE_PAULINE_ASSAULT.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new CaseBuilder(ALICE_PAULINE_ASSAULT).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE_PAULINE_ASSAULT.equals(editedAlice));

        // different description
        editedAlice = new CaseBuilder(ALICE_PAULINE_ASSAULT).withDescription("Some Random Description").build();
        assertFalse(ALICE_PAULINE_ASSAULT.equals(editedAlice));

        // different status
        editedAlice = new CaseBuilder(ALICE_PAULINE_ASSAULT).withStatus("CLOSED").build();
        assertFalse(ALICE_PAULINE_ASSAULT.equals(editedAlice));

        // different suspect
        Suspect suspect = new CasePersonBuilder().buildSuspect();
        editedAlice = new CaseBuilder(ALICE_PAULINE_ASSAULT).addSuspects(suspect).build();
        assertFalse(ALICE_PAULINE_ASSAULT.equals(editedAlice));

        // different witness
        Witness witness = new CasePersonBuilder().buildWitness();
        editedAlice = new CaseBuilder(ALICE_PAULINE_ASSAULT).addWitnesses(witness).build();
        assertFalse(ALICE_PAULINE_ASSAULT.equals(editedAlice));

        // different victims
        Victim victim = new CasePersonBuilder().buildVictim();
        editedAlice = new CaseBuilder(ALICE_PAULINE_ASSAULT).addVictims(victim).build();
        assertFalse(ALICE_PAULINE_ASSAULT.equals(editedAlice));

        // different document
        editedAlice = new CaseBuilder(ALICE_PAULINE_ASSAULT).withDocument("Random Name", "test1.txt").build();
        assertFalse(ALICE_PAULINE_ASSAULT.equals(editedAlice));
    }

    @Test
    public void testGetters() {
        // Title
        Title title = new Title("Hello");
        Case testCase = new CaseBuilder(ALICE_PAULINE_ASSAULT).withTitle("Hello").build();
        assertEquals(title, testCase.getTitle());

        // Description
        Description description = new Description("Testing");
        testCase = new CaseBuilder(ALICE_PAULINE_ASSAULT).withDescription("Testing").build();
        assertEquals(description, testCase.getDescription());

        // Status
        Status status = Status.ACTIVE;
        testCase = new CaseBuilder(ALICE_PAULINE_ASSAULT).withStatus("active").build();
        assertEquals(status, testCase.getStatus());

        // Suspect
        Suspect suspect = new CasePersonBuilder().buildSuspect();
        List<Suspect> suspects = new ArrayList<>();
        suspects.add(suspect);
        testCase = new CaseBuilder().addSuspects(suspect).build();
        assertEquals(suspects, testCase.getSuspects());

        // Victim
        Victim victim = new CasePersonBuilder().buildVictim();
        List<Victim> victims = new ArrayList<>();
        victims.add(victim);
        testCase = new CaseBuilder().addVictims(victim).build();
        assertEquals(victims, testCase.getVictims());

        // Witness
        Witness witness = new CasePersonBuilder().buildWitness();
        List<Witness> witnesses = new ArrayList<>();
        witnesses.add(witness);
        testCase = new CaseBuilder().addWitnesses(witness).build();
        assertEquals(witnesses, testCase.getWitnesses());

        // Document
        Document document = new Document(new Name("Test"), new Reference("test1.txt"));
        List<Document> docs = new ArrayList<>();
        docs.add(document);
        testCase = new CaseBuilder().withDocument("Test", "test1.txt").build();
        assertEquals(docs, testCase.getDocuments());

        // Tags
        Tag tag = new Tag("Test");
        HashSet<Tag> tags = new HashSet<>();
        tags.add(tag);
        testCase = new CaseBuilder().withTags("Test").build();
        assertEquals(tags, testCase.getTags());
    }
}
