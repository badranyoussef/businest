package org.daos;

import lombok.Getter;
import org.dtos.AccountDTO;
import org.entities.Account;
import org.entities.Role;
import org.entities.SubRole;

import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    private SubRoleDao subRoleDao = new SubRoleDao();

    // Opretter en liste af Accounts med SubRoles
    public List<Account> createAccountList() {
        List<Account> accountList = new ArrayList<>();

        List<SubRole> subRoles = subRoleDao.createSubRoleList();

        // Her tilføjer vi subRoles til Account, og vi giver mulighed for at tilføje flere SubRoles senere
        accountList.add(new Account(1, "Alex Johnson", new Role("Company Manager"), new ArrayList<>(List.of(subRoles.get(0)))));
        accountList.add(new Account(2, "Jamie Smith", new Role("Employee")));
        accountList.add(new Account(3, "Taylor Brown", new Role("Employee")));
        accountList.add(new Account(4, "Morgan White", new Role("Company Manager"), new ArrayList<>(List.of(subRoles.get(0)))));
        accountList.add(new Account(5, "Casey Black", new Role("HR")));
        accountList.add(new Account(6, "Jordan Green", new Role("Employee")));
        accountList.add(new Account(7, "Riley Grey", new Role("Employee")));
        accountList.add(new Account(8, "Avery Gold", new Role("Company Manager"), new ArrayList<>(List.of(subRoles.get(0)))));
        accountList.add(new Account(9, "Cameron Blue", new Role("Employee")));
        accountList.add(new Account(10, "Dakota Silver", new Role("Employee")));

        return accountList;
    }

    private static AccountDAO instance;
    @Getter
    private List<Account> accounts = createAccountList();

    // Singleton mønster for at hente instansen af AccountDAO
    public static AccountDAO getInstance() {
        if (instance == null) {
            instance = new AccountDAO();
        }
        return instance;
    }

    // Hent en Account baseret på ID
    public AccountDTO getAccountById(int id) {
        Account account = accounts.get(id - 1); // fordi det er et index
        return new AccountDTO(account);
    }

    // Opdater Account baseret på AccountDTO
    public AccountDTO updateAccount(AccountDTO dto) {
        for (Account account : accounts) {
            if (account.getId() == dto.getId()) {
                account.updateRole(new Role(dto.getRole()));
                return new AccountDTO(account);
            }
        }
        return null;
    }

    // Hent alle Accounts
    public List<AccountDTO> getAllAccounts() {
        List<AccountDTO> dtoList = new ArrayList<>();
        for (Account account : accounts) {
            dtoList.add(new AccountDTO(account));
        }
        return dtoList;
    }
}