package org.dtos;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.folder.Role;
import org.folder.SubRole;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FolderDTO {
    private String id;
    private String name;
    private String company;
    private Role role;
    private SubRole subRole;
}

