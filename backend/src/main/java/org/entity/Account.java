package org.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Account {
    private int id;
    private String name;
    private Role role;

    public void updateRole(Role role) {
        this.role = role;
    }
    public String getRoleAsString() {
        return role.toString();
    }
}
