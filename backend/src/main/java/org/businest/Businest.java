package org.businest;

import org.rest.ApplicationConfig;
import org.routes.Route;

public class Businest {
    public static void main(String[] args) {
        ApplicationConfig app = ApplicationConfig.getInstance();
        app.initiateServer()
                .startServer(7007)
                .setExceptionHandlers()
                .checkSecurityRoles()
                .setRoute(Route.addRoutes());
    }
}
