package org.controllers;

import io.javalin.http.Context;
import org.daos.RoleFolderDAO;
import org.entities.RoleFolder;

import java.util.List;

public class RoleFolderController {

    private RoleFolderDAO roleFolderDAO;

    public RoleFolderController(RoleFolderDAO roleFolderDAO) {
        this.roleFolderDAO = roleFolderDAO;
    }

    public void createRole(Context ctx) {
        RoleFolder roleFolder = ctx.bodyAsClass(RoleFolder.class);
        RoleFolder createdRoleFolder = roleFolderDAO.create(roleFolder);
        ctx.status(201).json(createdRoleFolder);
    }

    public void getRoles(Context ctx) {
        List<RoleFolder> roleFolders = roleFolderDAO.findAll();
        ctx.json(roleFolders);
    }

    public void deleteRole(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        roleFolderDAO.delete(id);
        ctx.status(204);
    }
    public List<RoleFolder> getRolesByCompanyId(Long companyId) {
        return roleFolderDAO.findRolesByCompanyId(companyId);
    }

    public List<RoleFolder> getRolesByCompanyName(String companyName) {
        return roleFolderDAO.findRolesByCompanyName(companyName);
    }
}
