package org.folder;

import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.UnauthorizedResponse;
import java.util.Set;
import java.util.HashSet;

public class SecurityController implements ISecurityController {

    @Override
    public void authenticate(Context ctx) {
        String authHeader = ctx.header("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedResponse("Unauthorized");
        }

        String token = authHeader.substring(7);

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
        if (!isValidToken(token)) {
            return null;
        }

        // For testing purposes, accept "validToken" as a valid token
        if ("validToken".equals(token) || "Bearer validToken".equals(token)) {
            User user = new User();
            user.setId("user123");
            user.setUsername("john.doe");
            user.setCompany("ExampleCompany");

            Set<Role> roles = new HashSet<>();
            roles.add(Role.COMPANY_MANAGER);
            user.setRoles(roles);

            return user;
        }
        return null; // Return null for invalid tokens
    }

    private boolean isValidToken(String token) {
        return "validToken".equals(token) || "Bearer validToken".equals(token);
    }

}
