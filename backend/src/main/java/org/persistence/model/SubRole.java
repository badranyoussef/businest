package org.persistence.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "subrole")
public class SubRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sub_role_folder", joinColumns = @JoinColumn(name = "sub_role_id"), inverseJoinColumns = @JoinColumn(name = "folder_id"))
    private Set<Folder> folders = new HashSet<>();

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    public SubRole(String name, Role role) {
        this.name = name;
        this.role = role;
    }
    public SubRole(String name) {
        this.name = name;
    }

    public void addFolder(Folder folder) {
        if( folder == null ) {
            return;
        }

        if (!this.folders.contains(folder)) {
            this.folders.add(folder);
            folder.addSubRole(this);
        }
    }
}
