package org.folder;

import io.javalin.http.Context;

public interface ISecurityController {
    void authenticate(Context ctx);
    void authorizeRole(Context ctx, Role requiredRole);
    void authorizeTitle(Context ctx, CompanyTitle requiredTitle);
    void authorizeSubRole(Context ctx, SubRole requiredSubRole);

}
