package org.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Permissions {
    private String name;
    private String description;
    private String code;

    public Permissions(String name, String description, String code) {
        this.name = name;
        this.description = description;
        this.code = code;
    }
}
