package org.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class Account {
    private int id;
    private String name;
    private Role role;
    private List<SubRole> subRoles;

    // Constructor med subRoles @todo denne kontruktør skaber udfordringer...
    /*public Account(int id, String name, Role role, List<SubRole> subRoles) {
        this.id = id;
        this.name = name;
        this.role = role;
        //this.subRoles = subRoles != null ? subRoles : new ArrayList<>();
        if (this.subRoles.isEmpty()) {
            this.subRoles.add(new SubRole("Default")); // Tilføj standard SubRole, hvis ingen findes
        }
    } */

    // Constructor uden subRoles
    public Account(int id, String name, Role role) {
        this(id, name, role, null); // Kalder den oprindelige constructor med null for subRoles
    }

    // Metode til at tilføje en SubRole
    public void addSubRole(SubRole subRole) {
        if (this.subRoles == null) {
            this.subRoles = new ArrayList<>();
        }
        this.subRoles.add(subRole);
    }

    // Opdater rolle
    public void updateRole(Role newRole) {
        this.role = newRole;
    }

    // Hent rollen som String
    public String getRoleAsString() {
        return this.role.toString();
    }
}