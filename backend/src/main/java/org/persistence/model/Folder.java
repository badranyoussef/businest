package org.persistence.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Set<File> files;
    private Set<Folder> folders;
    private Set<SubRole> subRoles;
    private Folder parentFolder;
    private Set<PermissionMatrixSettings> permissionMatrixSettings;

    public Folder(Set<File> files, Set<Folder> folders, Set<SubRole> subRoles, Folder parentFolder, Set<PermissionMatrixSettings> permissionMatrixSettings) {
        this.files = files;
        this.folders = folders;
        this.subRoles = subRoles;
        this.parentFolder = parentFolder;
        this.permissionMatrixSettings = permissionMatrixSettings;
    }
}
