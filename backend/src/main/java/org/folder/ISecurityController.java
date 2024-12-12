package org.folder;

import io.javalin.http.Context;
import org.entities.CompanyTitle;
import org.entities.Role;
import org.entities.SubRole;

public interface ISecurityController {
    void authenticate(Context ctx);
    void authorizeRole(Context ctx, Role requiredRole);
    void authorizeTitle(Context ctx, CompanyTitle requiredTitle);
    void authorizeSubRole(Context ctx, SubRole requiredSubRole);

}
