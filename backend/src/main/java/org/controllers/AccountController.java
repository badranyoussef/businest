package org.controllers;

import java.util.List;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import org.daos.AccountDAO;
import org.dtos.AccountDTO;

public class AccountController {

    AccountDAO dao;

    public Handler getAllAccounts(AccountDAO dao) {
        return ctx -> {
            List<AccountDTO> accounts = dao.getAllAccounts();
            if (accounts.isEmpty()) {
                //throw new ApiException(HttpStatus.NOT_FOUND.getCode(), "No account were found.", timestamp);
                System.out.println("No accounts found");
            } else {
                ctx.status(HttpStatus.OK).json(accounts);
            }
        };
    }

    public Handler getAccountsById(AccountDAO dao) {
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            AccountDTO account = dao.getAccountById(id);
            if (account == null ) {
                //throw new ApiException(HttpStatus.NOT_FOUND.getCode(), "No users were found.", timestamp);
                System.out.println("No user found");
            } else {
                ctx.status(HttpStatus.OK).json(account);
            }
        };
    }

    public Handler updateAccount(AccountDAO dao) {
        return ctx -> {
            AccountDTO account = ctx.bodyAsClass(AccountDTO.class);
            AccountDTO updatedAccount = dao.updateAccount(account);
            if (updatedAccount == null ) {
                //throw new ApiException(HttpStatus.NOT_FOUND.getCode(), "No users were found.", timestamp);
                System.out.println("something went wrong, try to update account again");
            } else {
                ctx.status(HttpStatus.OK).json(account);
            }
        };
    }
}
