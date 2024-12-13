package org.controllers;

import org.entities.RoleFolder;
import org.entities.SubRoleFolder;
import org.exceptions.ApiException;
import io.javalin.http.Context;
import org.dtos.FolderDTO;
import org.folder.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CompanyController {

    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    private final FolderService folderService;
    private final CompanyService companyService;
    private final RoleFolderController roleFolderController;
    private final SubRoleController subRoleController;

    public CompanyController(CompanyService companyService, FolderService folderService, RoleFolderController roleFolderController, SubRoleController subRoleController) {
        this.companyService = companyService;
        this.folderService = folderService;
        this.roleFolderController = roleFolderController;
        this.subRoleController = subRoleController;
    }

    // Retrieve roleFolders by company name
    public void getRoles(Context ctx) {
        String companyName = ctx.pathParam("companyName");
        try {
            List<RoleFolder> roleFolders = companyService.getRolesByCompany(companyName);
            ctx.json(roleFolders);
            ctx.status(200);
        } catch (Exception e) {
            ctx.status(500).result("Error retrieving roleFolders: " + e.getMessage());
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
        UserFolder manager = ctx.attribute("user");
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
            List<RoleFolder> roleFolders = roleFolderController.getRolesByCompanyId(companyId);
            ctx.json(roleFolders);
            ctx.status(200);
        } catch (Exception e) {
            ctx.status(500).result("Error retrieving roleFolders: " + e.getMessage());
        }
    }


    // Get all subroles by company ID
    public void getAllSubRolesByCompanyId(Context ctx) {
        Long companyId = Long.parseLong(ctx.pathParam("companyId"));
        try {
            List<SubRoleFolder> subRoleFolders = subRoleController.getSubRolesByCompanyId(companyId);
            ctx.json(subRoleFolders);
            ctx.status(200);
        } catch (Exception e) {
            ctx.status(500).result("Error retrieving subroles: " + e.getMessage());
        }
    }


    public void getAllRolesByCompanyName(Context ctx) {
        String companyName = ctx.pathParam("companyName");

        if (companyName == null || companyName.isEmpty()) {
            ctx.status(400).result("Company name is missing.");
            return;
        }

        try {
            List<RoleFolder> roleFolders = companyService.getRolesByCompanyName(companyName);
            ctx.json(roleFolders);
            ctx.status(200);
        } catch (ApiException e) {
            logger.error("Error retrieving roleFolders for company '{}': {}", companyName, e.getMessage());
            ctx.status(e.getStatusCode()).result(e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error retrieving roleFolders for company '{}': {}", companyName, e.getMessage());
            ctx.status(500).result("Internal Server Error");
        }
    }

    public void getAllSubRolesByCompanyName(Context ctx) {
        String companyName = ctx.pathParam("companyName");

        if (companyName == null || companyName.isEmpty()) {
            ctx.status(400).result("Company name is missing.");
            return;
        }

        try {
            List<SubRoleFolder> subRoleFolders = companyService.getSubRolesByCompanyName(companyName);
            ctx.json(subRoleFolders);
            ctx.status(200);
        } catch (ApiException e) {
            logger.error("Error retrieving sub-roleFolders for company '{}': {}", companyName, e.getMessage());
            ctx.status(e.getStatusCode()).result(e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error retrieving sub-roleFolders for company '{}': {}", companyName, e.getMessage());
            ctx.status(500).result("Internal Server Error");
        }
    }

}