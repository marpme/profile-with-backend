package net.ziemers.swxercise.lg.user.service;

import net.ziemers.swxercise.db.dao.user.TradeDao;
import net.ziemers.swxercise.db.dao.user.UserDao;
import net.ziemers.swxercise.lg.model.user.Role;
import net.ziemers.swxercise.lg.model.user.Trade;
import net.ziemers.swxercise.lg.model.user.User;
import net.ziemers.swxercise.lg.user.dto.TradeDto;
import net.ziemers.swxercise.lg.user.dto.UserDto;
import net.ziemers.swxercise.lg.user.enums.RightState;
import net.ziemers.swxercise.lg.user.service.User.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Set;

/**
 * Diese Klasse stellt alle Dienste im Kontext einer Benutzerverwaltung zur Verfügung.
 */
@Stateless
public class TradeService {

    @Inject
    private TradeDao dao;

    @Inject
    private TradeDtoToEntityContextService ctxService;

    @Inject
    private TradeDtoToEntityMapper mapper;

    @Inject
    private UserService userService;

    /**
     * Findet den Benutzer mit der übergebenen Id.
     *
     * @param id die Id des gesuchten Benutzes.
     * @return den gesuchten Benutzer, oder <code>null</code>, falls ein solcher nicht existiert.
     */
    public Trade findTrade(final Long id) {
        return dao.findById(id);
    }

    /**
     * Findet alle existierenden Benutzer.
     *
     * @return alle Benutzer, oder eine leere Collection, falls keine existieren.
     */
    public Collection<Trade> findAllTrade() {
        return dao.findAll(Trade.class);
    }

    /**
     * Erstellt einen neuen Benutzer, sofern noch keiner mit dem selben Benutzernamen existiert.
     * Zwischen der Groß- und Kleinschreibung wird nicht unterschieden.
     *
     * @param dto das {@link UserDto} enthält die Eigenschaften des zu erstellenden Benutzers
     * @return <code>true</code>, wenn das Aktualisieren des Benutzers erfolgreich war.
     */
    public boolean createTrade(final TradeDto dto) {
        // Kontext für den zu erstellenden Benutzer erzeugen; falls bereits ein Benutzer
        // mit dem selben Benutzernamen existiert, wird kein Benutzerobjekt zurückgeliefert
        dto.setAuthor(userService.findUser());
        final TradeDtoToEntityContext ctx = ctxService.createContext(dto);
        if (ctx.Trade != null) {
            mapper.map(ctx);
            return dao.saveOrUpdate(ctx.Trade) != null;
        }
        return false;
    }


}
