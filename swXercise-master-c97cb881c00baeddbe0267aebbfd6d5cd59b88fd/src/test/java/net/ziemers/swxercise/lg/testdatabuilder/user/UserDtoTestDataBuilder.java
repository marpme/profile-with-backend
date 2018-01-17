package net.ziemers.swxercise.lg.testdatabuilder.user;

import net.ziemers.swxercise.lg.testdatabuilder.AbstractTestDataBuilder;
import net.ziemers.swxercise.lg.user.dto.UserDto;

public class UserDtoTestDataBuilder extends AbstractTestDataBuilder<UserDto> {

    private String username = "hein";

    private String password = "secret";

    private String firstname = "Hein";

    private String lastname = "Blöd";

    private String mailaddress;

    @Override
    public UserDto build() {
        return new UserDto()
                .withUsername(username)
                .withPassword(password)
                .withFirstname(firstname)
                .withLastname(lastname)
                .withMailaddress(mailaddress);
    }

    public UserDtoTestDataBuilder withUsername(final String username) {
        this.username = username;
        return this;
    }

    public UserDtoTestDataBuilder withPassword(final String password) {
        this.password = password;
        return this;
    }

    public UserDtoTestDataBuilder withFirstname(final String firstname) {
        this.firstname = firstname;
        return this;
    }

    public UserDtoTestDataBuilder withLastname(final String lastname) {
        this.lastname = lastname;
        return this;
    }

    public UserDtoTestDataBuilder withMailaddr(final String mailaddress) {
        this.mailaddress = mailaddress;
        return this;
    }

}
