package org.routes;

import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;
import org.daos.FileDAO;
import org.persistence.HibernateConfig;

import static io.javalin.apibuilder.ApiBuilder.path;


public class Route {
    private EntityManagerFactory emf;
    private FileDAO fileDAO;
    private RouteFile routeFile;

    public Route(EntityManagerFactory emf) {
        this.emf = emf;
        fileDAO = FileDAO.getInstance(emf);
        routeFile = new RouteFile(fileDAO);
    }


    public EndpointGroup addRoutes() {
        return combineRoutes(routeFile.getRoutes());
    }

    private EndpointGroup combineRoutes(EndpointGroup... endpointGroups) {

        return () -> {
            for (EndpointGroup group : endpointGroups) {
                path("/", group);
            }
        };
    }

}