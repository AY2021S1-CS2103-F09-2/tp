package seedu.pivot.model.investigationcase;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.pivot.testutil.CaseBuilder;
import seedu.pivot.testutil.CasePersonBuilder;

public class DetailsContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        DetailsContainsKeywordsPredicate firstPredicate = new DetailsContainsKeywordsPredicate(
                firstPredicateKeywordList);
        DetailsContainsKeywordsPredicate secondPredicate = new DetailsContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DetailsContainsKeywordsPredicate firstPredicateCopy = new DetailsContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_caseContainsKeywords_returnsTrue() {
        // One keyword
        DetailsContainsKeywordsPredicate predicate = new DetailsContainsKeywordsPredicate(
                Collections.singletonList("Bank"));
        assertTrue(predicate.test(new CaseBuilder().withTitle("Bank Robbery").build()));

        // Multiple keywords, one field
        predicate = new DetailsContainsKeywordsPredicate(Arrays.asList("Bank", "Robbery"));
        assertTrue(predicate.test(new CaseBuilder().withTitle("Bank Robbery").build()));

        // Multiple keywords, muliple fields
        assertTrue(predicate.test(new CaseBuilder().withTitle("Bank").withDescription("Robbery").build()));

        // Only one matching keyword, one field
        predicate = new DetailsContainsKeywordsPredicate(Arrays.asList("Bank", "Robbery"));
        assertTrue(predicate.test(new CaseBuilder().withTitle("Bank murder").build()));

        // Mixed-case keywords, one field
        predicate = new DetailsContainsKeywordsPredicate(Arrays.asList("bAnK", "roBerRy"));
        assertTrue(predicate.test(new CaseBuilder().withTitle("Bank Robbery").build()));

        // Mixed-case keywords, multiple fields
        predicate = new DetailsContainsKeywordsPredicate(Arrays.asList("bAnK", "roBerRy"));
        assertTrue(predicate.test(new CaseBuilder().withTitle("Bank").withDescription("Robbery").build()));

        // Keywords match some fields in victims
        predicate = new DetailsContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertTrue(predicate.test(new CaseBuilder().withTitle("Bank Robbery")
                .withVictims(new CasePersonBuilder().withName("Janice").withGender("F")
                        .withEmail("alice@email.com").withAddress("123 Main Street").buildVictim())
                .build()));

        // Keywords match some fields in witness
        predicate = new DetailsContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertTrue(predicate.test(new CaseBuilder().withTitle("Bank Robbery")
                .withWitnesses(new CasePersonBuilder().withName("Janice").withGender("F")
                        .withEmail("alice@email.com").withAddress("123 Main Street").buildWitness())
                .build()));

        // Keywords match some fields in suspect
        predicate = new DetailsContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertTrue(predicate.test(new CaseBuilder().withTitle("Bank Robbery")
                .withSuspects(new CasePersonBuilder().withName("Janice").withGender("F")
                        .withEmail("alice@email.com").withAddress("123 Main Street").buildSuspect())
                .build()));

        // Keywords match some fields in documents
        predicate = new DetailsContainsKeywordsPredicate(
                Arrays.asList("12345", "alice@email.com", "./references/evidence1.txt"));
        assertTrue(predicate.test(new CaseBuilder().withTitle("Bank Robbery")
                .withDocument("Evidence found at Scene", "evidence1.txt")
                .build()));
    }

    @Test
    public void test_caseDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        DetailsContainsKeywordsPredicate predicate = new DetailsContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new CaseBuilder().withTitle("Alice").build()));

        // Non-matching keyword
        predicate = new DetailsContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new CaseBuilder().withTitle("Alice Bob").build()));
        assertFalse(predicate.test(new CaseBuilder().withTitle("Alice Bob").withDescription("Riots")
                .withStatus("CLOSED").withVictims(
                        new CasePersonBuilder().withName("Janice").withGender("F").buildVictim())
                .build()));

        //TODO: Possible test issue, mention of email here.
    }
}
