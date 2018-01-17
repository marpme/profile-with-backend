package net.ziemers.swxercise.lg.user.service;

import net.ziemers.swxercise.db.dao.user.TradeDao;
import net.ziemers.swxercise.db.dao.user.UserDao;
import net.ziemers.swxercise.lg.model.user.Profile;
import net.ziemers.swxercise.lg.model.user.Trade;
import net.ziemers.swxercise.lg.model.user.User;
import net.ziemers.swxercise.lg.user.dto.TradeDto;
import net.ziemers.swxercise.lg.user.dto.UserDto;
import net.ziemers.swxercise.lg.user.service.TradeDtoToEntityContext;
import net.ziemers.swxercise.lg.user.service.User.UserDtoToEntityContext;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class TradeDtoToEntityContextService {

    @Inject
    private TradeDao dao;

    /**
     * Erzeugt den Erstellungs-/Aktualisierungskontext zum übergebenen Data Transfer Objekt der Zielentität.
     *
     * @param dto das Benutzer-DTO
     * @return den erzeugten Kontext.
     */
    public TradeDtoToEntityContext createContext(final TradeDto dto) {
        final TradeDtoToEntityContext ctx = new TradeDtoToEntityContext();
        final Trade existingUser = dto.getTrade();

        // das übergebene UserDto in den Kontext füllen
        ctx.dto = dto;

        // einen neuen Benutzer in den Kontext füllen
        if (existingUser == null) {
            // es darf nicht mehrere Benutzer mit dem selben Benutzernamen geben!
            if (dao.findByTitle(dto.getTitle()) == null) {

                // wir füllen das User-Objekt mit Method Chaining
                ctx.Trade = new Trade(dto.getTitle(), dto.getDescription(), dto.getAuthor());
            }
        }

        // einen bereits existierenden Benutzer in den Kontext füllen
        else {
            ctx.Trade = existingUser;
        }
        return ctx;
    }

}
