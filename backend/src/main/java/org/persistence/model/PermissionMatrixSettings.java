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
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "folder_id")
    private Folder folder;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "sub_role_id")
    private SubRole subRole;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "permissions_id")
    private Permissions permissions;

    public PermissionMatrixSettings(Folder folder, SubRole subRole, Permissions permissions) {
        this.role = subRole.getRole();
        this.folder = folder;
        this.subRole = subRole;
        this.permissions = permissions;
    }
}
