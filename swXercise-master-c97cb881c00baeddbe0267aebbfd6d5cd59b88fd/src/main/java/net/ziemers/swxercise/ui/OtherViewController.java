package net.ziemers.swxercise.ui;

import javax.faces.bean.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST-Methoden, die nirgendwo besser hinpassen.
 */
@ApplicationScoped
@Path(UserViewController.webContextPath)
public class OtherViewController {

    static final String webContextPath = "/";

    @GET
    @Path("/v1/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        return "pong";
    }

}
