package net.ziemers.swxercise.lg.user.service;

import net.ziemers.swxercise.lg.user.dto.UserDto;
import net.ziemers.swxercise.lg.user.service.User.UserDtoToEntityContext;

import javax.ejb.Stateless;

/**
 * Mapper zur Konvertierung des Kontexts eines {@link UserDto}s in eine User-Entity.
 */
@Stateless
public class TradeDtoToEntityMapper {

    /**
     * Überträgt die Eigenschaften aus dem UserDto sowie dem zusätzlichen Kontext in die Zielentitäten.
     * Anzumerken ist, dass der Benutzername einer existierenden Profile-Entität niemals aktualisiert
     * wird.
     *
     * @param ctx der Kontext mit den Eigenschaften und der Zielentität
     * @return den Kontext.
     */
    public TradeDtoToEntityContext map(TradeDtoToEntityContext ctx) {
        // ins User-Objekt mappen
        ctx.Trade.setTitle(ctx.dto.getTitle());
        ctx.Trade.setDescription(ctx.dto.getDescription());

        // ins Profile-Objekt mappen, falls gegeben
        if (ctx.Author != null) {
            ctx.Trade.setCreator(ctx.Author);
        }
        return ctx;
    }

}
