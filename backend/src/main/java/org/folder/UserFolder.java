package org.folder;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.entities.Company;
import org.entities.CompanyTitle;
import org.entities.RoleFolder;
import org.entities.SubRoleFolder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "userFolders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "company_title", nullable = true)
    private CompanyTitle companyTitle;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleFolder> roleFolders = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_sub_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_role_id")
    )
    private Set<SubRoleFolder> subRoleFolders = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

}
