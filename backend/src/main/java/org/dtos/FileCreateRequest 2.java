package org.dtos;

import lombok.*;

@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString

public class FileCreateRequest {
    private String description;
    private String topic;
    private String filePath;
}
