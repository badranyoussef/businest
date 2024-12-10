package org.businest;

import org.rest.ApplicationConfig;
import org.routes.RouteAccount;


public class businest {


    public static void main(String[] args) {

        //EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(false);
        RouteAccount route = new RouteAccount();

        ApplicationConfig app = ApplicationConfig.getInstance();

        app.initiateServer()
                .startServer(7007)
                .setExceptionHandlers()
                .setRoute(route.routesUser());
    }
}