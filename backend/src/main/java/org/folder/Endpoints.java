package org.folder;

import io.javalin.Javalin;
import org.controllers.CompanyController;
import org.controllers.FolderController;
import org.controllers.RoleController;
import org.controllers.SubRoleController;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Endpoints {

    private final ISecurityController securityController;
    private final FolderController folderController;
    private final CompanyController companyController;
    private final RoleController roleController;
    private final SubRoleController subRoleController;

    public Endpoints(ISecurityController securityController, FolderController folderController,
                     CompanyController companyController, RoleController roleController,
                     SubRoleController subRoleController) {
        this.securityController = securityController;
        this.folderController = folderController;
        this.companyController = companyController;
        this.roleController = roleController;
        this.subRoleController = subRoleController;
    }

    public void registerRoutes(Javalin app) {
        // Apply authentication to all routes
        app.before("/*", ctx -> securityController.authenticate(ctx));

        // Define role-related routes
        app.routes(() -> {
            path("roles", () -> {
                before(ctx -> securityController.authorizeTitle(ctx, CompanyTitle.COMPANY_MANAGER));
                post(roleController::createRole);
                get(roleController::getRoles);
                delete("/{id}", roleController::deleteRole);
            });
        });

        // Define subrole-related routes
        app.routes(() -> {
            path("subroles", () -> {
                before(ctx -> securityController.authorizeTitle(ctx, CompanyTitle.COMPANY_MANAGER));
                post(subRoleController::createSubRole);
                get(subRoleController::getSubRoles);
                delete("/{id}", subRoleController::deleteSubRole);
            });
        });

        // Define folder-related routes
        app.routes(() -> {
            path("folders", () -> {
                before(ctx -> securityController.authorizeTitle(ctx, CompanyTitle.COMPANY_MANAGER));

                get("/{companyName}", folderController::getAllFoldersByCompanyName); // Get all folders by company
                get("/folder/{folderName}", folderController::getFolderByName); // Get folder by name
                post("/{folderId}/role", folderController::assignRole); // Assign role to folder
                post("/{folderId}/subrole", folderController::assignSubRole); // Assign subrole to folder
                post("/{folderId}/permissions", folderController::updateFolderPermissionsById); // Update permissions
            });
        });

        // Define company-specific routes
        app.routes(() -> {
            path("{companyName}", () -> {
                before(ctx -> securityController.authorizeTitle(ctx, CompanyTitle.COMPANY_MANAGER));
                get("/roles", companyController::getRoles); // Get roles for a company
            });
        });
    }
}
