package org.controllers;

import io.javalin.http.Context;
import org.dtos.FolderDTO;
import org.entities.Company;
import org.entities.Role;
import org.entities.SubRole;
import org.exceptions.ApiException;
import org.folder.CompanyService;
import org.folder.FolderService;
import org.folder.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FolderController {

    private final FolderService folderService;
    private final CompanyService companyService;
    private static final Logger logger = LoggerFactory.getLogger(FolderController.class);

    public FolderController(FolderService folderService, CompanyService companyService) {
        this.folderService = folderService;
        this.companyService = companyService;
    }

    // Assign a Role to a folder
    public void assignRole(Context ctx) {
        Long folderId = Long.parseLong(ctx.pathParam("folderId")); // Now using Long
        Long roleId = ctx.bodyAsClass(Long.class);

        try {
            Role role = new Role();
            role.setId(roleId);
            folderService.assignRole(folderId, role);
            ctx.status(200).result("Role updated successfully.");
        } catch (Exception e) {
            logger.error("Error updating role for folder {}: {}", folderId, e.getMessage());
            ctx.status(500).result("Internal Server Error");
        }
    }

    // Assign a SubRole to a folder
    public void assignSubRole(Context ctx) {
        Long folderId = Long.parseLong(ctx.pathParam("folderId")); // Now using Long
        Long subRoleId = ctx.bodyAsClass(Long.class);

        try {
            SubRole subRole = new SubRole();
            subRole.setId(subRoleId);
            folderService.assignSubRole(folderId, subRole);
            ctx.status(200).result("SubRole updated successfully.");
        } catch (Exception e) {
            logger.error("Error updating subRole for folder {}: {}", folderId, e.getMessage());
            ctx.status(500).result("Internal Server Error");
        }
    }

    // Get all folders by a company's name
    public void getAllFoldersByCompanyName(Context ctx) {
        String companyName = ctx.pathParam("companyName");

        try {
            Company company = companyService.findByName(companyName);
            if (company == null) {
                ctx.status(404).result("Company not found.");
                return;
            }
            List<FolderDTO> folders = folderService.getFoldersByCompany(String.valueOf(company));
            ctx.json(folders);
            ctx.status(200);
        } catch (Exception e) {
            logger.error("Error retrieving folders for company {}: {}", companyName, e.getMessage());
            ctx.status(500).result("Error retrieving folders: " + e.getMessage());
        }
    }

    // Get a folder by its name
    public void getFolderByName(Context ctx) {
        String folderName = ctx.pathParam("folderName");
        User manager = ctx.attribute("user");

        if (manager == null) {
            ctx.status(401).result("User not authenticated.");
            return;
        }

        Company company = manager.getCompany();
        if (company == null || company.getCompanyName() == null) {
            ctx.status(400).result("Company information is missing.");
            return;
        }

        String companyName = company.getCompanyName();

        try {
            // Use the service method to get the folder by name and companyName
            FolderDTO folder = folderService.getFolderByName(folderName, companyName);
            ctx.json(folder);
            ctx.status(200);
        } catch (ApiException e) {
            ctx.status(e.getStatusCode()).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Internal Server Error");
        }
    }


    // Update permissions for a folder by its ID
    public void updateFolderPermissionsById(Context ctx) {
        Long folderId = Long.parseLong(ctx.pathParam("folderId")); // Now using Long

        try {
            FolderDTO updatedFolder = ctx.bodyAsClass(FolderDTO.class);
            folderService.updateFolderPermissions(folderId, updatedFolder);
            ctx.status(200).result("Permissions updated successfully.");
        } catch (Exception e) {
            logger.error("Error updating permissions for folder {}: {}", folderId, e.getMessage());
            ctx.status(500).result("Internal Server Error");
        }
    }

    // Create a new folder
    public void createFolder(Context ctx) {
        try {
            FolderDTO folderDTO = ctx.bodyAsClass(FolderDTO.class);
            folderService.createFolder(folderDTO);
            ctx.status(201).result("Folder created successfully.");
        } catch (Exception e) {
            logger.error("Error creating folder: {}", e.getMessage());
            ctx.status(500).result("Internal Server Error");
        }
    }

    // Delete a folder by its ID
    public void deleteFolder(Context ctx) {
        Long folderId = Long.parseLong(ctx.pathParam("folderId")); // Now using Long

        try {
            folderService.deleteFolder(folderId);
            ctx.status(200).result("Folder deleted successfully.");
        } catch (Exception e) {
            logger.error("Error deleting folder {}: {}", folderId, e.getMessage());
            ctx.status(500).result("Internal Server Error");
        }
    }

    public void getAllFoldersByCompanyId(Context ctx) {
        Long companyId = Long.parseLong(ctx.pathParam("companyId"));
        try {
            List<FolderDTO> folders = folderService.getFoldersByCompanyId(companyId);
            ctx.json(folders);
            ctx.status(200);
        } catch (Exception e) {
            logger.error("Error retrieving folders for company ID {}: {}", companyId, e.getMessage());
            ctx.status(500).result("Error retrieving folders: " + e.getMessage());
        }
    }

}
