package org.daos;

import org.entities.Role;
import org.entities.SubRole;

import java.util.ArrayList;
import java.util.List;

public class RoleDAO {
    private final List<Role> roles;

    public RoleDAO() {
        this.roles = createRoleList();
    }

    // Opretter en liste af roller
    public List<Role> createRoleList() {
        List<Role> roles = new ArrayList<>();

        roles.add(new Role("Company Manager", List.of(
                new SubRole("Budget Manager"),
                new SubRole("Operations Coordinator"),
                new SubRole("Team Leader")
        )));

        roles.add(new Role("HR", List.of(
                new SubRole("Recruitment Specialist"),
                new SubRole("Employee Relations Manager"),
                new SubRole("Training Coordinator")
        )));

        roles.add(new Role("Developer", List.of(
                new SubRole("Frontend Developer"),
                new SubRole("Backend Developer"),
                new SubRole("Fullstack Developer"),
                new SubRole("Mobile App Developer")
        )));

        roles.add(new Role("Marketing", List.of(
                new SubRole("Content Strategist"),
                new SubRole("SEO Specialist"),
                new SubRole("Social Media Manager"),
                new SubRole("Brand Manager")
        )));

        roles.add(new Role("Sales", List.of(
                new SubRole("Account Manager"),
                new SubRole("Customer Success Specialist"),
                new SubRole("Sales Representative"),
                new SubRole("Business Development Manager")
        )));

        roles.add(new Role("Employee", List.of(
                new SubRole("Intern"),
                new SubRole("Junior Staff"),
                new SubRole("Senior Staff")
        )));

        return roles;
    }

    // Returnerer alle roller
    public List<Role> getAllRoles() {
        return new ArrayList<>(roles); // Returner en kopi for at undgå uforudsete ændringer
    }
}
