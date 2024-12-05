package org.persistence.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "folder")
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "parentFolder", fetch = FetchType.LAZY)
    private Set<FileData> files = new HashSet<>();

    @OneToMany(mappedBy = "parentFolder", fetch = FetchType.LAZY)
    private Set<Folder> subFolders = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_folder_id")
    private Folder parentFolder;

    @ManyToMany(mappedBy = "folders", fetch = FetchType.LAZY)
    @Column(name = "sub_roles")
    private Set<SubRole> subRoles = new HashSet<>();

    @OneToMany(mappedBy = "folder", fetch = FetchType.LAZY)
    private Set<PermissionMatrixSettings> permissionMatrixSettings = new HashSet<>();

    @ManyToMany(mappedBy = "folders", fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();

    public Folder(Set<FileData> files, Set<Folder> subFolders, Set<SubRole> subRoles, Folder parentFolder,
            Set<PermissionMatrixSettings> permissionMatrixSettings) {
        this.files = files;
        this.subFolders = subFolders;
        this.parentFolder = parentFolder;
        this.subRoles = subRoles;
        this.parentFolder = parentFolder;
        this.permissionMatrixSettings = permissionMatrixSettings;

        for (SubRole subRole : subRoles) {
            subRole.addFolder(this);
            if (!roles.contains(subRole.getRole())) {
                subRole.getRole().addSubFolder(this);
                roles.add(subRole.getRole());
            }
        }
    }

    public Folder(Set<FileData> files, Set<Folder> subFolders, Folder parentFolder,
            Set<PermissionMatrixSettings> permissionMatrixSettings) {
        this.files = files;
        this.subFolders = subFolders;
        this.parentFolder = parentFolder;
        this.permissionMatrixSettings = permissionMatrixSettings;
    }

    public Folder(Set<FileData> files, Set<Folder> subFolders, Folder parentFolder) {
        this.files = files;
        this.subFolders = subFolders;
        this.parentFolder = parentFolder;
    }

    public void addSubRole(SubRole subRole) {
        if (subRole == null) {
            return;
        }

        if (!this.subRoles.contains(subRole)) {
            this.subRoles.add(subRole);
            subRole.addFolder(this);
            addRole(subRole.getRole());
        }
    }

    public void addRole(Role role) {
        if (role == null) {
            return;
        }
        if (!roles.contains(role)) {
            roles.add(role);
            role.addSubFolder(this);
            for(SubRole subRole : role.getSubRoles()) {
                addSubRole(subRole);
            }
        }
    }

    public void addPermissionMatrixSettings(PermissionMatrixSettings permissionMatrixSettings) {
        if (permissionMatrixSettings == null) {
            return;
        }
        if (!this.permissionMatrixSettings.contains(permissionMatrixSettings)) {
            this.permissionMatrixSettings.add(permissionMatrixSettings);
        }
    }
}
