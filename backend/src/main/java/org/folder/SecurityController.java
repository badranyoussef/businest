package org.folder;

import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.UnauthorizedResponse;
import java.util.Set;
import java.util.HashSet;

public class SecurityController implements ISecurityController {

    @Override
    public void authenticate(Context ctx) {
        String token = ctx.header("Authorization");

        if (token == null || token.isEmpty()) {
            throw new UnauthorizedResponse("Unauthorized");
        }

        User user = getUserByToken(token);

        if (user == null) {
            throw new UnauthorizedResponse("Unauthorized");
        }

        ctx.attribute("user", user);
    }

    @Override
    public void authorizeRole(Context ctx, Role requiredRole) {
        User user = ctx.attribute("user");

        if (user == null || !user.getRoles().contains(requiredRole)) {
            throw new ForbiddenResponse("Forbidden");
        }
    }

    private User getUserByToken(String token) {
        // Replace with your actual token validation logic

        if (!isValidToken(token)) {
            return null;
        }

        // Example user
        User user = new User();
        user.setId("user123");
        user.setUsername("john.doe");
        user.setCompany("ExampleCompany");

        Set<Role> roles = new HashSet<>();
        roles.add(Role.COMPANY_MANAGER);
        user.setRoles(roles);

        return user;
    }

    private boolean isValidToken(String token) {
        // Replace with actual validation
        return true;
    }
}
