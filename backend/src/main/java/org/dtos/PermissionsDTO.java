package org.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.entities.Permissions;
import org.entities.SubRole;

@NoArgsConstructor
@AllArgsConstructor
public class PermissionsDTO {

    private String name;

    private String description;

    private String code;

    public PermissionsDTO(Permissions perm) {
        this.name = perm.getName();
        this.description = perm.getDescription();
        this.code = perm.getCode();
    }
}
