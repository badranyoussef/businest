package org.entities;

import jakarta.persistence.*;
import lombok.*;
import org.folder.UserFolder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roleFolders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleFolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Builder.Default
    @ManyToMany(mappedBy = "roleFolders")
    private Set<UserFolder> userFolders = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
}
