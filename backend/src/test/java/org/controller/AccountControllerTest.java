package org.controller;

import org.dto.AccountDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class AccountControllerTest {


    public static List<AccountDTO> createUserList() {
        List<AccountDTO> userList = new ArrayList<>();

        userList.add(new AccountDTO(1, "Alex Johnson", "Company Manager"));
        userList.add(new AccountDTO(2, "Jamie Smith", "Employee"));
        userList.add(new AccountDTO(3, "Taylor Brown", "Employee"));
        userList.add(new AccountDTO(4, "Morgan White", "Company Manager"));
        userList.add(new AccountDTO(5, "Casey Black", "Employee"));
        userList.add(new AccountDTO(6, "Jordan Green", "Employee"));
        userList.add(new AccountDTO(7, "Riley Grey", "Employee"));
        userList.add(new AccountDTO(8, "Avery Gold", "Company Manager"));
        userList.add(new AccountDTO(9, "Cameron Blue", "Employee"));
        userList.add(new AccountDTO(10, "Dakota Silver", "Employee"));

        return userList;
    }

    List accounts = createUserList();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void getAllAccounts() {
    }

    @org.junit.jupiter.api.Test
    void getAccountsById() {
    }

    @org.junit.jupiter.api.Test
    void updateAccount() {
    }
}