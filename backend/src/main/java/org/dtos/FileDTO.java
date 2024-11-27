package org.dtos;

import lombok.*;
import org.persistence.model.UserChangesLogEntry;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class FileDTO {
    private int id;
    private String folderPath;
    private String name;
    private String fileType;

}