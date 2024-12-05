package org.folder;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.persistence.model.File;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "folder")
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folder_id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false)
    private String name; // Folder display name

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<File> files = new HashSet<>();

    // Constructor to set folder name
    public Folder(int id, String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name must not be null or empty.");
        }
        this.name = name;
        this.id = id;
    }

    public void addFile(File file) {
        files.add(file);
        file.setFolder(this);
    }

    public void removeFile(File file) {
        files.remove(file);
        file.setFolder(null);
    }

    public String toString() {
        return "Folder{id=" + id + ", name='" + name + "'}";
    }
}
