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
    private Role role;
    private List<SubRole> subRoles;


    public Account(int id, String name, Role role, List<SubRole> subRoles) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.subRoles = subRoles;
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

    public void addSubRole(SubRole subRole) {
        this.subRoles.add(subRole);
    }

    public void removeSubRole(SubRole subRole) {
        this.subRoles.remove(subRole);
    }
}