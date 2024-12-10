package org.routes;

import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;
import org.daos.FileDAO;
import org.daos.FolderDAO;
import org.daos.LogDAO;
import org.daos.RoleDAO;

import static io.javalin.apibuilder.ApiBuilder.path;


public class Route {
    private EntityManagerFactory emf;
    private FileDAO fileDAO;
    private FolderDAO folderDAO;
    private RoleDAO roleDAO;
    private RouteFile routeFile;

    private LogDAO logDAO;
    private RouteLogEntries routeLogEntries;

    public Route(EntityManagerFactory _emf) {
        this.emf = _emf;
        fileDAO = new FileDAO(emf);
        folderDAO = new FolderDAO(emf);
        roleDAO = new RoleDAO();

        routeFile = new RouteFile(fileDAO, folderDAO, roleDAO);
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