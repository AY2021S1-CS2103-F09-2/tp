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
import seedu.pivot.model.investigationcase.caseperson.Victim;


/**
 * Jackson-friendly version of {@link Victim}.
 */
public class JsonAdaptedVictim {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Victim's %s field is missing!";
    private static final Logger logger = LogsCenter.getLogger(JsonAdaptedVictim.class);

    private final String name;
    private final String sex;
    private final String phone;
    private final String email;
    private final String address;

    /**
     * Constructs a {@code JsonAdaptedVictim} with the given {@code name}.
     */
    @JsonCreator
    public JsonAdaptedVictim(@JsonProperty("name") String name, @JsonProperty("sex") String sex,
                             @JsonProperty("phone") String phone, @JsonProperty("email") String email,
                             @JsonProperty("address") String address) {
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    /**
     * Converts a given {@code Victim} into this class for Jackson use.
     */
    public JsonAdaptedVictim(Victim source) {
        this.name = source.getName().getAlphaNum();
        this.sex = source.getSex().toString();
        this.phone = source.getPhone().toString();
        this.email = source.getEmail().toString();
        this.address = source.getAddress().toString();
    }

    /**
     * Converts this Jackson-friendly adapted victim object into the model's {@code Victim} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted victim.
     */
    public Victim toModelType() throws IllegalValueException {
        logger.info("Converting JSON to Victim");
        if (name == null) {
            logger.warning("Victim name is null. Check data");
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            logger.warning("Victim name is invalid. Check data");
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (sex == null) {
            logger.warning("Victim gender is null. Check data");
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Sex.class.getSimpleName()));
        }
        if (!Sex.isValidSex(sex)) {
            logger.warning("Victim sex is invalid. Check data");
            throw new IllegalValueException(Sex.MESSAGE_CONSTRAINTS);
        }
        final Sex modelSex = Sex.createSex(sex);

        if (phone == null) {
            logger.warning("Victim phone is null. Check data");
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            logger.warning("Victim phone is invalid. Check data");
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            logger.warning("Victim email is null. Check data");
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            logger.warning("Victim email is invalid. Check data");
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            logger.warning("Victim address is null. Check data");
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        final Address modelAddress = new Address(address);

        return new Victim(modelName, modelSex, modelPhone, modelEmail, modelAddress);
    }
}
