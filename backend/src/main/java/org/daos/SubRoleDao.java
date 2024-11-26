package org.daos;

import org.entities.SubRole;

import java.util.ArrayList;
import java.util.List;

public class SubRoleDao {

//    public SubRoleDao() {
//        this.subRoleList = createSubRoleList(); // Initialiserer listen af subroles
//    }

    private List<SubRole> subRoleList = new ArrayList<>();

    // Opretter en liste af subroles
    public List<SubRole> createSubRoleList() {
        List<SubRole> subRoleList = new ArrayList<>();
        subRoleList.add(new SubRole("Super Manager"));
        subRoleList.add(new SubRole("Department Manager"));
        subRoleList.add(new SubRole("Team Lead"));
        subRoleList.add(new SubRole("Project Manager"));
        subRoleList.add(new SubRole("Regional Manager"));
        return subRoleList;
    }

    // Hent alle subroles
    public List<SubRole> getAllSubRoles() {
        return subRoleList;
    }

    // Hent en subrole efter titel
    public SubRole getSubRoleByTitle(String title) {
        return subRoleList.stream()
                .filter(subRole -> subRole.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }

    // Tilføj en subrole
    public void addSubRole(SubRole subRole) {
        subRoleList.add(subRole);
    }

    // Fjern en subrole
    public void removeSubRole(String title) {
        subRoleList.removeIf(subRole -> subRole.getTitle().equals(title));
    }


}