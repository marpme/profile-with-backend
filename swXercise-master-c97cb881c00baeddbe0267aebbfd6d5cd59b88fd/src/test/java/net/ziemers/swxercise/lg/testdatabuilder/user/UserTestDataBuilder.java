package net.ziemers.swxercise.lg.testdatabuilder.user;

import net.ziemers.swxercise.lg.model.user.Address;
import net.ziemers.swxercise.lg.model.user.Profile;
import net.ziemers.swxercise.lg.model.user.User;
import net.ziemers.swxercise.lg.testdatabuilder.AbstractTestDataBuilder;

import javax.persistence.EntityManager;

public class UserTestDataBuilder extends AbstractTestDataBuilder<User> {

    private String firstname = "Hein";

    private String lastname = "Blöd";

    private Profile profile;

    private Address address = null;

    public UserTestDataBuilder(final EntityManager em) {
        super(em);
        profile = new ProfileTestDataBuilder(em).build();
    }

    public UserTestDataBuilder() {
        this(null);
    }

    @Override
    public User build() {
        return new User(firstname, lastname)
                .withProfile(profile)
                .withAddress(address);
    }

    public UserTestDataBuilder withFirstname(final String firstname) {
        this.firstname = firstname;
        return this;
    }

    public UserTestDataBuilder withLastname(final String lastname) {
        this.lastname = lastname;
        return this;
    }

    public UserTestDataBuilder withProfile(final Profile profile) {
        this.profile = profile;
        return this;
    }

    public UserTestDataBuilder withAddress(final Address address) {
        this.address = address;
        return this;
    }

}

