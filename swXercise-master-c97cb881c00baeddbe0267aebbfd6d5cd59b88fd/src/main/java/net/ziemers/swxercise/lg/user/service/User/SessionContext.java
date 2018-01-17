package net.ziemers.swxercise.lg.user.service.User;

import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;

import net.ziemers.swxercise.lg.model.user.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Verwaltet den SessionContext des zurzeit angemeldeten Benutzers.
 */
@SessionScoped
public class SessionContext implements java.io.Serializable {

	private static final long serialVersionUID = 8624253586553865146L;

    private static Map<String, SessionContext> sessions = Collections.synchronizedMap(new HashMap<>());

	private User user = null;

	private String sessionId = "";

	@PreDestroy
    private void deinit() {
	    sessions.remove(getSessionId());
    }

    public User getUser() {
        return user;
    }

    private void setUser(User user) {
        this.user = user;
    }

    private String getSessionId() { return sessionId; }

    private void setSessionId(String sessionId) { this.sessionId = sessionId; }

    /**
     * Liefert den Session-Kontext zur übergebenen REST-Session-Id.
     *
     * @param restSessionId die REST-Session-Id, deren Session-Kontext ermittelt werden soll
     * @return das {@link SessionContext}-Objekt des Benutzers mit der REST-Session-Id oder <code>null</code>.
     */
    public static SessionContext getInstanceByRestSessionId(final String restSessionId) {
	    return sessions.get(restSessionId);
    }

    /**
     * Meldet einen Benutzer in diesem Session-Kontext an.
     *
     * @param user das {@link User}-Objekt des Benutzers dieses Session-Kontexts
     * @param sessionId die Session-Id dieser Benutzer-Session
     * @return <code>true</code>, wenn der Benutzer erfolgreich am Session-Kontext angemeldet werden konnte.
     */
    public boolean login(final User user, final String sessionId) {
        if (getUser() == null) {
            setUser(user);
            setSessionId(sessionId);

            // wir können später von der Session-Id auf den Session-Kontext schließen
            sessions.putIfAbsent(getSessionId(), this);

            return true;
        }
        return false;
    }

    /**
     * Meldet einen Benutzer von diesem Session-Kontext ab.
     *
     * @return <code>true</code>, wenn die Abmeldung erfolgreich durchgeführt werden konnte.
     */
    public boolean logout() {
        if (getUser() != null) {
            setUser(null);

            // ohne authentifizierten Benutzer interessiert uns dieser Session-Kontext nicht mehr
            sessions.remove(getSessionId());

            return true;
        }
        return false;
    }

}
