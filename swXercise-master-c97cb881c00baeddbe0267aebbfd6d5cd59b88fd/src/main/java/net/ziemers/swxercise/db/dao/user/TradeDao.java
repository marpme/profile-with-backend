package net.ziemers.swxercise.db.dao.user;

import net.ziemers.swxercise.db.dao.GenericDao;
import net.ziemers.swxercise.lg.model.user.Trade;
import net.ziemers.swxercise.lg.model.user.User;

import javax.ejb.Stateless;

/**
 * Stellt Persistenz-Funktionalität im Kontext der Benutzerverwaltung
 * zur Verfügung.
 */
@Stateless
public class TradeDao extends GenericDao {

    /**
     * Findet einen {@link User} aufgrund seiner Id.
     *
     * @param id die Id des gewünschten Users
     * @return den User oder <code>null</code>, falls es keinen gibt.
     */
    public Trade findById(final Long id) {
        Trade trade = null;

        try {
            // ermittelt den ersten Datensatz mit der gesuchten Id, auch wenn
            // er sich nicht im Persistence Context befindet
            trade = (Trade) entityManager.createNamedQuery("Trade.findById")
                    .setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            /* nix */
        }
        return trade;
    }

    /**
     * Findet einen {@link User} aufgrund seines Benutzernamens.
     *
     * @param tradetitle der username des gewünschten Benutzers
     * @return den User oder <code>null</code>, falls es keinen gibt.
     */
    public Trade findByTitle(final String tradetitle) {
        Trade trade = null;

        try {
            // ermittelt den ersten Datensatz mit dem gesuchten Benutzernamen,
            // auch wenn er sich nicht im Persistence Context befindet
            trade = (Trade) entityManager.createNamedQuery("Trade.findByTitle")
                    .setParameter("tradetitle", tradetitle).getSingleResult();
        } catch (Exception e) {
            /* nix */
        }
        return trade;
    }

}
