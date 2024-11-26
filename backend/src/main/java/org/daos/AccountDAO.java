package org.daos;

import lombok.Getter;
import org.dtos.AccountDTO;
import org.entities.Account;
import org.entities.Role;
import org.entities.SubRole;

import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    private List<Account> accountList;
    private SubRoleDao subRoleDAO = new SubRoleDao();  // SubRoleDAO som intern komponent
    private List<SubRole> subRoles = subRoleDAO.createSubRoleList();


    // Constructor
    public AccountDAO() {
        this.subRoleDAO = new SubRoleDao(); // Initialiserer SubRoleDAO internt
        this.accountList = createAccountList(); // Initialiserer listen af accounts
    }

    // Opretter en liste af Accounts med SubRoles
    public List<Account> createAccountList() {
        List<Account> accountList = new ArrayList<>();

        // Her tilføjer vi subRoles til Account, og vi giver mulighed for at tilføje flere SubRoles senere
        accountList.add(new Account(1, "Alex Johnson", new Role("Company Manager"), new ArrayList<>(List.of(subRoles.get(0)))));
        accountList.add(new Account(2, "Jamie Smith", new Role("Employee"), new ArrayList<>()));
        accountList.add(new Account(3, "Taylor Brown", new Role("Employee"), new ArrayList<>()));
        accountList.add(new Account(4, "Morgan White", new Role("Company Manager"), new ArrayList<>(List.of(subRoles.get(0)))));
        accountList.add(new Account(5, "Casey Black", new Role("HR"), new ArrayList<>()));
        accountList.add(new Account(6, "Jordan Green", new Role("Employee"), new ArrayList<>()));
        accountList.add(new Account(7, "Riley Grey", new Role("Employee"), new ArrayList<>()));
        accountList.add(new Account(8, "Avery Gold", new Role("Company Manager"), new ArrayList<>(List.of(subRoles.get(0)))));
        accountList.add(new Account(9, "Cameron Blue", new Role("Employee"), new ArrayList<>()));
        accountList.add(new Account(10, "Dakota Silver", new Role("Employee"), new ArrayList<>()));

        return accountList;
    }

    private static AccountDAO instance;

    private List<Account> accounts = createAccountList();

    // Singleton mønster for at hente instansen af AccountDAO
    public static AccountDAO getInstance() {
        if (instance == null) {
            instance = new AccountDAO();
        }
        return instance;
    }

    public List<Account> getAllAccounts() {
        return accounts;
    }

    public Account getAccountById(int id) {
        return accounts.stream().filter(a -> a.getId() == id).findFirst().orElse(null);
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public Account updateAccount(Account updatedAccount) {
        Account account = getAccountById(updatedAccount.getId());
        if (account != null) {
            account.setName(updatedAccount.getName());
            account.setRole(updatedAccount.getRole());
            account.setSubRoles(updatedAccount.getSubRoles());
        }
        return account;
    }

    public boolean deleteAccount(int id) {
        return accounts.removeIf(a -> a.getId() == id);
    }

    // Metode til at tilføje en subrole til en account
    public void addSubRoleToAccount(int accountId, SubRole subRole) {
        Account account = getAccountById(accountId);
        if (account != null) {
            account.getSubRoles().add(subRole);
        } else {
            throw new IllegalArgumentException("Account with ID " + accountId + " not found");
        }
    }

    // Metode til at fjerne en subrole fra en account
    public void removeSubRoleFromAccount(int accountId, String subRoleTitle) {
        Account account = getAccountById(accountId);
        if (account != null) {
            account.getSubRoles().removeIf(subRole -> subRole.getTitle().equals(subRoleTitle));
        } else {
            throw new IllegalArgumentException("Account with ID " + accountId + " not found");
        }
    }



//    // Hent en Account baseret på ID
//    public AccountDTO getAccountById(int id) {
//        Account account = accounts.get(id - 1); // fordi det er et index
//        return new AccountDTO(account);
//    }
//
//    // Opdater Account baseret på AccountDTO
//    public AccountDTO updateAccount(AccountDTO dto) {
//        for (Account account : accounts) {
//            if (account.getId() == dto.getId()) {
//                account.updateRole(new Role(dto.getRole()));
//                return new AccountDTO(account);
//            }
//        }
//        return null;
//    }
//
//    // Hent alle Accounts
//    public List<AccountDTO> getAllAccounts() {
//        List<AccountDTO> dtoList = new ArrayList<>();
//        for (Account account : accounts) {
//            dtoList.add(new AccountDTO(account));
//        }
//        return dtoList;
//    }
//
//    public List<Role> getAllRoles() {
//        List<Role> dtoList = new ArrayList<>();
//        for (Account account : accounts) {
//            dtoList.add(account.getRole());
//        }
//        return dtoList;
//    }
}