package org.daos;

import org.dtos.AccountDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountDAOTest {
    static AccountDAO dao;

    @BeforeAll
    static void setUp() {
        dao = new AccountDAO();
        //List<Account> accounts = dao.createAccountList();
    }

    @Test
    void getAccountById() {
        int expected = 2;
        String expectedName = "Jamie Smith";
        AccountDTO dto = dao.getAccountById(2);
        int actual = dto.getId();
        assertEquals(expected, actual);
        assertEquals(expectedName, dto.getName());
    }

    @Test
    void updateAccount() {

        AccountDTO existingAccount = new AccountDTO(dao.getAccounts().get(0)); //henter en account
        existingAccount.setRole("HR"); //ændre rollen
        AccountDTO dto = dao.updateAccount(existingAccount); // opdaterer rollen

        String expected = dto.getRole();
        String actual = dao.getAccounts().get(0).getRoleAsString();

        assertEquals(expected, actual);
        
    }

    @Test
    void getAllAccounts() {
        int expected = 10;
        int actual = dao.getAllAccounts().size();

        assertEquals(expected, actual);


    }
}