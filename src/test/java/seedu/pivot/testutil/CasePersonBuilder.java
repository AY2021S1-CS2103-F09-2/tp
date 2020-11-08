package seedu.pivot.testutil;

import seedu.pivot.model.investigationcase.Name;
import seedu.pivot.model.investigationcase.caseperson.Address;
import seedu.pivot.model.investigationcase.caseperson.CasePerson;
import seedu.pivot.model.investigationcase.caseperson.Email;
import seedu.pivot.model.investigationcase.caseperson.Phone;
import seedu.pivot.model.investigationcase.caseperson.Sex;
import seedu.pivot.model.investigationcase.caseperson.Suspect;
import seedu.pivot.model.investigationcase.caseperson.Victim;
import seedu.pivot.model.investigationcase.caseperson.Witness;

/**
 * A utility class to help with building CasePerson objects.
 */
public class CasePersonBuilder {

    public static final String DEFAULT_NAME = "Tom Holland";
    public static final String DEFAULT_SEX = "M";
    public static final String DEFAULT_PHONE = "91234567";
    public static final String DEFAULT_EMAIL = "";
    public static final String DEFAULT_ADDRESS = "";

    // Identity fields
    private Name name;
    private Sex sex;

    // Data fields
    private Phone phone;
    private Email email;
    private Address address;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public CasePersonBuilder() {
        name = new Name(DEFAULT_NAME);
        sex = Sex.createSex(DEFAULT_SEX);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
    }

    /**
     * Initializes the CasePersonBuilder with the data of {@code personToCopy}.
     */
    public CasePersonBuilder(CasePerson personToCopy) {
        name = personToCopy.getName();
        sex = personToCopy.getSex();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
    }

    /**
     * Sets the {@code Name} of the {@code CasePerson} that we are building.
     */
    public CasePersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Sex} of the {@code CasePerson} that we are building.
     */
    public CasePersonBuilder withSex(String gender) {
        this.sex = Sex.createSex(gender);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code CasePerson} that we are building.
     */
    public CasePersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code CasePerson} that we are building.
     */
    public CasePersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code CasePerson} that we are building.
     */
    public CasePersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Generates a {@code Suspect} object with existing fields.
     * @return Suspect object
     */
    public Suspect buildSuspect() {
        return new Suspect(name, sex, phone, email, address);
    }

    /**
     * Generates a {@code Witness} object with existing fields.
     * @return Witness object
     */
    public Witness buildWitness() {
        return new Witness(name, sex, phone, email, address);
    }

    /**
     * Generates a {@code Victim} object with existing fields.
     * @return Victim object
     */
    public Victim buildVictim() {
        return new Victim(name, sex, phone, email, address);
    }
}
