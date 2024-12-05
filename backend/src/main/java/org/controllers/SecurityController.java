package org.controllers;

import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.UnauthorizedResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.daos.RoleDAO;
import org.daos.SubRoleDAO;
import org.entities.CompanyTitle;
import org.entities.Role;
import org.entities.SubRole;
import org.folder.*;

import org.entities.Company;

import org.folder.User;

    public class SecurityController implements ISecurityController {

        private final RoleDAO roleDAO;
        private final SubRoleDAO subRoleDAO;

        public SecurityController(RoleDAO roleDAO, SubRoleDAO subRoleDAO) {
            this.roleDAO = roleDAO;
            this.subRoleDAO = subRoleDAO;
        }

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

            if (user == null || user.getCompanyTitle() == null || !user.getCompanyTitle().equals(requiredTitle)) {
                throw new ForbiddenResponse("Forbidden: Insufficient company title.");
            }
        }

        @Override
        public void authorizeRole(Context ctx, Role requiredRole) {
            User user = ctx.attribute("user");

            if (user == null || user.getRoles() == null || user.getRoles().isEmpty()) {
                throw new ForbiddenResponse("Forbidden: User not authenticated or has no roles assigned.");
            }

            boolean hasRole = user.getRoles().stream()
                    .anyMatch(role -> role.getName().equals(requiredRole.getName()));

            if (!hasRole) {
                throw new ForbiddenResponse("Forbidden: Insufficient role.");
            }
        }

        @Override
        public void authorizeSubRole(Context ctx, SubRole requiredSubRole) {
            User user = ctx.attribute("user");

            if (user == null || user.getSubRoles() == null || user.getSubRoles().isEmpty()) {
                throw new ForbiddenResponse("Forbidden: User not authenticated or has no sub-roles assigned.");
            }

            boolean hasSubRole = user.getSubRoles().stream()
                    .anyMatch(subRole -> subRole.getName().equals(requiredSubRole.getName()));

            if (!hasSubRole) {
                throw new ForbiddenResponse("Forbidden: Insufficient sub-role.");
            }
        }

        private User getUserByToken(String token) {
            if (!isValidToken(token)) {
                return null;
            }

            if ("validToken".equals(token)) {
                User user = new User();
                user.setId(123L);
                user.setUsername("john.doe");

                Company company = new Company();
                company.setId(1L);
                company.setCompanyName("ExampleCompany");
                user.setCompany(company);

                user.setCompanyTitle(CompanyTitle.COMPANY_MANAGER);

                user.setRoles(getRolesForUser(user.getId()));
                user.setSubRoles(getSubRolesForUser(user.getId()));

                return user;
            }
            return null;
        }

        private boolean isValidToken(String token) {
            return "validToken".equals(token);
        }

        private Set<Role> getRolesForUser(Long userId) {
            List<Role> roles = roleDAO.findRolesByUserId(userId);
            return new HashSet<>(roles);
        }

        private Set<SubRole> getSubRolesForUser(Long userId) {
            List<SubRole> subRoles = subRoleDAO.findSubRolesByUserId(userId);
            return new HashSet<>(subRoles);
        }
    }

