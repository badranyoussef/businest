package org.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.entity.Account;

import javax.management.relation.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AccountDTO {
    private int id;
    private String name;
    private String role;
    public AccountDTO(Account account) {
        this.id = account.getId();
        this.name = account.getName();
        this.role = account.getRoleAsString();
    }
}
