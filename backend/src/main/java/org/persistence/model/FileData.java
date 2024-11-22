package org.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "file")
public class FileData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String topic;

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Column(name = "file_path", nullable = false)
    private String filePath;  // Store file path

    public FileData(String description, String topic, File file) {
        this.description = description;
        this.topic = topic;
        this.filePath = file.getAbsolutePath();  // Store absolute file path
        this.name = removeExtension(file.getName());
        this.fileType = extractFileType(file.getName());
    }

    private String extractFileType(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1);  // Extract the file type (extension)
        } else {
            return "unknown";  // Provide a default value if no extension is present
        }
    }

    private String removeExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0) {
            return filename.substring(0, dotIndex);  // Return the filename without the extension
        } else {
            return filename;  // No extension, return the full filename
        }
    }
}