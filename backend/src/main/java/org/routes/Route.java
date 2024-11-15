package org.routes;

import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;
import org.daos.FileDAO;
import org.daos.LogDAO;
import org.persistence.HibernateConfig;

import static io.javalin.apibuilder.ApiBuilder.path;


public class Route {
    private EntityManagerFactory emf;
    private FileDAO fileDAO;
    private RouteFile routeFile;

    private LogDAO logDAO;
    private RouteLogEntries routeLogEntries;

    public Route(EntityManagerFactory _emf) {
        this.emf = _emf;
        fileDAO = FileDAO.getInstance(emf);

        routeFile = new RouteFile(fileDAO);
        logDAO = new LogDAO(emf);
        routeLogEntries = new RouteLogEntries(logDAO);

    }


    public EndpointGroup addRoutes() {
        return combineRoutes(routeFile.getRoutes(), routeLogEntries.getRoutes());
    }

    private EndpointGroup combineRoutes(EndpointGroup... endpointGroups) {

        return () -> {
            for (EndpointGroup group : endpointGroups) {
                path("/", group);
            }
        };
    }

}