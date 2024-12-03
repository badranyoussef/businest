package org.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Role {
    private String title;
    private List<SubRole> subRoles;

    @Override
    public String toString() {
        return title;
    }

    public void addSubRole(SubRole subRole) {
        this.subRoles.add(subRole);
    }

    public void removeSubRole(SubRole subRole) {
        this.subRoles.remove(subRole);
    }
}
