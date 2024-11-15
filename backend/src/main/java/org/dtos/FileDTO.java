package org.dtos;

import lombok.*;

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