package net.ziemers.swxercise.ui.ws;

import net.ziemers.swxercise.lg.model.user.User;
import net.ziemers.swxercise.lg.user.service.User.SessionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Stub für die WebSocket-Unterstützung.
 *
 * Aufgepasst: Mittels CDI kann nur @ApplicationScoped injiziert werden,
 * da während eines WebSocket-Callbacks kein Session-Kontext aktiv ist.
 */
@ServerEndpoint(value = WebSocketController.serverEndpointPath,
        encoders = { WebSocketJson.MessageEncoder.class },
        decoders = { WebSocketJson.MessageDecoder.class } )
public class WebSocketController {

    static final String serverEndpointPath = "/ws/api/v1/anEndpoint/{restSessionid}";

    private static Map<Session, String> peers = Collections.synchronizedMap(new HashMap<>());

    private Logger logger;

    @PostConstruct
    private void init() {
        logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    }

    /**
     * Callback-Methode für das Öffnen einer neuen WebSocket-Verbindung. <code>restSessionId</code>
     * wird benötigt, um die WebSocket-Verbindung mit einer REST-Authentifizierung in Bezug bringen
     * zu können.
     *
     * @param wsSession das {@link Session}-Objekt der neuen WebSocket-Verbindung
     * @param restSessionId die Session-Id einer vorangegangenen REST-Authentifizierung
     */
    @OnOpen
    public void onOpen(Session wsSession, @PathParam("restSessionid") String restSessionId) {
        logger.info("WebSocket {} opened with session id #{}", restSessionId, wsSession.getId());

        // wir können später über die gegebene WebSocket-Session die REST-Session-Id dieses WebSockets ermitteln
        peers.putIfAbsent(wsSession, restSessionId);

        getUserBySession(wsSession);
    }

    /**
     * Callback-Methode, die den Empfang einer neuen WebSocket-Nachricht signalisiert.
     *
     * @param json der JSON-strukturierte Inhalt der WebSocket-Nachricht
     * @param wsSession das {@link Session}-Objekt der sendenden WebSocket-Verbindung
     */
    @OnMessage
    public void onMessage(WebSocketJson json, Session wsSession) throws IOException, EncodeException {
        logger.info("WebSocket Message '{}' received by session id #{}", json.getMessage(), wsSession.getId());

        getUserBySession(wsSession);

        try {
            // Wir senden die empfangene Nachricht gleich wieder zurück. Das JSON-Marshalling geschieht automatisch.
            wsSession.getBasicRemote().sendObject(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Callback-Methode, wenn in der WebSocket ein Problem auftrat.
     *
     * @param t die Exception
     */
    @OnError
    public void onError(Throwable t) {
        logger.error("WebSocket Error '{}' occured!", t.getMessage());
    }

    /**
     * Callback-Methode für das Schließen einer geöffneten WebSocket-Verbindung.
     *
     * @param reason die Ursache für das Schließen der WebSocket-Verbindung
     * @param wsSession das {@link Session}-Objekt der geschlossenen WebSocket-Verbindung
     */
    @OnClose
    public void onClose(CloseReason reason, Session wsSession) {
        logger.info("Closing WebSocket due to '{}' by session id #{}", reason.getReasonPhrase(), wsSession.getId());
        peers.remove(wsSession);
    }

    private User getUserBySession(final Session wsSession) {
        // die Map liefert uns zur WebSocket-Session gegebenenfalls die REST-Session-Id zurück;
        // und mit dieser schließen wir auf den authentifizierten REST-Benutzer
        String restSessionId = WebSocketController.peers.get(wsSession);
        SessionContext ctx = SessionContext.getInstanceByRestSessionId(restSessionId);

        if (ctx != null) {
            User user = ctx.getUser();

            logger.info("Detected WebSocket User '{}'", user.getFullName());

            return user;
        }
        return null;
    }

}
