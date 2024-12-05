package org.persistence.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "permission_matrix_settings")
public class PermissionMatrixSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne()
    @JoinColumn(name = "folder_id")
    private Folder folder;

    @ManyToOne()
    @JoinColumn(name = "sub_role_id")
    private SubRole subRole;

    @ManyToOne()
    @JoinColumn(name = "permissions_id")
    private Permissions permissions;

    public PermissionMatrixSettings(Folder folder, SubRole subRole, Permissions permissions) {
        this.folder = folder;
        this.subRole = subRole;
        this.permissions = permissions;
        role = subRole.getRole();
        folder.addPermissionMatrixSettings(this);
        folder.addSubRole(subRole);
    }
}
