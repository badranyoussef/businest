package org.folder;

import io.javalin.http.Context;

public interface ISecurityController {
    void authenticate(Context ctx);
    void authorizeRole(Context ctx, Role requiredRole);
}
