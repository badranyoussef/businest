package org.entities;

import lombok.*;
import org.persistence.model.UserChangesLogEntry;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserChangesLogEntryDTO {

    private int id;
    private String updatedUser;
    private String title;
    private String description;
    private String date;
    private String accountEditor;

    public UserChangesLogEntryDTO(UserChangesLogEntry u) {
        this.id = u.getId();
        this.updatedUser = u.getUpdatedUser();
        this.title = u.getTitle();
        this.description = u.getDescription();
        this.date = u.getDate().toString();
        this.accountEditor = u.getAccountEditor();
    }
}
