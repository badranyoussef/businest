package org.entities;

import jakarta.persistence.*;
import lombok.*;

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
    Set<Folder> folders;
    Set<SubRole> subRoles;

    public Role(String name, Set<Folder> folders, Set<SubRole> subRoles) {
        this.name = name;
        this.folders = folders;
        this.subRoles = subRoles;
    }
}
