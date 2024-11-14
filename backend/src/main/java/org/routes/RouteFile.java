package org.routes;

import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;
import org.controllers.FileController;
import org.daos.FileDAO;

import static io.javalin.apibuilder.ApiBuilder.*;

public class RouteFile {
    private FileDAO fileDAO;
    private FileController fileController;

    public RouteFile(EntityManagerFactory emf) {
        this.fileController = new FileController(emf);
    }


    public EndpointGroup getRoutes() {
        return () -> {
            path("files", () -> {
                //get("/{folder_path}/{file_type}", ctx -> FileController.getAllByTypeInPath(fileDAO).handle(ctx));

                //get("/{id}", ctx -> FileController.getById(fileDAO).handle(ctx));

                post("/create", fileController.create());

                //delete("/{id}", ctx -> FileController.delete(fileDAO).handle(ctx));

                //put("/{id}", ctx -> FileController.update(fileDAO).handle(ctx));

                //get("/{path}", ctx -> FileController.getAllFilesInPath(fileDAO).handle(ctx));
            });
        };
    }
}