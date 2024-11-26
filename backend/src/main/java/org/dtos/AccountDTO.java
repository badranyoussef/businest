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

public class AccountDTO {
    private int id;
    private String name;
    private String role;
    private List<String> subRoles;

    public AccountDTO(int id, String name, String role, List<String> subRoles) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.subRoles = subRoles;
    }
}
