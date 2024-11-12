package org.routes;

import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;
import org.daos.FileDAO;
import org.persistence.HibernateConfig;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Route {
    private static EntityManagerFactory emf;
    private static FileDAO fileDAO;
    private static RouteFile routeFile;

    public Route(EntityManagerFactory emf) {
        this.emf = emf;
        fileDAO = FileDAO.getInstance(emf);
        routeFile = new RouteFile(fileDAO);
    }

    public static EndpointGroup addRoutes() {
        return combineRoutes(routeFile.getRoutes());
    }

    private static EndpointGroup combineRoutes(EndpointGroup... endpointGroups) {
        return () -> {
            for (EndpointGroup group : endpointGroups) {
                path("/", group);
            }
        };
    }
}