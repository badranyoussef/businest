package org.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDTO {
    private int id;
    private String folder_path;
    private String name;
    private String file_type;

    public FileDTO(String folder_path, String name, String file_type) {
        this.folder_path = folder_path;
        this.name = name;
        this.file_type = file_type;
    }
}
