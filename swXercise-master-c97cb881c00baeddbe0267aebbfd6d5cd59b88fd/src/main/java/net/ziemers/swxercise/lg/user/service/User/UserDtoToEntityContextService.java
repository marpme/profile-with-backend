package net.ziemers.swxercise.lg.user.service.User;

import javax.ejb.Stateless;
import javax.inject.Inject;

import net.ziemers.swxercise.db.dao.user.UserDao;
import net.ziemers.swxercise.lg.model.user.Profile;
import net.ziemers.swxercise.lg.model.user.User;
import net.ziemers.swxercise.lg.user.dto.UserDto;

@Stateless
public class UserDtoToEntityContextService {

    @Inject
    private UserDao dao;

    /**
     * Erzeugt den Erstellungs-/Aktualisierungskontext zum übergebenen Data Transfer Objekt der Zielentität.
     *
     * @param dto das Benutzer-DTO
     * @return den erzeugten Kontext.
     */
    public UserDtoToEntityContext createContext(final UserDto dto) {
        final UserDtoToEntityContext ctx = new UserDtoToEntityContext();
        final User existingUser = dto.getUser();

        // das übergebene UserDto in den Kontext füllen
        ctx.dto = dto;

        // einen neuen Benutzer in den Kontext füllen
        if (existingUser == null) {
            // es darf nicht mehrere Benutzer mit dem selben Benutzernamen geben!
            if (dao.findByUsername(dto.getUsername()) == null) {
                // es soll niemals ein Profil ohne Benutzername und Kennwort geben
                ctx.profile = new Profile(dto.getUsername(), dto.getPassword());

                // wir füllen das User-Objekt mit Method Chaining
                ctx.user = new User(dto.getFirstname(), dto.getLastname())
                        .withProfile(ctx.profile);
            }
        }

        // einen bereits existierenden Benutzer in den Kontext füllen
        else {
            ctx.user = existingUser;
            ctx.profile = ctx.user.getProfile();
            ctx.address = ctx.user.getAddress();
        }
        return ctx;
    }

}
