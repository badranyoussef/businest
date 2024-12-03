package org.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "sub_role")
public class SubRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String name;
    private Role role;

    public SubRole(String name, Role role) {
        this.name = name;
        this.role = role;
    }
}
