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
        // Apply authentication to all routes under /folder
        app.before("/folder/*", ctx -> securityController.authenticate(ctx));

        // Define routes under /folder
        app.routes(() -> {
            path("folder", () -> {
                // Apply authorization to ensure only CompanyManagers can access
                before(ctx -> securityController.authorizeRole(ctx, Role.COMPANY_MANAGER));

                // Get all folders in the manager's company
                get("companyfolders", ctx -> folderController.getFoldersByCompany(ctx));

                // Assign role to a folder
                post("assignrole", ctx -> folderController.assignRole(ctx));
            });
        });
    }
}
