package org.controllers;

import io.javalin.http.Context;
import org.folder.CompanyService;
import org.folder.Role;

import java.util.List;

public class CompanyController {

    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

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
}
