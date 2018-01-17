package net.ziemers.swxercise.lg.user.service.User;

import net.ziemers.swxercise.lg.model.user.User;
import net.ziemers.swxercise.lg.user.dto.UserDto;

/**
 * Mapper zur Konvertierung des Kontexts einer {@link User}-Entity in ein {@link UserDto}.
 */
public class EntityToUserDtoMapper {

    /**
     * Erzeugt ein Data Transfer Object aus dem übergebenem Kontext.
     *
     * @param ctx der Kontext
     * @return das erzeugte DTO.
     */
    public UserDto map(EntityToUserDtoContext ctx) {
        final UserDto dto = new UserDto();

        if (ctx.user != null) {
          mapUser(ctx, dto);
          mapFirstname(ctx, dto);
          mapLastname(ctx, dto);
        }
        return dto;
    }

    private void mapUser(EntityToUserDtoContext ctx, UserDto dto) {
        dto.setUser(ctx.user);
    }

    private void mapFirstname(EntityToUserDtoContext ctx, UserDto dto) {
        dto.setFirstname(ctx.user.getFirstname());
    }

    private void mapLastname(EntityToUserDtoContext ctx, UserDto dto) {
        dto.setLastname(ctx.user.getLastname());
    }

}
