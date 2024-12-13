package org.folder;

import io.javalin.http.Context;
import org.entities.CompanyTitle;
import org.entities.RoleFolder;
import org.entities.SubRoleFolder;

public interface ISecurityController {
    void authenticate(Context ctx);
    void authorizeRole(Context ctx, RoleFolder requiredRoleFolder);
    void authorizeTitle(Context ctx, CompanyTitle requiredTitle);
    void authorizeSubRole(Context ctx, SubRoleFolder requiredSubRoleFolder);

}
