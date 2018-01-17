package net.ziemers.swxercise.lg.user.service.User;

import net.ziemers.swxercise.db.dao.user.RoleDao;
import net.ziemers.swxercise.lg.model.user.Role;
import net.ziemers.swxercise.lg.model.user.User;
import net.ziemers.swxercise.lg.user.dto.RoleDto;
import net.ziemers.swxercise.lg.user.enums.RightState;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;

/**
 * Diese Klasse stellt alle Dienste im Kontext einer Rollen- und Rechteverwaltung zur Verfügung.
 */
@Stateless
public class RoleService {

    @Inject
    private RoleDao dao;

    @Inject
    private SessionContext sessionContext;

    /**
     * Findet alle existierenden Rollen.
     *
     * @return alle Rollen, oder eine leere Collection, falls keine existieren.
     */
    public Collection<Role> findAllRoles() {
        return dao.findAll(Role.class);
    }

    /**
     * Findet die Rolle mit der übergebenen Id.
     *
     * @param id die Id der gesuchten Rolle.
     * @return die gesuchte Rolle, oder <code>null</code>, falls eine solche nicht existiert.
     */
    public Role findRole(final Long id) {
        return dao.findById(id);
    }

    /**
     * Findet die Rolle des zurzeit angemeldeten Benutzers.
     *
     * @return die gesuchte Rolle, oder <code>null</code>, falls eine solche nicht existiert.
     */
    public Role findRole() {
        final User user = sessionContext.getUser();
        if (user != null && user.getProfile() != null) {
            return user.getProfile().getRole();
        }
        return null;
    }

    /**
     * Erstellt eine neue Rolle, sofern noch keine mit dem selben Namen existiert.
     * Zwischen der Groß- und Kleinschreibung wird nicht unterschieden.
     *
     * @param dto das {@link RoleDto} enthält die Eigenschaften der zu erstellenden Rolle
     * @return die Id der neuen Rolle, wenn die Erstellung erfolgreich war.
     */
    public Long createRole(final RoleDto dto) {
        Role role = dao.findByName(dto.getName());
        if (role == null) {
            role = new Role(dto.getName(), dto.getRight());

            return dao.save(role);
        }
        return null;
    }

    /**
     * Aktualisiert die Rolle mit der übergebenen Id.
     *
     * @param id  die Id der zu aktualisierenden Rolle
     * @param dto das {@link RoleDto} enthält die Eigenschaften der zu aktualisierenden Rolle
     * @return <code>true</code>, wenn das Aktualisieren der Rolle erfolgreich war.
     */
    public boolean updateRole(final Long id, final RoleDto dto) {
        final Role role = dao.findById(id);
        if (role != null) {
            // TODO noch zu implementieren
            return false;
        }
        return false;
    }

    /**
     * Aktualisiert die Rolle des zurzeit angemeldeten Benutzers.
     *
     * @param dto das {@link RoleDto} enthält die Eigenschaften der zu aktualisierenden Rolle
     * @return <code>true</code>, wenn das Aktualisieren der Rolle erfolgreich war.
     */
    public boolean updateRole(final RoleDto dto) {
        // ist zurzeit ein Benutzer angemeldet, können wir dessen Rolle aktualisieren
        final User user = sessionContext.getUser();
        if (user != null) {
            // TODO noch zu implementieren
            return false;
        }
        return false;
    }

    /**
     * Löscht die Rolle mit der übergebenen Id.
     *
     * @param id die Id der zu löschenden Rolle.
     */
    public Role deleteRole(final Long id) {
        return dao.remove(Role.class, id);
    }

    /**
     * Löscht die Rolle des zurzeit angemeldeten Benutzers.
     *
     * @return <code>true</code>, wenn das Löschen der Rolle des angemeldeten Benutzers erfolgreich war.
     */
    public boolean deleteRole() {
        // ist zurzeit ein Benutzer angemeldet, können wir dessen Rolle löschen
        final User user = sessionContext.getUser();
        if (user != null && user.getProfile() != null) {
            final Role role = user.getProfile().getRole();
            if (role != null) {
                user.getProfile().setRole(null);
                dao.save(user);
                dao.remove(Role.class, role.getId());
                return true;
            }
        }
        return false;
    }

    /**
     * Verknüpft zwei Rollen in einer Vater-/Kindbeziehung.
     *
     * @param childName der Name der gewünschten Kindrolle
     * @param parentName der Name der gewünschten Vaterrolle
     * @return <code>true</code>, wenn die Verknüpfung erfolgreich durchgeführt werden konnte.
     */
    public boolean linkRoles(final String childName, final String parentName) {
        final Role childRole = dao.findByName(childName);
        if (childRole != null) {
            final Role parentRole = dao.findByName(parentName);
            if (parentRole != null) {
                childRole.setParent(parentRole);
                return dao.saveOrUpdate(childRole) != null;
            }
        }
        return false;
    }

    /**
     * Fügt einer Rolle ein neues Recht hinzu.
     *
     * @param id die Id der gewünschten Rolle
     * @param rightName der Name des gewünschten Rechts
     * @return <code>true</code>, wenn das Hinzufügen erfolgreich durchgeführt werden konnte.
     */
    public boolean addRight(final Long id, final String rightName) {
        final Role role = dao.findById(id);
        if (role != null) {
            final RightState right = RightState.getByName(rightName);
            if (right != null) {
                return role.addRight(right);
            }
        }
        return false;
    }

}
