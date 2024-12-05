package org.persistence.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_folder", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "folder_id"))
    Set<Folder> folders = new HashSet<>();

    @OneToMany(mappedBy = "role")
    Set<SubRole> subRoles = new HashSet<>();

    public Role(String name, Set<Folder> folders, Set<SubRole> subRoles) {
        this.name = name;
        this.folders = folders;
        this.subRoles = subRoles;
    }
    public Role(String name, Set<Folder> folders) {
        this.name = name;
        this.folders = folders;
    }    
    public Role(String name) {
        this.name = name;
    }

    public void addSubrole(SubRole subRole) {
        if( subRole == null ) {
            return;
        }

        if (!this.subRoles.contains(subRole)) {
            this.subRoles.add(subRole);
            subRole.setRole(this);
        }
    }

    public void addSubFolder(Folder folder) {
        if( folder == null ) {
            return;
        }

        if (!this.folders.contains(folder)) {
            this.folders.add(folder);
            folder.addRole(this);
        }
    }
}
