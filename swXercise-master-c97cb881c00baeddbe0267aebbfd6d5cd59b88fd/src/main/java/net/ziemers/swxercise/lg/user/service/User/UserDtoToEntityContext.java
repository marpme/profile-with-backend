package net.ziemers.swxercise.lg.user.service.User;

import net.ziemers.swxercise.lg.model.user.Address;
import net.ziemers.swxercise.lg.model.user.Profile;
import net.ziemers.swxercise.lg.model.user.User;
import net.ziemers.swxercise.lg.user.dto.UserDto;

public class UserDtoToEntityContext {

    public UserDto dto;

    public User user;

    public Profile profile;

    public Address address = null;

}
