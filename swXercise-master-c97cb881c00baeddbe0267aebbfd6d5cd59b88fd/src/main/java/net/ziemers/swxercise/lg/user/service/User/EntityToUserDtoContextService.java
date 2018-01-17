package net.ziemers.swxercise.lg.user.service.User;

import net.ziemers.swxercise.lg.model.user.User;
import net.ziemers.swxercise.lg.user.dto.UserDto;

import javax.ejb.Stateless;

/**
 * Service zum Erzeugen eines Mapping-Kontexts. Das Mapping verläuft von {@link User} nach {@link UserDto}.
 * Diese Klasse dient dazu, einen neuen Kontext zu erstellen, welcher dem {@link EntityToUserDtoMapper} dazu dient,
 * aus einer User-Entity ein entsprechendes DTO zu bauen.
 */
@Stateless
public class EntityToUserDtoContextService {

    /**
     * Erzeugt einen Mapping-Kontext für einen neuen Benutzer.
     *
     * @return den Mapping-Kontext.
     */
    public EntityToUserDtoContext createContext() {
        final EntityToUserDtoContext ctx = new EntityToUserDtoContext();
        return ctx;
    }

}
