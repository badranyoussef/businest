package org.controllers;

import org.exceptions.ApiException;
import io.javalin.http.Context;
import org.dtos.FolderDTO;
import org.folder.*;

import java.util.List;

public class CompanyController {

    private final FolderService folderService;
    private final CompanyService companyService;
    private final RoleController roleController;
    private final SubRoleController subRoleController;

    public CompanyController(CompanyService companyService, FolderService folderService, RoleController roleController, SubRoleController subRoleController) {
        this.companyService = companyService;
        this.folderService = folderService;
        this.roleController = roleController;
        this.subRoleController = subRoleController;
    }

    // Retrieve roles by company name
    public void getRoles(Context ctx) {
        String companyName = ctx.pathParam("companyName");
        try {
            List<Role> roles = companyService.getRolesByCompany(companyName);
            ctx.json(roles);
            ctx.status(200);
        } catch (Exception e) {
            ctx.status(500).result("Error retrieving roles: " + e.getMessage());
        }
    }


    // Retrieve all folders for a company
    public void getAllFoldersByCompanyName(Context ctx) {
        String companyName = ctx.pathParam("companyName");

        try {
            List<FolderDTO> folders = folderService.getFoldersByCompany(companyName);
            ctx.json(folders);
            ctx.status(200);
        } catch (Exception e) {
            ctx.status(500).result("Error retrieving folders: " + e.getMessage());
        }
    }

    // Retrieve a folder by name
    public void getFolderByName(Context ctx) {
        String folderName = ctx.pathParam("folderName");
        User manager = ctx.attribute("user");
        String company = String.valueOf(manager.getCompany());

        try {
            // Use the service method to get the folder by name and company
            FolderDTO folder = folderService.getFolderByName(folderName, company);
            ctx.json(folder);
            ctx.status(200);
        } catch (ApiException e) {
            ctx.status(e.getStatusCode()).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Internal Server Error");
        }
    }
    public void getAllRolesByCompanyId(Context ctx) {
        Long companyId = Long.parseLong(ctx.pathParam("companyId"));
        try {
            List<Role> roles = roleController.getRolesByCompanyId(companyId);
            ctx.json(roles);
            ctx.status(200);
        } catch (Exception e) {
            ctx.status(500).result("Error retrieving roles: " + e.getMessage());
        }
    }


    // Get all subroles by company ID
    public void getAllSubRolesByCompanyId(Context ctx) {
        Long companyId = Long.parseLong(ctx.pathParam("companyId"));
        try {
            List<SubRole> subRoles = subRoleController.getSubRolesByCompanyId(companyId);
            ctx.json(subRoles);
            ctx.status(200);
        } catch (Exception e) {
            ctx.status(500).result("Error retrieving subroles: " + e.getMessage());
        }
    }
}
