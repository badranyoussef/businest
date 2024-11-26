package org.folder;

import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;

import java.util.Set;

public class TestSecurityController implements ISecurityController {

    @Override
    public void authenticate(Context ctx) {
        // Simulate authenticated user
        User testUser = new User();
        testUser.setId("user123");
        testUser.setUsername("john.doe");
        testUser.setCompany("ExampleCompany");
        testUser.setRoles(Set.of(Role.COMPANY_MANAGER));
        testUser.setSubRoles(Set.of(SubRole.MEDIUM)); // Initialize with desired SubRoles
        ctx.attribute("user", testUser);
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
}
