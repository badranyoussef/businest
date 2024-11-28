package org.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "permission_matrix_settings")
public class PermissionMatrixSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    @ManyToOne
    @JoinColumn(name = "sub_role_id")
    private SubRole subRole;

    @ManyToOne
    @JoinColumn(name = "permissions_id")
    private Permissions permissions;

    public PermissionMatrixSettings(Role role, Folder folder, SubRole subRole, Permissions permissions) {
        this.role = role;
        this.folder = folder;
        this.subRole = subRole;
        this.permissions = permissions;
    }
}
