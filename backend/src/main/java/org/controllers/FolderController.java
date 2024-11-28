package org.controllers;

import io.javalin.http.Context;
import org.dtos.FolderDTO;
import org.folder.FolderService;
import org.folder.Role;
import org.folder.SubRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FolderController {

    private final FolderService folderService;
    private static final Logger logger = LoggerFactory.getLogger(FolderController.class);

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    // Assign a Role to a folder
    public void assignRole(Context ctx) {
        String folderId = ctx.pathParam("folderId");
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
        String folderId = ctx.pathParam("folderId");
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
            List<FolderDTO> folders = folderService.getFoldersByCompany(companyName);
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
        String companyName = ctx.queryParam("companyName");

        if (companyName == null || companyName.isBlank()) {
            ctx.status(400).result("Company name is required.");
            return;
        }

        try {
            // Fetch folder by name within the context of a specific company
            FolderDTO folder = folderService.getFoldersByCompany(companyName).stream()
                    .filter(f -> f.getName().equalsIgnoreCase(folderName))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Folder not found."));

            ctx.json(folder);
            ctx.status(200);
        } catch (IllegalArgumentException e) {
            logger.error("Error retrieving folder by name {}: {}", folderName, e.getMessage());
            ctx.status(404).result(e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error retrieving folder by name {}: {}", folderName, e.getMessage());
            ctx.status(500).result("Internal Server Error");
        }
    }

    // Update permissions for a folder by its ID
    public void updateFolderPermissionsById(Context ctx) {
        String folderId = ctx.pathParam("folderId");

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
        String folderId = ctx.pathParam("folderId");

        try {
            folderService.deleteFolder(folderId);
            ctx.status(200).result("Folder deleted successfully.");
        } catch (Exception e) {
            logger.error("Error deleting folder {}: {}", folderId, e.getMessage());
            ctx.status(500).result("Internal Server Error");
        }
    }
}
