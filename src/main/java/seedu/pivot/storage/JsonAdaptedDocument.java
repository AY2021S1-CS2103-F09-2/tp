package seedu.pivot.storage;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.pivot.commons.core.LogsCenter;
import seedu.pivot.commons.exceptions.IllegalValueException;
import seedu.pivot.model.investigationcase.Document;
import seedu.pivot.model.investigationcase.Name;
import seedu.pivot.model.investigationcase.Reference;

/**
 * Jackson-friendly version of {@link Document}.
 */
class JsonAdaptedDocument {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Document's %s field is missing!";
    private static final Logger logger = LogsCenter.getLogger(JsonAdaptedDocument.class);

    private final String name;
    private final String reference;

    /**
     * Constructs a {@code JsonAdaptedDocument} with the given {@code documentStorageName }.
     */
    @JsonCreator
    public JsonAdaptedDocument(@JsonProperty("name") String name, @JsonProperty("reference") String reference) {
        this.name = name;
        this.reference = reference;
    }

    /**
     * Converts a given {@code Document} into this class for Jackson use.
     */
    public JsonAdaptedDocument(Document source) {
        name = source.getName().getAlphaNum();
        reference = source.getReference().getFileName();
    }


    /**
     * Converts this Jackson-friendly adapted Document object into the model's {@code Document} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Document.
     */
    public Document toModelType() throws IllegalValueException {
        logger.info("Converting JSON to Document");
        if (name == null) {
            logger.warning("Document name is null. Check data");
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "document name"));
        }
        if (reference == null) {
            logger.warning("Document reference is null. Check data");
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "document reference"));
        }
        if (!Name.isValidName(name)) {
            logger.warning("Document name is invalid. Check data");
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        if (!Reference.isValidReference(reference)) {
            logger.warning("Document reference is invalid. Check data");
            throw new IllegalValueException(Reference.MESSAGE_CONSTRAINTS);
        }
        return new Document(new Name(name), new Reference(reference));
    }

}
