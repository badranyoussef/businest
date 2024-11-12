package org.folder;

import io.javalin.http.Context;
import com.google.gson.Gson;

import java.util.List;

public class FolderController {

    private FolderService folderService;
    private Gson gson = new Gson();

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    public void getFoldersByCompany(Context ctx) {
        User manager = ctx.attribute("user");
        String company = manager.getCompany();

        try {
            List<Folder> folders = folderService.getFoldersInCompany(company);
            ctx.json(folders);
        } catch (Exception e) {
            ctx.status(500).result("Internal Server Error");
            // Log the exception
        }
    }

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
        } catch (IllegalArgumentException e) {
            ctx.status(404).result("Not Found: " + e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Internal Server Error");
            // Log the exception
        }
    }

    // Inner class to represent the request body
    class RoleAssignmentRequest {
        private String folderId;
        private Role newRole;

        // Getters and setters
        public String getFolderId() { return folderId; }
        public void setFolderId(String folderId) { this.folderId = folderId; }
        public Role getNewRole() { return newRole; }
        public void setNewRole(Role newRole) { this.newRole = newRole; }
    }
}
