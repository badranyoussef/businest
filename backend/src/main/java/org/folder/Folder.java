package org.folder;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "folders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Folder {
    @Id
    private String id;

    private String name;

    private String company;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @PrePersist
    public void prePersist() {
        if (role == null) {
            role = Role.GUEST;
        }
    }
}
