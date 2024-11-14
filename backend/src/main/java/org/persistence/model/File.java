package org.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dtos.FileDTO;

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

        @Column(name = "folder_path", nullable = false)
        private String folderPath;

        @Column(name = "name", nullable = false)
        private String name;

        @Column(name = "file_type", nullable = false)
        private String fileType;

        public File(String folderPath, String name, String fileType) {
            this.folderPath = folderPath;
            this.name = name;
            this.fileType = fileType;
        }
        public File(FileDTO fileDTO) {
            this.folderPath = fileDTO.getFolder_path();
            this.name = fileDTO.getName();
            this.fileType = fileDTO.getFile_type();
        }
    }
