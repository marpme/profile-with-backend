package net.ziemers.swxercise.ui.ws;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;
import java.util.Collections;

/**
 * Ein Beispiel-JSON zur Übertragung durch den WebSocket.
 */
public class WebSocketJson {

    /**
     * Wandelt ein Objekt der Klasse in einen JSON-String um.
     */
    public static class MessageEncoder implements Encoder.Text<WebSocketJson> {

        @Override
        public void init(EndpointConfig config) {}

        @Override
        public String encode(WebSocketJson message) throws EncodeException {
            return Json.createObjectBuilder()
                    .add("message", message.getMessage())
                    .build()
                    .toString();
        }

        @Override
        public void destroy() {}

    }

    /**
     * Wandelt einen JSON-String in ein Objekt der Klasse um.
     */
    public static class MessageDecoder implements Decoder.Text<WebSocketJson> {

        private JsonReaderFactory factory = Json.createReaderFactory(Collections.emptyMap());

        @Override
        public void init(EndpointConfig config) {}

        @Override
        public WebSocketJson decode(String str) throws DecodeException {
            WebSocketJson message = new WebSocketJson();
            JsonReader reader = factory.createReader(new StringReader(str));
            JsonObject json = reader.readObject();

            message.setMessage(json.getString("message"));

            return message;
        }

        @Override
        public boolean willDecode(String str) {
            return true;
        }

        @Override
        public void destroy() {}

    }

    /*
     * Payload der JSON-Nachricht
     */
    private String message;

    private WebSocketJson() {}

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

}
