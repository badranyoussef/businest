package org.controller;

import java.util.List;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;

public class AccountController {

    private Handler

    public Handler getAllUsers(UserDAO dao) {
        return ctx -> {
            List<User> users = dao.getAllUsers();
            if (users.isEmpty()) {
                throw new ApiException(HttpStatus.NOT_FOUND.getCode(), "No users were found.", timestamp);
            } else {
                ctx.status(HttpStatus.OK).json(users);
            }
        };
    }

}
