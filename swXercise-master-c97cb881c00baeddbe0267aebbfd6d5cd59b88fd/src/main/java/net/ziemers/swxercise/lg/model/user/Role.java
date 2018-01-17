package net.ziemers.swxercise.lg.model.user;

import net.ziemers.swxercise.db.BaseEntity;
import net.ziemers.swxercise.lg.user.enums.RightState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;

/**
 * Rollen sind Container für Benutzerrechte (siehe {@link net.ziemers.swxercise.lg.user.enums.RightState}.
 * Eine Rolle kann von einer Vaterrolle erben. Somit umfasst diese Rolle auch alle Rechte der Vaterrolle.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Role.findById", query = "SELECT r FROM Role r WHERE r.id = :id"),
        @NamedQuery(name = "Role.findByName", query = "SELECT r FROM Role r WHERE lower(r.name) = lower(:name)")})
public class Role extends BaseEntity {

    @NotNull
    private String name;

    private Collection<RightState> rights = new HashSet<>();

    private Role parent = null;

    public Role() {}

    public Role(final String name) {
        setName(name);
    }

    public Role(final String name, final RightState right) {
        this(name);
        rights.add(right);
    }

    public Role(final String name, final String right) {
        this(name, RightState.getByName(right));
    }

    public boolean hasRight(RightState right) {
        return rights.contains(right);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role withName(final String name) {
        setName(name);
        return this;
    }

    @ElementCollection(targetClass = RightState.class, fetch = FetchType.EAGER)
    //@JoinTable(name = "Role_rights", joinColumns = @JoinColumn(name = "Role_id"))
    //@Column(name = "rights")
    @Enumerated(EnumType.STRING)
    public Collection<RightState> getRights() {
        return rights;
    }

    public void setRights(Collection<RightState> rights) {
        this.rights = rights;
    }

    public boolean addRight(final RightState right) {
        // TODO Doubletten verhindern
        this.rights.add(right);
        return true;
    }

    @ManyToOne
    public Role getParent() {
        return parent;
    }

    public void setParent(Role parent) {
        this.parent = parent;
    }

}
