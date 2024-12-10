package org.routes;

import io.javalin.apibuilder.EndpointGroup;
import org.controllers.FileController;
import org.daos.FileDAO;
import org.daos.FolderDAO;
import org.daos.RoleDAO;
import org.persistence.model.Folder;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.io.File;

public class RouteFile {
    private FileDAO fileDAO;
    private RoleDAO roleDAO;
    private FolderDAO folderDAO;


    public RouteFile(FileDAO fileDAO,FolderDAO folderDAO, RoleDAO roleDAO ) {
        this.fileDAO = fileDAO;
    }

    public EndpointGroup getRoutes() {
        FileController fileController = new FileController(fileDAO, folderDAO, roleDAO);

        return () -> path("/files", () -> {
            get("/file/{id}", ctx -> fileController.getById().handle(ctx));

            get("/{folder_path}", ctx -> fileController.getAllFilesInPath().handle(ctx));

            get("/{folder_path}/{file_type}", ctx -> fileController.getAllByTypeInPath().handle(ctx));

            post("/", ctx -> fileController.create().handle(ctx));

            delete("/{id}", ctx -> fileController.delete(fileDAO).handle(ctx));

            put("/", ctx -> fileController.update().handle(ctx));

            get("/getpermissions/{folder_id}", ctx -> fileController.getFilePermissionsForUserInFolder().handle(ctx));
        });

    }
}