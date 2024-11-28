package org.controllers;

import io.javalin.http.Context;
import org.daos.SubRoleDAO;
import org.folder.SubRole;

import java.util.List;

public class SubRoleController {

    private SubRoleDAO subRoleDAO;

    public SubRoleController(SubRoleDAO subRoleDAO) {
        this.subRoleDAO = subRoleDAO;
    }

    public void createSubRole(Context ctx) {
        SubRole subRole = ctx.bodyAsClass(SubRole.class);
        SubRole createdSubRole = subRoleDAO.create(subRole);
        ctx.status(201).json(createdSubRole);
    }

    public void getSubRoles(Context ctx) {
        List<SubRole> subRoles = subRoleDAO.findAll();
        ctx.json(subRoles);
    }

    public void deleteSubRole(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        subRoleDAO.delete(id);
        ctx.status(204);
    }
}

