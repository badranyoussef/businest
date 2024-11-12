package org.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class UserChangesLogEntryDTO {

    private int id;
    private String updatedUser;
    private String title;
    private String description;
    private Date date;
    private String accountEditor;
}
