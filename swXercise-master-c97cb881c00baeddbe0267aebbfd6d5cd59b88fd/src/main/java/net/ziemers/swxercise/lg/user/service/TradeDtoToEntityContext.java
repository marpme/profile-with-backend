package net.ziemers.swxercise.lg.user.service;

import net.ziemers.swxercise.lg.model.user.Address;
import net.ziemers.swxercise.lg.model.user.Profile;
import net.ziemers.swxercise.lg.model.user.Trade;
import net.ziemers.swxercise.lg.model.user.User;
import net.ziemers.swxercise.lg.user.dto.TradeDto;
import net.ziemers.swxercise.lg.user.dto.UserDto;

public class TradeDtoToEntityContext {

    public TradeDto dto;

    public Trade Trade;

    public User Author;
}
