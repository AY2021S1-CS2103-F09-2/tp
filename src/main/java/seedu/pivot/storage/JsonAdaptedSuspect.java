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
import seedu.pivot.model.investigationcase.caseperson.Suspect;


/**
 * Jackson-friendly version of {@link Suspect}.
 */
public class JsonAdaptedSuspect {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Suspect's %s field is missing!";
    private static final Logger logger = LogsCenter.getLogger(JsonAdaptedSuspect.class);

    private final String name;
    private final String sex;
    private final String phone;
    private final String email;
    private final String address;

    /**
     * Constructs a {@code JsonAdaptedSuspect} with the given suspect details.
     */
    @JsonCreator
    public JsonAdaptedSuspect(@JsonProperty("name") String name, @JsonProperty("sex") String sex,
                              @JsonProperty("phone") String phone, @JsonProperty("email") String email,
                              @JsonProperty("address") String address) {
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    /**
     * Converts a given {@code Suspect} into this class for Jackson use.
     */
    public JsonAdaptedSuspect(Suspect source) {
        this.name = source.getName().getAlphaNum();
        this.sex = source.getSex().toString();
        this.phone = source.getPhone().toString();
        this.email = source.getEmail().toString();
        this.address = source.getAddress().toString();
    }

    /**
     * Converts this Jackson-friendly adapted suspect object into the model's {@code Suspect} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted suspect.
     */
    public Suspect toModelType() throws IllegalValueException {
        logger.info("Converting JSON to Suspect");
        if (name == null) {
            logger.warning("Suspect name is null. Check data");
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            logger.warning("Suspect name is invalid. Check data");
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (sex == null) {
            logger.warning("Suspect sex is null. Check data");
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Sex.class.getSimpleName()));
        }
        if (!Sex.isValidSex(sex)) {
            logger.warning("Suspect gender is invalid. Check data");
            throw new IllegalValueException(Sex.MESSAGE_CONSTRAINTS);
        }
        final Sex modelSex = Sex.createSex(sex);

        if (phone == null) {
            logger.warning("Suspect phone is null. Check data");
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            logger.warning("Suspect phone is invalid. Check data");
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            logger.warning("Suspect email is null. Check data");
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            logger.warning("Suspect email is invalid. Check data");
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            logger.warning("Suspect address is null. Check data");
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        final Address modelAddress = new Address(address);

        return new Suspect(modelName, modelSex, modelPhone, modelEmail, modelAddress);
    }
}
