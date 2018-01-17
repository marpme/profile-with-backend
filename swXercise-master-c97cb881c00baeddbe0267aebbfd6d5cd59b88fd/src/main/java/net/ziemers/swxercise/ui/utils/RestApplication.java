package net.ziemers.swxercise.ui.utils;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Basisklasse für alle REST-Methoden.
 */
@ApplicationPath(RestApplication.webContextPath)
public class RestApplication extends Application {

    static final String webContextPath = "/rest";

}
