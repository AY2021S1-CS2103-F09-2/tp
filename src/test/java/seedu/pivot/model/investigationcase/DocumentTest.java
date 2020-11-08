package seedu.pivot.model.investigationcase;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pivot.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DocumentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Document(new Name("name"), null));
        assertThrows(NullPointerException.class, () -> new Document(null, new Reference("valid")));
    }

    @Test
    public void equals() {
        Document document = new Document(new Name("name"), new Reference("reference"));
        Document duplicateDocument = new Document(new Name("name"), new Reference("reference"));
        Document differentName = new Document(new Name("Bob"), new Reference("reference"));
        Document differentReference = new Document(new Name("name"), new Reference("ref"));
        assertTrue(document.equals(document));
        assertTrue(document.equals(duplicateDocument));
        assertFalse(document.equals(differentName));
        assertFalse(document.equals(differentReference));
    }
}
