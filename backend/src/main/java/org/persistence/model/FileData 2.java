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
    private String filePath;

    public FileData(String description, String topic, File file) {
        System.out.println("Creating FileData with:");
        System.out.println("Description: " + description);
        System.out.println("Topic: " + topic);
        System.out.println("File: " + (file != null ? file.getAbsolutePath() : "null"));

        // Validate inputs
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        if (topic == null || topic.trim().isEmpty()) {
            throw new IllegalArgumentException("Topic cannot be null or empty");
        }

        // Set the basic fields
        this.description = description.trim();
        this.topic = topic.trim();
        this.filePath = file.getAbsolutePath();

        // Get the filename
        String fileName = file.getName();
        if (fileName == null || fileName.trim().isEmpty()) {
            fileName = "unnamed_file.txt";
        }

        // Extract name and file type
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            // File has an extension
            this.name = fileName.substring(0, dotIndex).trim();
            this.fileType = fileName.substring(dotIndex + 1).toLowerCase().trim();
        } else {
            // File has no extension
            this.name = fileName.trim();
            this.fileType = "txt"; // Default file type
        }

        // Final validation
        if (this.name == null || this.name.isEmpty()) {
            this.name = "unnamed";
        }
        if (this.fileType == null || this.fileType.isEmpty()) {
            this.fileType = "txt";
        }
    }
}