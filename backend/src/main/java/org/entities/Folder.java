package org.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import org.persistence.model.File;

/*
FROM classDiagram:
 class Folder {
    - String name
    - List<File> files
    - List<Folder> folders
    - List<Role> subRoles
    - Folder parentFolder
    - List<PermissionMatrixSettings> permissionMatrixSettings
    + addFile(File file)
    + addFolder(Folder folder)
    + removeFile(File file)
    + removeFolder(Folder folder)
    + getFiles()
    + getFolders()
    + getName()
    + setName(String name)
    + getParentFolder()
    + setParentFolder(Folder parentFolder)
}
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Folder {
    private String name;
    private List<File> files;
    private List<Folder> folders;
    private List<SubRole> subRoles;
    private Folder parentFolder;
    private List<PermissionMatrixSettings> permissionMatrixSettings;

    public void addFile(File file) {
        // files.add(file);
    }

    public void addFolder(Folder folder) {
        // folders.add(folder);
    }

    public void removeFile(File file) {
        // files.remove(file);
    }

    public void removeFolder(Folder folder) {
        // folders.remove(folder);
    }

    public void getFiles() {
        // return files;
    }

    public void getFolders() {
        // return folders;
    }

    public void getName() {
        // return name;
    }

    public void setName(String name) {
        // this.name = name;
    }

    public void getParentFolder() {
        // return parentFolder;
    }

    public void setParentFolder(Folder parentFolder) {
        // this.parentFolder = parentFolder;
    }
}
