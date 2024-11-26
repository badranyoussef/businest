package org.folder;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;



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

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "sub_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private SubRole subRole;
    @PrePersist
    public void prePersist() {
        if (role == null) {
            role = Role.GUEST;
        }
        if (subRole == null) {
            subRole = SubRole.LOW;
        }
    }
}
