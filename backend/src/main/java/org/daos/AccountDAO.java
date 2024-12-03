package org.daos;

import org.entities.Account;
import org.entities.Role;
import org.entities.SubRole;

import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    private List<Account> accountList;
    private RoleDAO roleDAO = new RoleDAO();  // RoleDAO som intern komponent
    private List<Role> roles = roleDAO.createRoleList();


    // Constructor
    public AccountDAO() {
        this.roleDAO = new RoleDAO(); // Initialiserer RoleDAO internt
        this.accountList = createAccountList(); // Initialiserer listen af accounts
    }

    // Opretter en liste af Accounts med SubRoles
    public List<Account> createAccountList() {
        List<Account> accountList = new ArrayList<>();

        accountList.add(new Account(1, "Alex Johnson", new ArrayList<>(List.of(
                new Role("Company Manager", new ArrayList<>(List.of(
                        new SubRole("Budget Manager"),
                        new SubRole("Operations Manager")
                ))),
                new Role("ROLETEST", new ArrayList<>(List.of(
                        new SubRole("TEST SUBROLE"),
                        new SubRole("TEST SUBROLE")
                )))
        ))));

        accountList.add(new Account(2, "Jamie Smith", new ArrayList<>(List.of(
                new Role("Developer", new ArrayList<>(List.of(
                        new SubRole("Frontend Developer"),
                        new SubRole("Backend Developer")
                )))
        ))));

        accountList.add(new Account(3, "Taylor Brown", new ArrayList<>(List.of(
                new Role("Employee", new ArrayList<>(List.of(
                        new SubRole("Intern"),
                        new SubRole("Support Staff")
                )))
        ))));

        accountList.add(new Account(4, "Morgan White", new ArrayList<>(List.of(
                new Role("Company Manager", new ArrayList<>(List.of(
                        new SubRole("Strategic Planner"),
                        new SubRole("Department Manager")
                )))
        ))));

        accountList.add(new Account(5, "Casey Black", new ArrayList<>(List.of(
                new Role("HR", new ArrayList<>(List.of(
                        new SubRole("Recruitment Specialist"),
                        new SubRole("Training Coordinator")
                )))
        ))));

        accountList.add(new Account(6, "Jordan Green", new ArrayList<>(List.of(
                new Role("Marketing", new ArrayList<>(List.of(
                        new SubRole("SEO Specialist"),
                        new SubRole("Content Strategist")
                )))
        ))));

        accountList.add(new Account(7, "Riley Grey", new ArrayList<>(List.of(
                new Role("Employee", new ArrayList<>(List.of(
                        new SubRole("Customer Support"),
                        new SubRole("Office Assistant")
                )))
        ))));

        accountList.add(new Account(8, "Avery Gold", new ArrayList<>(List.of(
                new Role("Developer", new ArrayList<>(List.of(
                        new SubRole("Mobile Developer"),
                        new SubRole("DevOps Engineer")
                )))
        ))));

        accountList.add(new Account(9, "Cameron Blue", new ArrayList<>(List.of(
                new Role("Employee", new ArrayList<>(List.of(
                        new SubRole("Warehouse Staff"),
                        new SubRole("Logistics Coordinator")
                )))
        ))));

        accountList.add(new Account(10, "Dakota Silver", new ArrayList<>(List.of(
                new Role("HR", new ArrayList<>(List.of(
                        new SubRole("Benefits Coordinator"),
                        new SubRole("Employee Relations Specialist")
                )))
        ))));

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
            account.setRoles(updatedAccount.getRoles());
        }
        return account;
    }

    public boolean deleteAccount(int id) {
        return accounts.removeIf(a -> a.getId() == id);
    }

    // Metode til at tilføje en subrole til en account
    public void addSubRoleToAccount(int accountId, Role role, SubRole subRole) {
        Account account = getAccountById(accountId);
        if (account != null) {
            account.getRoles().stream()
                    .filter(r -> r.getTitle().equals(role.getTitle())) // Filtrer på roleName
                    .findFirst()  // Find den første rolle der matcher
                    .ifPresent(r -> {
                        // Hvis rollen findes, tilføj SubRole til denne rolle
                        r.getSubRoles().add(subRole);
                    });
        } else {
            throw new IllegalArgumentException("Account with ID " + accountId + " not found");
        }
    }

    // Metode til at fjerne en subrole fra en account
    public void removeSubRoleFromAccount(int accountId, String subRoleTitle) {
        Account account = getAccountById(accountId);
        if (account != null) {
            //account.getSubRoles().removeIf(subRole -> subRole.getTitle().equals(subRoleTitle));
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