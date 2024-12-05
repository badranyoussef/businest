package org.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.entities.Company;
import org.entities.Role;
import org.entities.SubRole;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FolderDTO {
    private Long id;
    private String name;
    private Company company;
    private Role role;
    private SubRole subRole;
}
