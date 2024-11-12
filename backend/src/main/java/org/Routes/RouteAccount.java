package org.Routes;

import io.javalin.apibuilder.EndpointGroup;
import org.controller.AccountController;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.post;

public class RouteAccount {
    public class RouteUser {
        private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(false);
        private static AccountController accountController = new AccountController(emf);
        private static UserDAO userDAO = UserDAO.getInstance(emf);

        public EndpointGroup routesUser() {
            return () -> {
                path("/account", () -> {
                    post("/update_role", customLogger.handleExceptions(accountController.addRoleToUser()), Role.ADMIN);
                    get("/users", customLogger.handleExceptions(accountController.getAllUsers(userDAO)), Role.ADMIN);
                    get("/users/id", customLogger.handleExceptions(accountController.getAllUsers(userDAO)), Role.ADMIN);
                });
            };
        }
    }
}
