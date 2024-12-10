package org.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private int id;
    private String name;
    private List<Role> roles;


    public Account(int id, String name, List<Role> roles) {
        this.id = id;
        this.name = name;
        this.roles = roles;
    }


    // Constructor med en tom arraylist af subroles
//    public Account(int id, String name, Role role) {
//        this(id, name, role, new ArrayList<>()); // Kalder den oprindelige constructor med null for subRoles
//    }

//    public List<SubRole> getSubRoles() {
//        return subRoles;
//    }
//
//    public void setSubRoles(List<SubRole> subRoles) {
//        this.subRoles = subRoles;
//    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }
}