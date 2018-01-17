package net.ziemers.swxercise.lg.user.service;

import net.ziemers.swxercise.lg.model.user.User;
import net.ziemers.swxercise.lg.user.dto.TradeDto;
import net.ziemers.swxercise.lg.user.dto.UserDto;
import net.ziemers.swxercise.lg.user.service.User.EntityToUserDtoContext;

/**
 * Mapper zur Konvertierung des Kontexts einer {@link User}-Entity in ein {@link UserDto}.
 */
public class EntityToTradeDtoMapper {

    /**
     * Erzeugt ein Data Transfer Object aus dem übergebenem Kontext.
     *
     * @param ctx der Kontext
     * @return das erzeugte DTO.
     */
    public TradeDto map(EntityToTradeDtoContext ctx) {
        final TradeDto dto = new TradeDto();

        if (ctx.trade != null) {
            mapTrade(ctx, dto);
            mapDescription(ctx, dto);
            mapTitle(ctx, dto);
        }
        return dto;
    }

    private void mapTrade(EntityToTradeDtoContext ctx, TradeDto dto) {
        dto.setTrade(ctx.trade);
    }

    private void mapDescription(EntityToTradeDtoContext ctx, TradeDto dto) {
        dto.setDescription(ctx.trade.getDescription());
    }

    private void mapTitle(EntityToTradeDtoContext ctx, TradeDto dto) { dto.setTitle(ctx.trade.getTitle()); }
}
