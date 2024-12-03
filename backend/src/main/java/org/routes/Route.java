package org.routes;

import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;
import org.daos.FileDAO;
import org.daos.LogDAO;
import org.folder.FileDao;
import org.folder.RouteFolderFile;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Route {
    private final EntityManagerFactory emf;
    private final FileDAO fileDAO;
    private final FileDao fileDao;
    private final LogDAO logDAO;
    private final RouteFile routeFile;
    private final RouteLogEntries routeLogEntries;
    private final RouteFolderFile routeFolderFile;

    public Route(EntityManagerFactory _emf) {
        this.emf = _emf;

        // Initialize DAOs
        this.fileDAO = FileDAO.getInstance(emf);
        this.fileDao = new  FileDao(emf);
        this.logDAO = new LogDAO(emf);

        // Initialize Routes
        this.routeFile = new RouteFile(fileDAO);
        this.routeLogEntries = new RouteLogEntries(logDAO);
        this.routeFolderFile = new RouteFolderFile(emf); // Pass EntityManagerFactory to RouteFolderFile
    }

    public EndpointGroup addRoutes() {
        return combineRoutes(
                routeFile.getRoutes(),
                routeLogEntries.getRoutes(),
                routeFolderFile.configureRoutes() // Call configureRoutes instead of getRoutes
        );
    }

    private EndpointGroup combineRoutes(EndpointGroup... endpointGroups) {
        return () -> {
            for (EndpointGroup group : endpointGroups) {
                path("/", group);
            }
        };
    }
}
