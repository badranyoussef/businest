package org.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import org.persistence.model.Role;

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

    @ManyToOne
    private Role role;

    public SubRole(String name, Role role) {
        this.name = name;
        this.role = role;
    }
}
