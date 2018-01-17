package net.ziemers.swxercise.lg.user.dto;

import net.ziemers.swxercise.lg.model.user.User;

import javax.validation.constraints.NotNull;

/**
 * Das Data Transfer Object im Kontext der Benutzerverwaltung. Es wird unter anderem auch aus einem
 * JSON-Objekt des {@link net.ziemers.swxercise.ui.UserViewController}s gefüllt.
 */
public class UserDto {

    private User user = null;

    @NotNull
    private String username;    // aus dem Profile

    @NotNull
    private String password = "";    // aus dem Profile

    private String firstname;

    private String lastname;

    private String mailaddress; // aus dem Profile

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public UserDto withUsername(final String username) {
        this.username = username;
        return this;
    }

    public String getPassword() { return password; }

    public UserDto withPassword(final String password) {
        this.password = password;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public UserDto withFirstname(final String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public UserDto withLastname(final String lastname) {
        this.lastname = lastname;
        return this;
    }

    public String getMailaddress() {
        return mailaddress;
    }

    public UserDto withMailaddress(final String mailaddress) {
        this.mailaddress = mailaddress;
        return this;
    }

}
