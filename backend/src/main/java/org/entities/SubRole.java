package org.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SubRole {
    private String title;

    @Override
    public String toString() {
        return title;
    }

    public String getTitle() {
        return title;
    }
}