package org.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.entities.SubRole;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
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
