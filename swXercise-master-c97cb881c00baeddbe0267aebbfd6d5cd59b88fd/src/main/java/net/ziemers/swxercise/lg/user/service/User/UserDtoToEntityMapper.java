package net.ziemers.swxercise.lg.user.service.User;

import net.ziemers.swxercise.lg.user.dto.UserDto;

import javax.ejb.Stateless;

/**
 * Mapper zur Konvertierung des Kontexts eines {@link UserDto}s in eine User-Entity.
 */
@Stateless
public class UserDtoToEntityMapper {

    /**
     * Überträgt die Eigenschaften aus dem UserDto sowie dem zusätzlichen Kontext in die Zielentitäten.
     * Anzumerken ist, dass der Benutzername einer existierenden Profile-Entität niemals aktualisiert
     * wird.
     *
     * @param ctx der Kontext mit den Eigenschaften und der Zielentität
     * @return den Kontext.
     */
    public UserDtoToEntityContext map(UserDtoToEntityContext ctx) {
        // ins User-Objekt mappen
        ctx.user.setFirstname(ctx.dto.getFirstname());
        ctx.user.setLastname(ctx.dto.getLastname());

        // ins Profile-Objekt mappen, falls gegeben
        if (ctx.profile != null) {
            ctx.user.setProfile(ctx.profile);

            // eventuell ein neues Kenntwort ins Profile-Objekt mappen
            if(ctx.dto.getPassword().length() > 0) {
                ctx.profile.setPassword(ctx.dto.getPassword());
            }
            ctx.profile.setMailaddress(ctx.dto.getMailaddress());
        }

        // ins Address-Objekt mappen, falls gegeben
        if (ctx.address != null) {
            ctx.user.setAddress(ctx.address);
        }
        return ctx;
    }

}
