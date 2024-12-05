package org.controllers;

import io.javalin.http.Handler;
import org.daos.RoleDAO;
import org.daos.SubRoleDAO;
import org.dtos.RoleDTO;
import org.dtos.SubRoleDTO;
import org.entities.Role;
import org.entities.SubRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RoleController {
    private SubRoleDAO subRoleDao;
    private RoleDAO roleDao;

    public RoleController(SubRoleDAO subRoleDao, RoleDAO roleDao) {
        this.subRoleDao = subRoleDao;
        this.roleDao = roleDao;
    }


    public Handler getRolesAndSubRoles = ctx -> {
        try {
            var roles = roleDao.getAllRoles()
                            .stream()
                            .map(r -> new RoleDTO(r.getTitle(), r.getSubRoles()))
                            .collect(Collectors.toList());
            /*var subRoles = subRoleDao.getAllSubRoles()
                            .stream()
                            .map(s -> new SubRoleDTO(s.getTitle()))
                            .collect(Collectors.toList());
            HashMap<String, Object> model = new HashMap<>();
            model.put("roles", roles);
            model.put("subRoles", subRoles);*/
            ctx.json(roles);
        } catch (Exception e) {
            e.printStackTrace(); // Log the full stack trace
            ctx.status(500).result("Server error: " + e.getMessage());
        }
    };

}
