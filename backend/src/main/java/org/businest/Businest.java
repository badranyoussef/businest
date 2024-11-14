package org.businest;

import jakarta.persistence.EntityManagerFactory;
import org.daos.FileDAO;
import org.persistence.HibernateConfig;
import org.rest.ApplicationConfig;
import org.routes.Route;
import org.routes.RouteFile;


public class Businest {

    private static HibernateConfig hibernateConfig = new HibernateConfig();
    public static void main(String[] args) {

        Route route = new Route(HibernateConfig.getEntityManagerFactoryConfig(false));

        ApplicationConfig app = ApplicationConfig.getInstance();

        app.initiateServer()
                .startServer(7007)
                .setExceptionHandlers()
                .setRoute(route.addRoutes());

    }
}