package seedu.pivot.storage;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.pivot.commons.core.LogsCenter;
import seedu.pivot.commons.exceptions.IllegalValueException;
import seedu.pivot.model.investigationcase.Name;
import seedu.pivot.model.investigationcase.caseperson.Address;
import seedu.pivot.model.investigationcase.caseperson.Email;
import seedu.pivot.model.investigationcase.caseperson.Phone;
import seedu.pivot.model.investigationcase.caseperson.Sex;
import seedu.pivot.model.investigationcase.caseperson.Witness;



/**
 * Jackson-friendly version of {@link Witness}.
 */
public class JsonAdaptedWitness {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Witness' %s field is missing!";
    private static final Logger logger = LogsCenter.getLogger(JsonAdaptedWitness.class);

    private final String name;
    private final String sex;
    private final String phone;
    private final String email;
    private final String address;

    /**
     * Constructs a {@code JsonAdaptedWitness} with the given {@code witnessName}.
     */
    @JsonCreator
    public JsonAdaptedWitness(@JsonProperty("name") String name, @JsonProperty("sex") String sex,
                              @JsonProperty("phone") String phone, @JsonProperty("email") String email,
                              @JsonProperty("address") String address) {
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    /**
     * Converts a given {@code Witness} into this class for Jackson use.
     */
    public JsonAdaptedWitness(Witness source) {
        this.name = source.getName().getAlphaNum();
        this.sex = source.getSex().toString();
        this.phone = source.getPhone().toString();
        this.email = source.getEmail().toString();
        this.address = source.getAddress().toString();
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Witness} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted witness.
     */
    public Witness toModelType() throws IllegalValueException {
        logger.info("Converting JSON to Witness");
        if (name == null) {
            logger.warning("Witness name is null. Check data");
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            logger.warning("Witness name is invalid. Check data");
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (sex == null) {
            logger.warning("Witness gender is null. Check data");
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Sex.class.getSimpleName()));
        }
        if (!Sex.isValidSex(sex)) {
            logger.warning("Witness gender is invalid. Check data");
            throw new IllegalValueException(Sex.MESSAGE_CONSTRAINTS);
        }
        final Sex modelSex = Sex.createSex(sex);

        if (phone == null) {
            logger.warning("Witness phone is null. Check data");
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            logger.warning("Witness phone is invalid. Check data");
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            logger.warning("Witness email is null. Check data");
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            logger.warning("Witness email is invalid. Check data");
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            logger.warning("Witness address is null. Check data");
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        final Address modelAddress = new Address(address);

        return new Witness(modelName, modelSex, modelPhone, modelEmail, modelAddress);
    }

}
