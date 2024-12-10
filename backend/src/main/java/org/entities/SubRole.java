package org.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubRole {
    private String title;

    @Override
    public String toString() {
        return title;
    }
}