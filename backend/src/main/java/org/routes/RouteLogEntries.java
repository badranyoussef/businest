package org.routes;

import io.javalin.apibuilder.EndpointGroup;
import org.controllers.FileController;
import org.controllers.NotificationController;
import org.daos.FileDAO;
import org.daos.LogDAO;

import static io.javalin.apibuilder.ApiBuilder.*;


public class RouteLogEntries {
    private LogDAO logDAO;

    public RouteLogEntries(LogDAO _logDAO) {
        this.logDAO = _logDAO;
    }

    public EndpointGroup getRoutes() {

        return () -> path("/logs", () -> {
            get("/all", ctx -> NotificationController.getAllLogEntries(logDAO).handle(ctx));
        });
    }
}