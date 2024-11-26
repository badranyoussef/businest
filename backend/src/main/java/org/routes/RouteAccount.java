package org.routes;

import io.javalin.apibuilder.EndpointGroup;
import org.controllers.AccountController;
import org.daos.AccountDAO;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.post;

public class RouteAccount {

    private static final AccountDAO accountDAO = new AccountDAO();
    private static final AccountController accountController = new AccountController(accountDAO);

    //private static AccountController accountController = new AccountController();
        //private static AccountDAO accountDAO = new AccountDAO();

        public EndpointGroup routesUser() {
            return () -> {
                path("/account", () -> {
                    put("/update_role", accountController.updateAccount);
                    get("/", accountController.getAllAccounts);
                    get("/{id}", accountController.getAccountById);
                    post("/", accountController.createAccount);
                });
            };
        }
}
