package net.ziemers.swxercise.ui;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.ziemers.swxercise.lg.user.enums.RightState;
import net.ziemers.swxercise.lg.user.service.User.SessionContext;
import net.ziemers.swxercise.lg.model.user.User;
import net.ziemers.swxercise.lg.user.dto.UserDto;
import net.ziemers.swxercise.lg.user.service.User.UserService;
import net.ziemers.swxercise.ui.enums.ResponseState;
import net.ziemers.swxercise.ui.utils.RestResponse;
import org.slf4j.Logger;

/**
 * REST-Methoden für die Benutzerverwaltung.
 */
@ApplicationScoped
@Path(UserViewController.webContextPath)
public class UserViewController {

    static final String webContextPath = "/";

    /*
     * SLF4J: "Simple Logging Facade for Java"
     * Dependency Injection erfolgt mit dem {@link net.ziemers.swxercise.lg.utils.LoggerProducer}
     */
    @Inject
    private Logger logger;

    @Inject
    private UserService userService;

    /**
     * Liefert alle User-Objekte zurück.
     * <p>
     * Aufruf:
     * GET http://localhost:8080/swxercise/rest/v1/users
     *
     * @return die User-Objekte, oder ein leeres JSON-Array, falls keine existieren.
     */
    @GET
    @Path("v1/users")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(RightState.Constants.ADMIN)
    public Collection<User> getAllUsers() {
        return userService.findAllUsers();
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
    @Path("v1/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(RightState.Constants.ADMIN)
    public User getUser(@PathParam("id") Long id) {
        return userService.findUser(id);
    }

    /**
     * Liefert das User-Objekt des zurzeit angemeldeten Benutzers zurück.
     * <p>
     * Aufruf:
     * GET http://localhost:8080/swxercise/rest/v1/user
     *
     * @return das User-Objekt als JSON, oder <code>null</code>, falls keines existiert.
     */
    @GET
    @Path("v1/user")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(RightState.Constants.LOGGED_IN)
    public User getUser() {
        logger.info("Trying to get user's profile info.");

        return userService.findUser();
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
    @Path("v1/user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(RightState.Constants.ADMIN)
    public RestResponse createUser(UserDto dto) {
        if (userService.createUser(dto)) {
            return new RestResponse();
        }
        return new RestResponse(ResponseState.ALREADY_EXISTING);
    }

    /**
     * Aktualisiert das User-Objekt mit der gewünschten Id mit den Eigenschaften,
     * welche mittels {@link UserDto} definiert werden. Der Pfadparameter wird
     * als erstes ge'marshal't, das DTO im Post-Content danach (REST-Konvention).
     * <p>
     * Aufruf:
     * PUT http://localhost:8080/swxercise/rest/v1/user/42
     *
     * @param id  die Id des zu aktualisierenden User-Objekts
     * @param dto das mittels der als JSON-Objekt übergebenenen Eigenschaften zu füllende {@link UserDto}
     * @return ein {@link ResponseState}-Objekt mit den Ergebnisinformationen des Aufrufs.
     */
    @PUT
    @Path("v1/user/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(RightState.Constants.ADMIN)
    public RestResponse updateUser(@PathParam("id") Long id, UserDto dto) {
        if (userService.updateUser(id, dto)) {
            return new RestResponse();
        }
        return new RestResponse(ResponseState.FAILED);
    }

    /**
     * Aktualisiert das User-Objekt des zurzeit angemeldeten Benutzers mit den
     * Eigenschaften, welche mittels {@link UserDto} definiert werden. Der
     * Pfadparameter wird als erstes ge'marshal't, das DTO im Post-Content
     * danach (REST-Konvention).
     * <p>
     * Aufruf:
     * PUT http://localhost:8080/swxercise/rest/v1/user
     *
     * @param dto das mittels der als JSON-Objekt übergebenenen Eigenschaften zu füllende {@link UserDto}
     * @return ein {@link ResponseState}-Objekt mit den Ergebnisinformationen des Aufrufs.
     */
    @PUT
    @Path("v1/user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(RightState.Constants.LOGGED_IN)
    public RestResponse updateUser(UserDto dto) {
        if (userService.updateUser(dto)) {
            return new RestResponse();
        }
        return new RestResponse(ResponseState.FAILED);
    }

    /**
     * Löscht das User-Objekt mit der gewünschten Id.
     * <p>
     * Aufruf:
     * DELETE http://localhost:8080/swxercise/rest/v1/user/42
     *
     * @param id die Id des gewünschten User-Objekts
     * @return ein {@link ResponseState}-Objekt mit den Ergebnisinformationen des Aufrufs.
     */
    @DELETE
    @Path("v1/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(RightState.Constants.ADMIN)
    public RestResponse deleteUser(@PathParam("id") Long id) {
        if (userService.deleteUser(id) != null) {
            return new RestResponse();
        }
        return new RestResponse(ResponseState.FAILED);
    }

    /**
     * Löscht das User-Objekt des zurzeit angemeldeten Benutzers.
     * <p>
     * Aufruf:
     * DELETE http://localhost:8080/swxercise/rest/v1/user
     *
     * @return ein {@link ResponseState}-Objekt mit den Ergebnisinformationen des Aufrufs.
     */
    @DELETE
    @Path("v1/user")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(RightState.Constants.LOGGED_IN)
    public RestResponse deleteUser() {
        if (userService.deleteUser()) {
            return new RestResponse();
        }
        return new RestResponse(ResponseState.FAILED);
    }

    /**
     * Meldet einen Benutzer durch übergebenen username und password mit einem neuen User-{@link SessionContext} an.
     * <p>
     * Aufruf:
     * POST http://localhost:8080/swxercise/rest/v1/user/login
     *
     * @param request das {@link HttpServletRequest}-Objekt des augenblicklichen HTTP-Requests
     * @param dto das mittels der als JSON-Objekt übergebenenen Eigenschaften zu füllende {@link UserDto}
     * @return ein {@link ResponseState}-Objekt mit den Ergebnisinformationen des Aufrufs.
     */
    @POST
    @Path("v1/user/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(RightState.Constants.NOT_LOGGED_IN)
    public RestResponse loginUser(@Context HttpServletRequest request, UserDto dto) {
        // wir benötigen die Session-Id später, um REST-Zugriffe mit WebSockets in Bezug zu bringen
        String sessionId = request.getSession(false).getId();

        logger.info("Trying to log-in user '{}' with session id #{}.", dto.getUsername(), sessionId);

        if (userService.loginUser(dto, sessionId)) {
            return new RestResponse(sessionId);
        }
        return new RestResponse(ResponseState.FAILED);
    }

    /**
     * Meldet den angemeldeten Benutzer von seinem User-{@link SessionContext} ab.
     * <p>
     * Aufruf:
     * POST http://localhost:8080/swxercise/rest/v1/user/logout
     *
     * @return ein {@link ResponseState}-Objekt mit den Ergebnisinformationen des Aufrufs.
     */
    @POST
    @Path("v1/user/logout")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(RightState.Constants.LOGGED_IN)
    public RestResponse logoutUser() {
        logger.info("Trying to log-out user.");

        if (userService.logoutUser()) {
            return new RestResponse();
        }
        return new RestResponse(ResponseState.FAILED);
    }

}
