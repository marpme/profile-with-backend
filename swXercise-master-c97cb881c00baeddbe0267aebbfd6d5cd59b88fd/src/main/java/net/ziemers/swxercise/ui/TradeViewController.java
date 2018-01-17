package net.ziemers.swxercise.ui;

import net.ziemers.swxercise.lg.model.user.Trade;
import net.ziemers.swxercise.lg.model.user.User;
import net.ziemers.swxercise.lg.user.dto.TradeDto;
import net.ziemers.swxercise.lg.user.dto.UserDto;
import net.ziemers.swxercise.lg.user.enums.RightState;
import net.ziemers.swxercise.lg.user.service.TradeService;
import net.ziemers.swxercise.lg.user.service.User.SessionContext;
import net.ziemers.swxercise.ui.enums.ResponseState;
import net.ziemers.swxercise.ui.utils.RestResponse;
import org.slf4j.Logger;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * REST-Methoden für die Benutzerverwaltung.
 */
@ApplicationScoped
@Path(TradeViewController.webContextPath)
public class TradeViewController {

    static final String webContextPath = "/";

    /*
     * SLF4J: "Simple Logging Facade for Java"
     * Dependency Injection erfolgt mit dem {@link net.ziemers.swxercise.lg.utils.LoggerProducer}
     */
    @Inject
    private Logger logger;

    @Inject
    private TradeService tradeService;

    /**
     * Liefert alle User-Objekte zurück.
     * <p>
     * Aufruf:
     * GET http://localhost:8080/swxercise/rest/v1/users
     *
     * @return die User-Objekte, oder ein leeres JSON-Array, falls keine existieren.
     */
    @GET
    @Path("v1/trades")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Trade> getAllTrades() {
        return tradeService.findAllTrade();
    }

    /**
     * Liefert das User-Objekt mit der gewünschten Id zurück.
     * <p>
     * Aufruf:
     * GET http://localhost:8080/swxercise/rest/v1/user/42
     *
     * @param id die Id des gewünschten User-Objekts
     * @return das User-Objekt als JSON, oder <code>null</code>, falls keines existiert.
     */
    @GET
    @Path("v1/trade/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Trade getTrade(@PathParam("id") Long id) {
        return tradeService.findTrade(id);
    }

    /**
     * Erstellt ein neues User-Objekt mit den gewünschten Eigenschaften, welche mittels {@link UserDto} definiert werden.
     * <p>
     * Aufruf:
     * POST http://localhost:8080/swxercise/rest/v1/user
     *
     * @param dto das mittels der als JSON-Objekt übergebenenen Eigenschaften zu füllende {@link UserDto}
     * @return ein {@link ResponseState}-Objekt mit den Ergebnisinformationen des Aufrufs.
     */
    @POST
    @Path("v1/trade")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(RightState.Constants.LOGGED_IN)
    public RestResponse createUser(TradeDto dto) {
        if (tradeService.createTrade(dto)) {
            return new RestResponse();
        }
        return new RestResponse(ResponseState.ALREADY_EXISTING);
    }

}
