package org.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Role {
    private String title;

    @Override
    public String toString() {
        return title;
    }
}
