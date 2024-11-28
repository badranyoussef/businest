package org.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.folder.Role;
import org.folder.SubRole;

@Entity
@Table(name = "folders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Folder {

    @Id
    @Column(name = "folder_id", nullable = false)
    private String id;

    @Column(name = "folder_name", nullable = false)
    private String name;

    @Column(name = "company", nullable = false)
    private String company;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "sub_role_id")
    private SubRole subRole;

    @PrePersist
    public void prePersist() {
        if (role == null) {
            role = null; // Or set a default
        }
        if (subRole == null) {
            subRole = null; // Or set a default
        }
    }
}
