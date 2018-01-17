package net.ziemers.swxercise.lg.user.dto;

import javax.validation.constraints.NotNull;

/**
 * Das Data Transfer Object im Kontext der Rollen- und Rechteverwaltung. Es wird unter anderem auch aus einem
 * JSON-Objekt des {@link net.ziemers.swxercise.ui.RoleViewController}s gefüllt.
 */
public class RoleDto {

    @NotNull
    private String name;

    @NotNull
    private String right;

    public String getName() { return name; }

    public RoleDto withName(final String name) {
        this.name = name;
        return this;
    }

    public String getRight() { return right; }

    public RoleDto withRight(final String right) {
        this.right = right;
        return this;
    }

}
