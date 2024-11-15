package org.routes;

import io.javalin.apibuilder.EndpointGroup;
import org.controllers.AccountController;
import org.daos.AccountDAO;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.post;

public class RouteAccount {
    public class RouteUser {
        private static AccountController accountController = new AccountController();
        private static AccountDAO accountDAO = new AccountDAO();

        public EndpointGroup routesUser() {
            return () -> {
                path("/account", () -> {
                    put("/update_role", accountController.updateAccount(accountDAO));
                    get("/", accountController.getAllAccounts(accountDAO));
                    get("/id", accountController.getAccountsById(accountDAO));
                });
            };
        }
    }
}
