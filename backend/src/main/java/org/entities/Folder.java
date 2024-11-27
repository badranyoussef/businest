package org.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

import org.persistence.model.File;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Folder {
    private String name;
    private Set<File> files;
    private Set<Folder> folders;
    private Set<SubRole> subRoles;
    private Folder parentFolder;
    private Set<PermissionMatrixSettings> permissionMatrixSettings;

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
