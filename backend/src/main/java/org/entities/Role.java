package org.entities;

import jakarta.persistence.*;
import lombok.*;
import org.entities.Company;
import org.folder.User;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Builder.Default
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
}
