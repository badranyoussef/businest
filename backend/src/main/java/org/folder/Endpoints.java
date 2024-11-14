package org.folder;

import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;

public class Endpoints {

    private ISecurityController securityController;
    private FolderController folderController;

    public Endpoints(ISecurityController securityController, FolderController folderController) {
        this.securityController = securityController;
        this.folderController = folderController;
    }

    public void registerRoutes(Javalin app) {
        // Apply authentication to all routes under /folders
        app.before("/folders/*", ctx -> securityController.authenticate(ctx));

        // Define routes under /folders
        app.routes(() -> {
            path("folders", () -> {
                // Apply authorization to ensure only CompanyManagers can access
                before(ctx -> securityController.authorizeRole(ctx, Role.COMPANY_MANAGER));

                // Get all folders for a specific company
                get("/{companyName}", ctx -> folderController.getFoldersByCompany(ctx));

                // Assign role to a folder
                post("/{folderId}/role", ctx -> folderController.assignRole(ctx));
            });
        });
    }
}
