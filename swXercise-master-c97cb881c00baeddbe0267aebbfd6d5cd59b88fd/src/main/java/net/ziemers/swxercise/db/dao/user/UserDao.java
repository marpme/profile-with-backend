package net.ziemers.swxercise.db.dao.user;

import net.ziemers.swxercise.db.dao.GenericDao;
import net.ziemers.swxercise.lg.model.user.User;

import javax.ejb.Stateless;

/**
 * Stellt Persistenz-Funktionalität im Kontext der Benutzerverwaltung
 * zur Verfügung.
 */
@Stateless
public class UserDao extends GenericDao {

    /**
     * Findet einen {@link User} aufgrund seiner Id.
     *
     * @param id die Id des gewünschten Users
     * @return den User oder <code>null</code>, falls es keinen gibt.
     */
    public User findById(final Long id) {
        User user = null;

        try {
            // ermittelt den ersten Datensatz mit der gesuchten Id, auch wenn
            // er sich nicht im Persistence Context befindet
            user = (User) entityManager.createNamedQuery("User.findById")
                    .setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            /* nix */
        }
        return user;
    }

    /**
     * Findet einen {@link User} aufgrund seines Benutzernamens.
     *
     * @param username der username des gewünschten Benutzers
     * @return den User oder <code>null</code>, falls es keinen gibt.
     */
    public User findByUsername(final String username) {
        User user = null;

        try {
            // ermittelt den ersten Datensatz mit dem gesuchten Benutzernamen,
            // auch wenn er sich nicht im Persistence Context befindet
            user = (User) entityManager.createNamedQuery("User.findByUsername")
                    .setParameter("username", username).getSingleResult();
        } catch (Exception e) {
            /* nix */
        }
        return user;
    }

}
