package org.folder;

import com.github.dockerjava.api.exception.NotFoundException;
import io.javalin.http.Context;
import com.google.gson.Gson;
import org.dtos.FolderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class FolderController {

    private FolderService folderService;
    private Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(FolderController.class);

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    // Existing method to get folders by company
    public void getFoldersByCompany(Context ctx) {
        User manager = ctx.attribute("user");
        String company = manager.getCompany();

        try {
            List<FolderDTO> folders = folderService.getFoldersInCompany(company);
            ctx.json(folders);
        } catch (Exception e) {
            logger.error("Error fetching folders for company {}: {}", company, e.getMessage());
            ctx.status(500).result("Internal Server Error");
        }
    }

    // Existing method to assign role
    public void assignRole(Context ctx) {
        User manager = ctx.attribute("user");
        String company = manager.getCompany();

        RoleAssignmentRequest roleRequest = gson.fromJson(ctx.body(), RoleAssignmentRequest.class);

        if (roleRequest == null || roleRequest.getFolderId() == null || roleRequest.getNewRole() == null) {
            ctx.status(400).result("Bad Request: Missing folder ID or new role.");
            return;
        }

        try {
            folderService.assignRole(roleRequest.getFolderId(), company, roleRequest.getNewRole());
            ctx.status(200).result("Role updated successfully.");
        } catch (SecurityException e) {
            ctx.status(403).result("Forbidden: " + e.getMessage());
        } catch (NotFoundException e) {
            ctx.status(404).result("Not Found: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error assigning role: {}", e.getMessage());
            ctx.status(500).result("Internal Server Error");
        }
    }

    // New method to assign SubRole
    public void assignSubRole(Context ctx) {
        User manager = ctx.attribute("user");
        String company = manager.getCompany();

        SubRoleAssignmentRequest subRoleRequest = gson.fromJson(ctx.body(), SubRoleAssignmentRequest.class);

        if (subRoleRequest == null || subRoleRequest.getFolderId() == null || subRoleRequest.getNewSubRole() == null) {
            ctx.status(400).result("Bad Request: Missing folder ID or new sub role.");
            return;
        }

        try {
            folderService.updateSubRole(subRoleRequest.getFolderId(), company, subRoleRequest.getNewSubRole());
            ctx.status(200).result("SubRole updated successfully.");
        } catch (SecurityException e) {
            ctx.status(403).result("Forbidden: " + e.getMessage());
        } catch (NotFoundException e) {
            ctx.status(404).result("Not Found: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error assigning sub role: {}", e.getMessage());
            ctx.status(500).result("Internal Server Error");
        }
    }

    // Inner class to represent the request body for Role assignment
    class RoleAssignmentRequest {
        private String folderId;
        private Role newRole;

        // Getters and setters
        public String getFolderId() { return folderId; }
        public void setFolderId(String folderId) { this.folderId = folderId; }
        public Role getNewRole() { return newRole; }
        public void setNewRole(Role newRole) { this.newRole = newRole; }
    }

    // Inner class to represent the request body for SubRole assignment
    class SubRoleAssignmentRequest {
        private String folderId;
        private SubRole newSubRole;

        // Getters and setters
        public String getFolderId() { return folderId; }
        public void setFolderId(String folderId) { this.folderId = folderId; }
        public SubRole getNewSubRole() { return newSubRole; }
        public void setNewSubRole(SubRole newSubRole) { this.newSubRole = newSubRole; }
    }
}
