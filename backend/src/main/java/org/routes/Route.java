package org.routes;

import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;
import org.daos.FileDAO;
import org.persistence.HibernateConfig;

public class Route {

    //TODO:
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(false);
    private static FileDAO fileDAO = FileDAO.getInstance(emf);

    private static RouteFile routeFile = new RouteFile(fileDAO);

    public static EndpointGroup addRoutes() {
        return combineRoutes(routeFile.getRoutes());
    }

    private static EndpointGroup combineRoutes(EndpointGroup... endpointGroups) {
        return () -> {
            for (EndpointGroup group : endpointGroups) {
                group.addEndpoints();
            }
        };
    }
}
