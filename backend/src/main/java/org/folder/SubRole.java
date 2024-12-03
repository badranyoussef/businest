package org.folder;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.entities.Company;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sub_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "subRoles")
    private Set<User> users = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
