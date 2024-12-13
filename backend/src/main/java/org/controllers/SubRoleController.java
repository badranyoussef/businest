package org.controllers;

import io.javalin.http.Context;
import org.daos.SubRoleFolderDAO;
import org.entities.SubRoleFolder;

import java.util.List;

public class SubRoleController {

    private SubRoleFolderDAO subRoleFolderDAO;

    public SubRoleController(SubRoleFolderDAO subRoleFolderDAO) {
        this.subRoleFolderDAO = subRoleFolderDAO;
    }

    public void createSubRole(Context ctx) {
        SubRoleFolder subRoleFolder = ctx.bodyAsClass(SubRoleFolder.class);
        SubRoleFolder createdSubRoleFolder = subRoleFolderDAO.create(subRoleFolder);
        ctx.status(201).json(createdSubRoleFolder);
    }

    public void getSubRoles(Context ctx) {
        List<SubRoleFolder> subRoleFolders = subRoleFolderDAO.findAll();
        ctx.json(subRoleFolders);
    }

    public void deleteSubRole(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        subRoleFolderDAO.delete(id);
        ctx.status(204);
    }
    public List<SubRoleFolder> getSubRolesByCompanyId(Long companyId) {
        return subRoleFolderDAO.findSubRolesByCompanyId(companyId);
    }
    public List<SubRoleFolder> getSubRolesByCompanyName(String companyName) {
        return subRoleFolderDAO.findSubRolesByCompanyName(companyName);
    }

}

