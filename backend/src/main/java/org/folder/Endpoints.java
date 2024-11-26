package org.folder;

import io.javalin.Javalin;
import org.controllers.CompanyController;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Endpoints {

    private ISecurityController securityController;
    private FolderController folderController;
    private CompanyController companyController;

    public Endpoints(ISecurityController securityController, FolderController folderController, CompanyController companyController) {
        this.securityController = securityController;
        this.folderController = folderController;
        this.companyController = companyController;
    }

    public void registerRoutes(Javalin app) {
        // Apply authentication to all routes
        app.before("/*", ctx -> securityController.authenticate(ctx));

        // Define routes
        app.routes(() -> {
            path("folders", () -> {
                // Authorization for folder routes
                before(ctx -> securityController.authorizeRole(ctx, Role.COMPANY_MANAGER));

                // Get all folders for a specific company
                get("/{companyName}", ctx -> folderController.getFoldersByCompany(ctx));

                // Assign role to a folder
                post("/{folderId}/role", ctx -> folderController.assignRole(ctx));

                // Assign sub role to a folder
                post("/{folderId}/subrole", ctx -> folderController.assignSubRole(ctx));
            });

            path("{companyName}", () -> {
                // Authorization for company routes
                before(ctx -> securityController.authorizeRole(ctx, Role.COMPANY_MANAGER));

                // Get all roles for a specific company
                get("/roles", ctx -> companyController.getRoles(ctx));
            });
        });
    }
}
