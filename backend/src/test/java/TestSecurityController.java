import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import org.entities.Company;
import org.entities.CompanyTitle;
import org.entities.Role;
import org.entities.SubRole;
import org.folder.ISecurityController;
import org.folder.User;

import java.util.HashSet;
import java.util.Set;

public class TestSecurityController implements ISecurityController {

    @Override
    public void authenticate(Context ctx) {
        User testUser = new User();
        testUser.setId(123L);
        testUser.setUsername("john.doe");
        testUser.setCompany(new Company(1L, "ExampleCompany", null, null));

        // For testing, assign roles and subroles based on headers
        Set<Role> roles = extractRolesFromHeader(ctx);
        Set<SubRole> subRoles = extractSubRolesFromHeader(ctx);

        testUser.setRoles(roles);
        testUser.setSubRoles(subRoles);

        ctx.attribute("user", testUser);
    }

    @Override
    public void authorizeRole(Context ctx, Role requiredRole) {
        User user = ctx.attribute("user");

        if (user == null || user.getRoles() == null) {
            throw new ForbiddenResponse("Forbidden: No user authenticated.");
        }

        boolean hasRole = user.getRoles().stream()
                .anyMatch(role -> role.getName().equals(requiredRole.getName()));

        if (!hasRole) {
            throw new ForbiddenResponse("Forbidden: Insufficient role.");
        }
    }

    @Override
    public void authorizeTitle(Context ctx, CompanyTitle requiredTitle) {

    }


    @Override
    public void authorizeSubRole(Context ctx, SubRole requiredSubRole) {
        User user = ctx.attribute("user");

        if (user == null || user.getSubRoles() == null) {
            throw new ForbiddenResponse("Forbidden: No user authenticated.");
        }

        boolean hasSubRole = user.getSubRoles().stream()
                .anyMatch(subRole -> subRole.getName().equals(requiredSubRole.getName()));

        if (!hasSubRole) {
            throw new ForbiddenResponse("Forbidden: Insufficient sub-role.");
        }
    }

    // Helper method to extract roles from headers
    private Set<Role> extractRolesFromHeader(Context ctx) {
        String rolesHeader = ctx.header("Test-User-Roles");
        Set<Role> roles = new HashSet<>();
        if (rolesHeader != null) {
            String[] roleNames = rolesHeader.split(",");
            for (String roleName : roleNames) {
                Role role = new Role();
                role.setName(roleName.trim());
                roles.add(role);
            }
        }
        return roles;
    }

    // Helper method to extract subroles from headers
    private Set<SubRole> extractSubRolesFromHeader(Context ctx) {
        String subRolesHeader = ctx.header("Test-User-SubRoles");
        Set<SubRole> subRoles = new HashSet<>();
        if (subRolesHeader != null) {
            String[] subRoleNames = subRolesHeader.split(",");
            for (String subRoleName : subRoleNames) {
                SubRole subRole = new SubRole();
                subRole.setName(subRoleName.trim());
                subRoles.add(subRole);
            }
        }
        return subRoles;
    }
}
