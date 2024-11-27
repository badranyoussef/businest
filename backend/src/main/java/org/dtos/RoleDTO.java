package org.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoleDTO {
    private String title;


    @Override
    public String toString() {
        return title;
    }
}
