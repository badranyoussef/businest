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
