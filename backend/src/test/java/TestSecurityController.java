import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import org.entities.Company;
import org.entities.CompanyTitle;
import org.entities.RoleFolder;
import org.entities.SubRoleFolder;
import org.folder.ISecurityController;
import org.folder.UserFolder;

import java.util.HashSet;
import java.util.Set;

public class TestSecurityController implements ISecurityController {

    @Override
    public void authenticate(Context ctx) {
        UserFolder testUserFolder = new UserFolder();
        testUserFolder.setId(123L);
        testUserFolder.setUsername("john.doe");
        testUserFolder.setCompany(new Company(1L, "ExampleCompany", null, null));

        // For testing, assign roleFolders and subroles based on headers
        Set<RoleFolder> roleFolders = extractRolesFromHeader(ctx);
        Set<SubRoleFolder> subRoleFolders = extractSubRolesFromHeader(ctx);

        testUserFolder.setRoleFolders(roleFolders);
        testUserFolder.setSubRoleFolders(subRoleFolders);

        ctx.attribute("user", testUserFolder);
    }

    @Override
    public void authorizeRole(Context ctx, RoleFolder requiredRoleFolder) {
        UserFolder userFolder = ctx.attribute("userFolder");

        if (userFolder == null || userFolder.getRoleFolders() == null) {
            throw new ForbiddenResponse("Forbidden: No userFolder authenticated.");
        }

        boolean hasRole = userFolder.getRoleFolders().stream()
                .anyMatch(role -> role.getName().equals(requiredRoleFolder.getName()));

        if (!hasRole) {
            throw new ForbiddenResponse("Forbidden: Insufficient roleFolder.");
        }
    }

    @Override
    public void authorizeTitle(Context ctx, CompanyTitle requiredTitle) {

    }


    @Override
    public void authorizeSubRole(Context ctx, SubRoleFolder requiredSubRoleFolder) {
        UserFolder userFolder = ctx.attribute("userFolder");

        if (userFolder == null || userFolder.getSubRoleFolders() == null) {
            throw new ForbiddenResponse("Forbidden: No userFolder authenticated.");
        }

        boolean hasSubRole = userFolder.getSubRoleFolders().stream()
                .anyMatch(subRole -> subRole.getName().equals(requiredSubRoleFolder.getName()));

        if (!hasSubRole) {
            throw new ForbiddenResponse("Forbidden: Insufficient sub-roleFolder.");
        }
    }

    // Helper method to extract roleFolders from headers
    private Set<RoleFolder> extractRolesFromHeader(Context ctx) {
        String rolesHeader = ctx.header("Test-UserFolder-Roles");
        Set<RoleFolder> roleFolders = new HashSet<>();
        if (rolesHeader != null) {
            String[] roleNames = rolesHeader.split(",");
            for (String roleName : roleNames) {
                RoleFolder roleFolder = new RoleFolder();
                roleFolder.setName(roleName.trim());
                roleFolders.add(roleFolder);
            }
        }
        return roleFolders;
    }

    // Helper method to extract subroles from headers
    private Set<SubRoleFolder> extractSubRolesFromHeader(Context ctx) {
        String subRolesHeader = ctx.header("Test-UserFolder-SubRoles");
        Set<SubRoleFolder> subRoleFolders = new HashSet<>();
        if (subRolesHeader != null) {
            String[] subRoleNames = subRolesHeader.split(",");
            for (String subRoleName : subRoleNames) {
                SubRoleFolder subRoleFolder = new SubRoleFolder();
                subRoleFolder.setName(subRoleName.trim());
                subRoleFolders.add(subRoleFolder);
            }
        }
        return subRoleFolders;
    }
}
