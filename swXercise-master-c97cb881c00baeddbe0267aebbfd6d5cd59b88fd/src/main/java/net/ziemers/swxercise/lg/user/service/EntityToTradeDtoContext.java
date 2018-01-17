package net.ziemers.swxercise.lg.user.service;

import net.ziemers.swxercise.lg.model.user.Trade;
import net.ziemers.swxercise.lg.model.user.User;
import net.ziemers.swxercise.lg.user.dto.UserDto;
import net.ziemers.swxercise.lg.user.service.User.EntityToUserDtoMapper;

/**
 * Mapping-Kontext von {@link User} nach {@link UserDto}. Diese Klasse dient als Hilfe bei der
 * Erstellung eines User-DTOs. Es stellt dem {@link EntityToUserDtoMapper} diejenigen
 * Informationen zur Verfügung, die er benötigt, um das DTO zu bauen.
 */
public class EntityToTradeDtoContext {

    public Trade trade;

}
