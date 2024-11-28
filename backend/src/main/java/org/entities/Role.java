package org.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Role {
    private String name;

    @ManyToMany
    Set<Folder> folders;
    @OneToMany
    Set<SubRole> subRoles;


    public Role(String name, Set<Folder> folders, Set<SubRole> subRoles) {
        this.name = name;
        this.folders = folders;
        this.subRoles = subRoles;
    }
}
