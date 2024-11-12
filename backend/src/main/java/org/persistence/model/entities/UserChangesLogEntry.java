package org.persistence.model.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserChangesLogEntry {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String updatedUser;
    private String title;
    private String description;
    private Date date;
    private String accountEditor;

}
