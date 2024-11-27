package org.daos;

import org.entities.Role;

import java.util.ArrayList;
import java.util.List;

public class RoleDAO {
    private final List<Role> roles;

    public RoleDAO() {
        this.roles = createRoleList();
    }

    // Opretter en liste af roller
    private List<Role> createRoleList() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("Company Manager"));
        roles.add(new Role("Employee"));
        roles.add(new Role("HR"));
        roles.add(new Role("Developer"));
        return roles;
    }

    // Returnerer alle roller
    public List<Role> getAllRoles() {
        return new ArrayList<>(roles); // Returner en kopi for at undgå uforudsete ændringer
    }
}
