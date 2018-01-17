package net.ziemers.swxercise.lg.model.user;

import javax.persistence.*;

import net.ziemers.swxercise.db.BaseEntity;

/**
 *  Stellt die Stammdaten eines Benutzers zur Verfügung. Nutzt hierbei verschiedene Subklassen.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
        @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE lower(u.profile.username) = lower(:username)")})
public class User extends BaseEntity {

    private String firstname;

    private String lastname;

    private Profile profile;

    private Address address;

    public User() {
        super();
    }

    public User(final String firstname, final String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Transient
    public String getFullName() {
        return String.format("%s %s", getFirstname(), getLastname());
    }

    @OneToOne(cascade = {CascadeType.ALL})
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public User withProfile(final Profile profile) {
        setProfile(profile);
        return this;
    }

    @OneToOne(cascade = {CascadeType.ALL})
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User withAddress(final Address address) {
        setAddress(address);
        return this;
    }

}
