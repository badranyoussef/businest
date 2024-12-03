package org.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.entities.Permissions1;

@NoArgsConstructor
@AllArgsConstructor
public class PermissionsDTO {

    private String name;

    private String description;

    private String code;

    public PermissionsDTO(Permissions1 perm) {
        this.name = perm.getName();
        this.description = perm.getDescription();
        this.code = perm.getCode();
    }
}
