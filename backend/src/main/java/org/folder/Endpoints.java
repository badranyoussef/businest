package org.folder;

import io.javalin.Javalin;
import org.controllers.*;
import org.controllers.FolderController;
import org.controllers.RoleFolderController;
import org.controllers.SubRoleController;
import org.entities.CompanyTitle;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Endpoints {

    private final ISecurityController securityController;
    private final FolderController folderController;
    private final CompanyController companyController;
    private final RoleFolderController roleFolderController;
    private final SubRoleController subRoleController;

    public Endpoints(ISecurityController securityController, FolderController folderController,
                     CompanyController companyController, RoleFolderController roleFolderController,
                     SubRoleController subRoleController) {
        this.securityController = securityController;
        this.folderController = folderController;
        this.companyController = companyController;
        this.roleFolderController = roleFolderController;
        this.subRoleController = subRoleController;
    }

    public void registerRoutes(Javalin app) {
        // Apply authentication to all routes
        app.before("/*", ctx -> securityController.authenticate(ctx));

        // Define roleFolder-related routes
        app.routes(() -> {
            path("roleFolders", () -> {
                before(ctx -> securityController.authorizeTitle(ctx, CompanyTitle.COMPANY_MANAGER));
                post(roleFolderController::createRole);
                get(roleFolderController::getRoles);
                delete("{id}", roleFolderController::deleteRole);
            });
        });

        // Define subrole-related routes
        app.routes(() -> {
            path("subroles", () -> {
                before(ctx -> securityController.authorizeTitle(ctx, CompanyTitle.COMPANY_MANAGER));
                post(subRoleController::createSubRole);
                get(subRoleController::getSubRoles);
                delete("{id}", subRoleController::deleteSubRole);
            });
        });

        // Define folder-related routes
        app.routes(() -> {
            path("folders", () -> {
                before(ctx -> securityController.authorizeTitle(ctx, CompanyTitle.COMPANY_MANAGER));

                // Get folder by name
                get("{folderName}", folderController::getFolderByName); // GET /folders/{folderName}

                // Get all folders by company name
                get("{companyName}", folderController::getAllFoldersByCompanyName); // GET /folders/{companyName}

                // Update folder permissions
                post("{folderId}/permissions", folderController::updateFolderPermissionsById); // POST /folders/{folderId}/permissions

                // Assign roleFolder to folder
                post("{folderId}/roleFolder", folderController::assignRole); // POST /folders/{folderId}/roleFolder

                // Assign subrole to folder
                post("{folderId}/subrole", folderController::assignSubRole); // POST /folders/{folderId}/subrole
            });
        });

        // Define company-specific routes
        app.routes(() -> {
            // Apply authorization to all routes under this path
            before(ctx -> securityController.authorizeTitle(ctx, CompanyTitle.COMPANY_MANAGER));

            // Routes using company ID
            path("companies/{companyId}", () -> {
                // Get all roleFolders for a company by ID
                get("roleFolders", ctx -> {
                    companyController.getAllRolesByCompanyId(ctx); // GET /companies/{companyId}/roleFolders
                });

                // Get all subroles for a company by ID
                get("subroles", ctx -> {
                    companyController.getAllSubRolesByCompanyId(ctx); // GET /companies/{companyId}/subroles
                });
            });

            // Routes using company name
            path("companies/{companyName}", () -> {
                // Get roleFolders for a company by name
                get("roleFolders", ctx -> {
                    companyController.getAllRolesByCompanyName(ctx); // GET /companies/{companyName}/roleFolders
                });

                // Get subroles for a company by name
                get("subroles", ctx -> {
                    companyController.getAllSubRolesByCompanyName(ctx); // GET /companies/{companyName}/subroles
                });
            });
        });

    }
}
