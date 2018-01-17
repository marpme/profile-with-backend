package net.ziemers.swxercise.lg.user.service.User;

import net.ziemers.swxercise.lg.model.user.User;
import net.ziemers.swxercise.lg.user.dto.UserDto;

/**
 * Mapping-Kontext von {@link User} nach {@link UserDto}. Diese Klasse dient als Hilfe bei der
 * Erstellung eines User-DTOs. Es stellt dem {@link EntityToUserDtoMapper} diejenigen
 * Informationen zur Verfügung, die er benötigt, um das DTO zu bauen.
 */
public class EntityToUserDtoContext {

    public User user;

}
