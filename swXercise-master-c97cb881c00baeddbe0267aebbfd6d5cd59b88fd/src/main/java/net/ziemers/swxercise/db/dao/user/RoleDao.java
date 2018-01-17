package net.ziemers.swxercise.db.dao.user;

import net.ziemers.swxercise.db.dao.GenericDao;
import net.ziemers.swxercise.lg.model.user.Role;

import javax.ejb.Stateless;

/**
 * Stellt Persistenz-Funktionalität im Kontext der Rollen- und Rechteverwaltung
 * zur Verfügung.
 */
@Stateless
public class RoleDao extends GenericDao {

    /**
     * Findet eine {@link Role} aufgrund ihrer Id.
     *
     * @param id die Id der gewünschten Rolle
     * @return die Rolle oder <code>null</code>, falls es keine gibt.
     */
    public Role findById(final Long id) {
        Role role = null;

        try {
            // ermittelt den ersten Datensatz mit der gesuchten Id, auch wenn
            // er sich nicht im Persistence Context befindet
            role = (Role) entityManager.createNamedQuery("Role.findById")
                    .setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            /* nix */
        }
        return role;
    }

    /**
     * Findet eine {@link Role} aufgrund ihres Rollennamens.
     *
     * @param name der name der gewünschten Rolle
     * @return den User oder null, falls es keinen gibt.
     */
    public Role findByName(final String name) {
        Role role = null;

        try {
            // ermittelt den ersten Datensatz mit dem gesuchten Rollennamen,
            // auch wenn er sich nicht im Persistence Context befindet
            role = (Role) entityManager.createNamedQuery("Role.findByName")
                    .setParameter("name", name).getSingleResult();
        } catch (Exception e) {
            /* nix */
        }
        return role;
    }

}
