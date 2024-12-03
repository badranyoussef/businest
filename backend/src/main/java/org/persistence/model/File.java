package org.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.folder.Folder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "file")

public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "folder_path", nullable = true)
    private String folderPath;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @ManyToOne
    @JoinColumn(name = "folder_id", nullable = false)
    @JsonBackReference
    private Folder folder;


    public File(String folderPath, String name, String fileType) {
        this.folderPath = folderPath;
        this.name = name;
        this.fileType = fileType;
    }

    public File(String name, String fileType, Folder folder) {
        this.name = name;
        this.fileType = fileType;
        this.folder = folder;
    }



    public String toString() {
        return "File{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + fileType + '\'' +
                ", folder=" + (folder != null ? folder.getId() : "null") +
                '}';
    }

}