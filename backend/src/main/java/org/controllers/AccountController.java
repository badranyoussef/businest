package org.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import org.daos.AccountDAO;
import org.dtos.AccountDTO;
import org.entities.Account;
import org.entities.Role;
import org.entities.SubRole;

public class AccountController {


    private AccountDAO dao;

    // Constructor tager AccountDAO som parameter
    public AccountController(AccountDAO dao) {
        this.dao = dao;
    }

    // Get all accounts
    public Handler getAllAccounts = ctx -> {
        var accounts = dao.getAllAccounts().stream()
                .map(a -> new AccountDTO(
                        a.getId(),
                        a.getName(),
                        a.getRole().getTitle(),
                        a.getSubRoles().stream().map(SubRole::getTitle).collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
        ctx.json(accounts);
    };

    public Handler createAccount = ctx -> {
        AccountDTO dto = ctx.bodyAsClass(AccountDTO.class);  // Deserialiserer indholdet af requesten
        Account account = new Account(
                dto.getId(),
                dto.getName(),
                new Role(dto.getRole()),
                dto.getSubRoles().stream()
                        .map(subRole -> new SubRole(subRole)) // dto.getSubRoles() er en liste af titles
                        .collect(Collectors.toList())
        );

        // Tilføj ny account til DAO (eller en liste i dit tilfælde)
        dao.addAccount(account);  // Du skal implementere denne metode i din DAO

        ctx.status(201).json(account);  // Returnerer den nyoprettede account med 201 status
    };


    // Get account by ID
    public Handler getAccountById = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Account account = dao.getAccountById(id);
        if (account == null) {
            ctx.status(404).result("Account not found");
        } else {
            AccountDTO dto = new AccountDTO(
                    account.getId(),
                    account.getName(),
                    account.getRole().getTitle(),
                    account.getSubRoles().stream().map(SubRole::getTitle).collect(Collectors.toList())
            );
            ctx.json(dto);
        }
    };

    // Update account (e.g., role and subroles)
    public Handler updateAccount = ctx -> {
        AccountDTO dto = ctx.bodyAsClass(AccountDTO.class);
        Account account = dao.getAccountById(dto.getId());
        if (account != null) {
            account.setName(dto.getName());
            account.setRole(new Role(dto.getRole()));
            account.setSubRoles(dto.getSubRoles().stream().map(SubRole::new).collect(Collectors.toList()));
            dao.updateAccount(account);
            ctx.status(200).result("Account updated");
        } else {
            ctx.status(403).result("Account not found");
        }
    };



//    AccountDAO dao;
//
//    public Handler getAllAccounts(AccountDAO dao) {
//        return ctx -> {
//            List<AccountDTO> accounts = dao.getAllAccounts();
//            if (accounts.isEmpty()) {
//                //throw new ApiException(HttpStatus.NOT_FOUND.getCode(), "No account were found.", timestamp);
//                System.out.println("No accounts found");
//            } else {
//                ctx.status(HttpStatus.OK).json(accounts);
//            }
//        };
//    }
//
//    public Handler getAccountById() {
//        return ctx -> {
//            int id = Integer.parseInt(ctx.pathParam("id"));
//            Account account = dao.getAccountById(id);
//            if (account == null ) {
//                //throw new ApiException(HttpStatus.NOT_FOUND.getCode(), "No users were found.", timestamp);
//                System.out.println("No user found");
//            } else {
//                ctx.status(HttpStatus.OK).json(account);
//            }
//        };
//    }
//
//    public Handler updateAccount(AccountDAO dao) {
//        return ctx -> {
//            AccountDTO account = ctx.bodyAsClass(AccountDTO.class);
//            AccountDTO updatedAccount = dao.updateAccount(account);
//            if (updatedAccount == null ) {
//                //throw new ApiException(HttpStatus.NOT_FOUND.getCode(), "No users were found.", timestamp);
//                System.out.println("something went wrong, try to update account again");
//            } else {
//                ctx.status(HttpStatus.OK).json(account);
//            }
//        };
//    }

    // Handler til at hente alle roller
    /*public Handler getAllRoles(AccountDAO accountDAO) {
        return ctx -> {
            List<AccountDTO> roles = accountDAO.getAllRoles(); // Hent alle roller fra DAO
            if (roles.isEmpty()) {
                System.out.println("No roles found");
                ctx.status(HttpStatus.NOT_FOUND).result("No roles found");
            } else {
                ctx.status(HttpStatus.OK).json(roles);
            }
        };
    }

     */
}
