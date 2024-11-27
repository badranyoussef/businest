package org.routes;

import io.javalin.apibuilder.EndpointGroup;
import org.controllers.AccountController;
import org.controllers.RoleController;
import org.daos.AccountDAO;
import org.daos.RoleDAO;
import org.daos.SubRoleDAO;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.post;

public class RouteAccount {

    private static final AccountDAO accountDAO = new AccountDAO();
    private static final RoleDAO roleDAO = new RoleDAO();
    private static final SubRoleDAO subRoleDAO = new SubRoleDAO();
    private static final AccountController accountController = new AccountController(accountDAO);
    private static final RoleController roleController = new RoleController(subRoleDAO,roleDAO);

    //private static AccountController accountController = new AccountController();
        //private static AccountDAO accountDAO = new AccountDAO();

        public EndpointGroup routesUser() {
            return () -> {
                path("/account", () -> {
                    put("/update_role", accountController.updateAccount);
                    get("/", accountController.getAllAccounts);
                    get("/roles_subroles", roleController.getRolesAndSubRoles);
                    get("/{id}", accountController.getAccountById);
                    post("/", accountController.createAccount);
                });
            };
        }
}
