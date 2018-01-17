package net.ziemers.swxercise.ui.utils;

import javax.annotation.PostConstruct;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.ziemers.swxercise.lg.user.service.User.UserService;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dies ist ein Interceptor für die Annotationen @PermitAll, @DenyAll und @RolesAllowed. Er stellt sicher,
 * dass die REST-Methoden, welche mit den genannten Annotationen versehen wurden, nur von authorisierten
 * Benutzern aufgerufen werden können.
 */
@Provider
public class SecurityInterceptor implements ContainerRequestFilter {

    private static final String PROPERTY_RMI = "org.jboss.resteasy.core.ResourceMethodInvoker";

    // 401: The request has not been applied because it lacks valid authentication credentials for the target resource.
    private static final ServerResponse ACCESS_DENIED = new ServerResponse("Access denied for this rest method", 401, new Headers<>());

    // 403: The server understood the request but refuses to authorize it.
    private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("Nobody can access this rest method", 403, new Headers<>());

    private Logger logger;

    @Inject
    private UserService userService;

    @PostConstruct
    public void initialize() {
        logger = LoggerFactory.getLogger(SecurityInterceptor.class);
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        final ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty(PROPERTY_RMI);
        final AnnotatedElement element = methodInvoker.getMethod();

        // Zugriff auf die Methode ist für alle erlaubt (obsolet)
        if (element.isAnnotationPresent(PermitAll.class)) {
            logger.debug("@PermitAll");
        }

        // Zugriff auf die Methode ist für niemanden erlaubt
        else if (element.isAnnotationPresent(DenyAll.class)) {
            requestContext.abortWith(ACCESS_FORBIDDEN);
            logger.debug("@DenyAll");
        }

        // Zugriff auf die Methode muss anhand ihrer Rollen geprüft werden
        else if (element.isAnnotationPresent(RolesAllowed.class)) {
            final RolesAllowed rolesAllowed = element.getAnnotation(RolesAllowed.class);
            final Set<String> rolesSet = new HashSet<>(Arrays.asList(rolesAllowed.value()));

            if (!userService.isUserAllowed(rolesSet)) {
                requestContext.abortWith(ACCESS_DENIED);
            }

            logger.debug("@RolesAllowed({})", rolesSet.toString());
        }
    }

}
