package org.businest;

import org.persistence.HibernateConfig;
import org.rest.ApplicationConfig;
import org.routes.Route;

public class Businest {

    private static HibernateConfig hibernateConfig = new HibernateConfig();
    public static void main(String[] args) {


        ApplicationConfig app = ApplicationConfig.getInstance();
        app.initiateServer()
                .startServer(7007)
                .setExceptionHandlers()
                .checkSecurityRoles()
                .setRoute(Route.addRoutes());
    }
}
