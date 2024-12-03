package org.folder;

import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class RouteFolderFile {

    private final FileDao fileDAO;
    private final FolderDAO folderDAO;

    public RouteFolderFile(EntityManagerFactory emf) {
        this.fileDAO = new FileDao(emf);
        this.folderDAO = new FolderDAO(emf);
    }

    public EndpointGroup configureRoutes() {
        return () -> {
            path("/folders", () -> {
                post("/{folder_id}/files", ctx -> FolderFileController.addFileToFolder(fileDAO).handle(ctx));
                get("/alle", ctx -> FolderFileController.getAllFolders(folderDAO).handle(ctx));
                get("/{folder_id}/files", ctx -> FolderFileController.getAllFilesByFolderId(fileDAO, folderDAO).handle(ctx));
                post("/", ctx -> FolderFileController.createFolder(folderDAO).handle(ctx));
                get("/{folder_id}", ctx -> FolderFileController.getFolderById(folderDAO).handle(ctx));
            });
        };
    }
}
