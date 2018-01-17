package net.ziemers.swxercise.ui;

import net.ziemers.swxercise.lg.model.user.Role;
import net.ziemers.swxercise.lg.user.dto.RoleDto;
import net.ziemers.swxercise.lg.user.enums.RightState;
import net.ziemers.swxercise.lg.user.service.User.RoleService;
import net.ziemers.swxercise.ui.enums.ResponseState;
import net.ziemers.swxercise.ui.utils.RestResponse;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * REST-Methoden für die Rollen- und Rechteverwaltung.
 */
@ApplicationScoped
@Path(RoleViewController.webContextPath)
public class RoleViewController {

    static final String webContextPath = "/";

    @Inject
    private RoleService roleService;

    /**
     * Liefert alle Role-Objekte zurück.
     * <p>
     * Aufruf:
     * GET http://localhost:8080/swxercise/rest/v1/roles
     *
     * @return die Role-Objekte, oder ein leeres JSON-Array, falls keine existieren.
     */
    @GET
    @Path("v1/roles")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(RightState.Constants.ADMIN)
    public Collection<Role> getAllRoles() {
        return roleService.findAllRoles();
    }

    /**
     * Liefert das Role-Objekt mit der gewünschten Id zurück.
     * <p>
     * Aufruf:
     * GET http://localhost:8080/swxercise/rest/v1/role/42
     *
     * @param id die Id des gewünschten Role-Objekts
     * @return das Role-Objekt als JSON, oder <code>null</code>, falls keines existiert.
     */
    @GET
    @Path("v1/role/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(RightState.Constants.ADMIN)
    public Role getRole(@PathParam("id") Long id) {
        return roleService.findRole(id);
    }

    /**
     * Liefert das Role-Objekt des zurzeit angemeldeten Benutzers zurück.
     * <p>
     * Aufruf:
     * GET http://localhost:8080/swxercise/rest/v1/role
     *
     * @return das Role-Objekt als JSON, oder <code>null</code>, falls keines existiert.
     */
    @GET
    @Path("v1/role")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(RightState.Constants.LOGGED_IN)
    public Role getRole() {
      return roleService.findRole();
    }

    /**
     * Erstellt ein neues Role-Objekt mit den gewünschten Eigenschaften, welche mittels {@link RoleDto} definiert werden.
     * <p>
     * Aufruf:
     * POST http://localhost:8080/swxercise/rest/v1/role
     *
     * @param dto das mittels der als JSON-Objekt übergebenenen Eigenschaften zu füllende {@link RoleDto}
     * @return ein {@link ResponseState}-Objekt mit den Ergebnisinformationen des Aufrufs.
     */
    @POST
    @Path("v1/role")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(RightState.Constants.ADMIN)
    public RestResponse createRole(RoleDto dto) {
        final Long id = roleService.createRole(dto);
        if (id != null) {
            return new RestResponse(ResponseState.SUCCESS, String.valueOf(id));
        }
        return new RestResponse(ResponseState.ALREADY_EXISTING);
    }

    /**
     * Aktualisiert das Role-Objekt mit der gewünschten Id mit den Eigenschaften,
     * welche mittels {@link RoleDto} definiert werden. Der Pfadparameter wird
     * als erstes ge'marshal't, das DTO im Post-Content danach (REST-Konvention).
     * <p>
     * Aufruf:
     * PUT http://localhost:8080/swxercise/rest/v1/role/42
     *
     * @param id  die Id des zu aktualisierenden Role-Objekts
     * @param dto das mittels der als JSON-Objekt übergebenenen Eigenschaften zu füllende {@link RoleDto}
     * @return ein {@link ResponseState}-Objekt mit den Ergebnisinformationen des Aufrufs.
     */
    @PUT
    @Path("v1/role/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(RightState.Constants.ADMIN)
    public RestResponse updateRole(@PathParam("id") Long id, RoleDto dto) {
        if (roleService.updateRole(id, dto)) {
            return new RestResponse();
        }
        return new RestResponse(ResponseState.FAILED);
    }

    /** Fügt der angegebenen Rolle das angegebene Recht hinzu.
     * <p>
     * Aufruf:
     * PUT http://localhost:8080/swxercise/rest/v1/role/add/42/admin
     *
     * @param id die Id der gewünschen Rolle
     * @param rightName der Name des gewünschten Rechts
     * @return ein {@link ResponseState}-Objekt mit den Ergebnisinformationen des Aufrufs.
     */
    @PUT
    @Path("v1/role/add/{id}/{rightname}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(RightState.Constants.ADMIN)
    public RestResponse addRight(@PathParam("id") Long id, @PathParam("rightname") String rightName) {
        if (roleService.addRight(id, rightName)) {
            return new RestResponse();
        }
        return new RestResponse(ResponseState.FAILED);
    }

    /**
     * Aktualisiert das Role-Objekt des zurzeit angemeldeten Benutzers mit den
     * Eigenschaften, welche mittels {@link RoleDto} definiert werden. Der
     * Pfadparameter wird als erstes ge'marshal't, das DTO im Post-Content
     * danach (REST-Konvention).
     * <p>
     * Aufruf:
     * PUT http://localhost:8080/swxercise/rest/v1/role
     *
     * @param dto das mittels der als JSON-Objekt übergebenenen Eigenschaften zu füllende {@link RoleDto}
     * @return ein {@link ResponseState}-Objekt mit den Ergebnisinformationen des Aufrufs.
     */
    @PUT
    @Path("v1/role")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(RightState.Constants.ADMIN)
    public RestResponse updateRole(RoleDto dto) {
        if (roleService.updateRole(dto)) {
            return new RestResponse();
        }
        return new RestResponse(ResponseState.FAILED);
    }

    /**
     * Verknüpft eine Rolle (die "Kindrolle") mit einer anderen Rolle (der "Vaterrolle").
     * <p>
     * Aufruf:
     * PUT http://localhost:8080/swxercise/rest/v1/role/childname/parentname
     *
     * @param childName die Kindrolle, welche mit der Vaterrolle verknüpft werden soll
     * @param parentName die Vaterrolle, welche mit der Kindrolle verknüpft werden soll.
     * @return ein {@link ResponseState}-Objekt mit den Ergebnisinformationen des Aufrufs.
     */
    @PUT
    @Path("v1/role/link/{childname}/{parentname}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(RightState.Constants.ADMIN)
    public RestResponse linkRoles(@PathParam("childname") String childName, @PathParam("parentname") String parentName) {
        if (roleService.linkRoles(childName, parentName)) {
            return new RestResponse();
        }
        return new RestResponse(ResponseState.FAILED);
    }

    /**
     * Löscht das Role-Objekt mit der gewünschten Id.
     * <p>
     * Aufruf:
     * DELETE http://localhost:8080/swxercise/rest/v1/role/42
     *
     * @param id die Id des gewünschten Role-Objekts
     * @return ein {@link ResponseState}-Objekt mit den Ergebnisinformationen des Aufrufs.
     */
    @DELETE
    @Path("v1/role/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(RightState.Constants.ADMIN)
    public RestResponse deletetRole(@PathParam("id") Long id) {
        roleService.deleteRole(id);
        return new RestResponse();
    }

    /**
     * Löscht das Role-Objekt des zurzeit angemeldeten Benutzers.
     * <p>
     * Aufruf:
     * DELETE http://localhost:8080/swxercise/rest/v1/role
     *
     * @return ein {@link ResponseState}-Objekt mit den Ergebnisinformationen des Aufrufs.
     */
    @DELETE
    @Path("v1/role")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(RightState.Constants.ADMIN)
    public RestResponse deleteRole() {
        if (roleService.deleteRole()) {
            return new RestResponse();
        }
        return new RestResponse(ResponseState.FAILED);
    }

}
