package org.controllers;

import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.UnauthorizedResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.daos.RoleFolderDAO;
import org.daos.SubRoleFolderDAO;
import org.entities.CompanyTitle;
import org.entities.RoleFolder;
import org.entities.SubRoleFolder;
import org.folder.*;

import org.entities.Company;

import org.folder.UserFolder;

    public class SecurityController implements ISecurityController {

        private final RoleFolderDAO roleFolderDAO;
        private final SubRoleFolderDAO subRoleFolderDAO;

        public SecurityController(RoleFolderDAO roleFolderDAO, SubRoleFolderDAO subRoleFolderDAO) {
            this.roleFolderDAO = roleFolderDAO;
            this.subRoleFolderDAO = subRoleFolderDAO;
        }

        @Override
        public void authenticate(Context ctx) {
            String authHeader = ctx.header("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new UnauthorizedResponse("Unauthorized: Missing or invalid Authorization header.");
            }

            String token = authHeader.substring(7);

            UserFolder userFolder = getUserByToken(token);

            if (userFolder == null) {
                throw new UnauthorizedResponse("Unauthorized: Invalid token.");
            }

            ctx.attribute("userFolder", userFolder);
        }

        @Override
        public void authorizeTitle(Context ctx, CompanyTitle requiredTitle) {
            UserFolder userFolder = ctx.attribute("userFolder");

            if (userFolder == null || userFolder.getCompanyTitle() == null || !userFolder.getCompanyTitle().equals(requiredTitle)) {
                throw new ForbiddenResponse("Forbidden: Insufficient company title.");
            }
        }

        @Override
        public void authorizeRole(Context ctx, RoleFolder requiredRoleFolder) {
            UserFolder userFolder = ctx.attribute("userFolder");

            if (userFolder == null || userFolder.getRoleFolders() == null || userFolder.getRoleFolders().isEmpty()) {
                throw new ForbiddenResponse("Forbidden: UserFolder not authenticated or has no roleFolders assigned.");
            }

            boolean hasRole = userFolder.getRoleFolders().stream()
                    .anyMatch(role -> role.getName().equals(requiredRoleFolder.getName()));

            if (!hasRole) {
                throw new ForbiddenResponse("Forbidden: Insufficient roleFolder.");
            }
        }

        @Override
        public void authorizeSubRole(Context ctx, SubRoleFolder requiredSubRoleFolder) {
            UserFolder userFolder = ctx.attribute("userFolder");

            if (userFolder == null || userFolder.getSubRoleFolders() == null || userFolder.getSubRoleFolders().isEmpty()) {
                throw new ForbiddenResponse("Forbidden: UserFolder not authenticated or has no sub-roleFolders assigned.");
            }

            boolean hasSubRole = userFolder.getSubRoleFolders().stream()
                    .anyMatch(subRole -> subRole.getName().equals(requiredSubRoleFolder.getName()));

            if (!hasSubRole) {
                throw new ForbiddenResponse("Forbidden: Insufficient sub-roleFolder.");
            }
        }

        private UserFolder getUserByToken(String token) {
            if (!isValidToken(token)) {
                return null;
            }

            if ("validToken".equals(token)) {
                UserFolder userFolder = new UserFolder();
                userFolder.setId(123L);
                userFolder.setUsername("john.doe");

                Company company = new Company();
                company.setId(1L);
                company.setCompanyName("ExampleCompany");
                userFolder.setCompany(company);

                userFolder.setCompanyTitle(CompanyTitle.COMPANY_MANAGER);

                userFolder.setRoleFolders(getRolesForUser(userFolder.getId()));
                userFolder.setSubRoleFolders(getSubRolesForUser(userFolder.getId()));

                return userFolder;
            }
            return null;
        }

        private boolean isValidToken(String token) {
            return "validToken".equals(token);
        }

        private Set<RoleFolder> getRolesForUser(Long userId) {
            List<RoleFolder> roleFolders = roleFolderDAO.findRolesByUserId(userId);
            return new HashSet<>(roleFolders);
        }

        private Set<SubRoleFolder> getSubRolesForUser(Long userId) {
            List<SubRoleFolder> subRoleFolders = subRoleFolderDAO.findSubRolesByUserId(userId);
            return new HashSet<>(subRoleFolders);
        }
    }

