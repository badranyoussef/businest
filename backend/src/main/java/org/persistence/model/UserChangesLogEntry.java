package org.persistence.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private LocalDate date;
    private String accountEditor;

    public UserChangesLogEntry(String updatedUser, String title, String description, LocalDate date, String accountEditor) {
        this.updatedUser = updatedUser;
        this.title = title;
        this.description = description;
        this.date = date;
        this.accountEditor = accountEditor;
    }
}
