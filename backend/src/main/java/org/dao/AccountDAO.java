package org.dao;
import org.entity.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccountDAO {
    private static AccountDAO instance;
    private static final List<AccountDTO> accounts = createAccountList();
    public static AccountDAO getInstance() {
        if (instance == null) {
            instance = new AccountDAO();
        }
        return instance;
    }
    public static List<AccountDTO> getAccounts() {
        return accounts;
    }

    public static AccountDTO getAccountById(int id) {
        AccountDTO account = accounts.get(id-1); //because it is a index
        return account;
    }

    public static Account updateAccount(AccountDTO accountDTO) {
        accounts.set(accountDTO.getId, )
        return null;
    }

    public static List<AccountDTO> createAccountList() {

        accounts.add(new AccountDTO(1, "Alex Johnson", "Company Manager"));
        accounts.add(new AccountDTO(2, "Jamie Smith", "Employee"));
        accounts.add(new AccountDTO(3, "Taylor Brown", "Employee"));
        accounts.add(new AccountDTO(4, "Morgan White", "Company Manager"));
        accounts.add(new AccountDTO(5, "Casey Black", "Employee"));
        accounts.add(new AccountDTO(6, "Jordan Green", "Employee"));
        accounts.add(new AccountDTO(7, "Riley Grey", "Employee"));
        accounts.add(new AccountDTO(8, "Avery Gold", "Company Manager"));
        accounts.add(new AccountDTO(9, "Cameron Blue", "Employee"));
        accounts.add(new AccountDTO(10, "Dakota Silver", "Employee"));

        return userList;
    }

    List accounts = createUserList();
}
