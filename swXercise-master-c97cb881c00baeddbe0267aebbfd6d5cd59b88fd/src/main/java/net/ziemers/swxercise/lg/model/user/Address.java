package net.ziemers.swxercise.lg.model.user;

import javax.persistence.Entity;

import net.ziemers.swxercise.db.BaseEntity;

/**
 * Verwaltet die Adressdaten eines Benutzers.
 */
@Entity
public class Address extends BaseEntity {

    private String street;

    private String zipcode;

    private String city;

    private String country;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
