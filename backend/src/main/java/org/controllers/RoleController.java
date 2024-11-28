package org.controllers;

import io.javalin.http.Context;
import org.daos.RoleDAO;
import org.folder.Role;

import java.util.List;

public class RoleController {

    private RoleDAO roleDAO;

    public RoleController(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    public void createRole(Context ctx) {
        Role role = ctx.bodyAsClass(Role.class);
        Role createdRole = roleDAO.create(role);
        ctx.status(201).json(createdRole);
    }

    public void getRoles(Context ctx) {
        List<Role> roles = roleDAO.findAll();
        ctx.json(roles);
    }

    public void deleteRole(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        roleDAO.delete(id);
        ctx.status(204);
    }
}
