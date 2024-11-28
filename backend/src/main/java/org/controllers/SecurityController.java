package org.controllers;

import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.UnauthorizedResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.daos.RoleDAO;
import org.daos.SubRoleDAO;
import org.folder.*;

public class SecurityController implements ISecurityController {

    private RoleDAO roleDAO;
    private SubRoleDAO subRoleDAO;
    @Override
    public void authenticate(Context ctx) {
        String authHeader = ctx.header("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedResponse("Unauthorized: Missing or invalid Authorization header.");
        }

        String token = authHeader.substring(7);

        User user = getUserByToken(token);

        if (user == null) {
            throw new UnauthorizedResponse("Unauthorized: Invalid token.");
        }

        ctx.attribute("user", user);
    }

    @Override
    public void authorizeTitle(Context ctx, CompanyTitle requiredTitle) {
        User user = ctx.attribute("user");

        if (user == null || user.getCompanyTitle() != requiredTitle) {
            throw new ForbiddenResponse("Forbidden: Insufficient company title.");
        }
    }


    @Override
    public void authorizeRole(Context ctx, Role requiredRole) {
        User user = ctx.attribute("user");

        if (user == null || user.getRoles() == null || !user.getRoles().contains(requiredRole)) {
            throw new ForbiddenResponse("Forbidden: Insufficient role.");
        }
    }

    @Override
    public void authorizeSubRole(Context ctx, SubRole requiredSubRole) {
        User user = ctx.attribute("user");

        if (user == null || user.getSubRoles() == null || !user.getSubRoles().contains(requiredSubRole)) {
            throw new ForbiddenResponse("Forbidden: Insufficient sub-role.");
        }
    }

    private User getUserByToken(String token) {
        if (!isValidToken(token)) {
            return null;
        }

        // For testing purposes, accept "validToken" as a valid token
        if ("validToken".equals(token)) {
            User user = new User();
            user.setId(123L);
            user.setUsername("john.doe");
            user.setCompany("ExampleCompany");

            user.setCompanyTitle(CompanyTitle.COMPANY_MANAGER);


            user.setRoles(getRolesForUser("123"));
            user.setSubRoles(getSubRolesForUser("123"));

            return user;
        }
        return null; // Return null for invalid tokens
    }
    private boolean isValidToken(String token) {
        return "validToken".equals(token);
    }

    private Set<Role> getRolesForUser(String userId) {
        List<Role> roles = roleDAO.findRolesByUserId(userId);
        return new HashSet<>(roles);
    }

    private Set<SubRole> getSubRolesForUser(String userId) {
        List<SubRole> subRoles = subRoleDAO.findSubRolesByUserId(userId);
        return new HashSet<>(subRoles);
    }

}
