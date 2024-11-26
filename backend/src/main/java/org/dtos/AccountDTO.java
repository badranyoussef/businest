package org.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.entities.Account;
import org.entities.SubRole;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AccountDTO {
    private int id;
    private String name;
    private String role;
    private List<SubRole> subRoles;

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.name = account.getName();
        this.role = account.getRoleAsString();
        this.subRoles = account.getSubRoles();
    }
}
