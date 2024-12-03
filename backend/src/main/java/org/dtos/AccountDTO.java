package org.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.entities.Account;
import org.entities.Role;
import org.entities.SubRole;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class AccountDTO {
    private int id;
    private String name;
    private List<Role> roles;

    public AccountDTO(int id, String name, List<Role> roles) {
        this.id = id;
        this.name = name;
        this.roles = roles;
    }
}
