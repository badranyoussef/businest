package org.dao;
import lombok.Getter;
import org.dto.AccountDTO;
import org.entity.Account;
import org.entity.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccountDAO {

    public static List<Account> createAccountList() {
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account(1, "Alex Johnson", Role.COMPANY_MANAGER));
        accountList.add(new Account(2, "Jamie Smith", Role.EMPLOYEE));
        accountList.add(new Account(3, "Taylor Brown", Role.EMPLOYEE));
        accountList.add(new Account(4, "Morgan White", Role.COMPANY_MANAGER));
        accountList.add(new Account(5, "Casey Black", Role.HR));
        accountList.add(new Account(6, "Jordan Green", Role.EMPLOYEE));
        accountList.add(new Account(7, "Riley Grey", Role.EMPLOYEE));
        accountList.add(new Account(8, "Avery Gold", Role.COMPANY_MANAGER));
        accountList.add(new Account(9, "Cameron Blue", Role.EMPLOYEE));
        accountList.add(new Account(10, "Dakota Silver", Role.EMPLOYEE));

        return accountList;
    }

    private static AccountDAO instance;

    private List<Account> accounts = createAccountList();

    public static AccountDAO getInstance() {
        if (instance == null) {
            instance = new AccountDAO();
        }
        return instance;
    }

    public AccountDTO getAccountById(int id) {
        Account account = accounts.get(id - 1); //because it is a index
        AccountDTO dto = new AccountDTO(account);
        return dto;
    }

    public AccountDTO updateAccount(AccountDTO dto) {
        for (Account account : accounts) {
            if (account.getId() == dto.getId()) {
                return new AccountDTO(account);
            }
        }
        return null;
    }
    
    public List<AccountDTO> getAllAccounts(){
        List<AccountDTO> dtoList = new ArrayList<>();
        for (Account account: accounts) {
            dtoList.add(new AccountDTO(account));
        }
        return dtoList;
    }
}